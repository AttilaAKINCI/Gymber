package com.akinci.gymber.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.core.compose.reduce
import com.akinci.gymber.core.coroutine.ContextProvider
import com.akinci.gymber.core.location.LocationManager
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.core.utils.distance.DistanceUtils
import com.akinci.gymber.domain.Gym
import com.akinci.gymber.domain.GymUseCase
import com.akinci.gymber.domain.mapper.toImage
import com.akinci.gymber.domain.mapper.toImages
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val contextProvider: ContextProvider,
    private val gymUseCase: GymUseCase,
    private val permissionManager: PermissionManager,
    private val locationManager: LocationManager,
    private val distanceUtils: DistanceUtils,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(
        State(isPermissionRequired = !permissionManager.isLocationPermissionGranted())
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch {
            withContext(contextProvider.io) {
                gymUseCase.getGyms()
            }.onSuccess { gyms ->
                val processedGyms = processDistance(gyms)
                val finalGyms = processedGyms ?: gyms

                _stateFlow.reduce {
                    copy(
                        gyms = finalGyms,
                        imageStates = finalGyms.toImages().toPersistentList(),
                        isDistanceCalculated = processedGyms != null,
                    )
                }
            }
        }
    }

    fun onGymLike() {
        // We can send a feedback to backend in terms of LIKE action by user.
    }

    fun onGymDislike() {
        // We can send a feedback to backend in terms of DISLIKE action by user.
    }

    fun hideRationaleDialog() {
        _stateFlow.reduce {
            copy(
                isPermissionRequired = false,
                shouldShowRationale = false
            )
        }
    }

    fun onLocationPermissionResult(isGranted: Boolean) {
        /* if (isGranted) {
             // now we have location permission, we need to calculate distances to locations.
             viewModelScope.launch {
                 sendUIState(gyms = stateFlow.value.gyms)
             }
         } else {
             _stateFlow.reduce {
                 copy(shouldShowRationale = true)
             }
         }*/
    }

    private suspend fun processDistance(gyms: List<Gym>): List<Gym>? {
        // get device location
        val deviceLocation = locationManager.getCurrentLocation().getOrNull() ?: return null

        return gyms.map { gym ->
            gym.copy(
                locations = gym.locations.map { location ->
                    distanceUtils.calculateDistance(deviceLocation, location.toCoordinate())
                        ?.let {
                            location.copy(
                                distance = it.value,
                                distanceText = it.valueText,
                            )
                        } ?: location
                }
            )
        }
    }
}

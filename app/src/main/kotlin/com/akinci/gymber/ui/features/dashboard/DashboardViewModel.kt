package com.akinci.gymber.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.core.compose.reduce
import com.akinci.gymber.core.coroutine.ContextProvider
import com.akinci.gymber.core.location.LocationManager
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.core.utils.distance.DistanceUtils
import com.akinci.gymber.domain.GymUseCase
import com.akinci.gymber.domain.Location
import com.akinci.gymber.domain.getNearest
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeImage
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

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch {
            withContext(contextProvider.io) {
                gymUseCase.getGyms()
            }.onSuccess { gyms ->
                val processedGyms = gyms.map {
                    it.copy(locations = calculateDistance(locations = it.locations))
                }

                _stateFlow.reduce {
                    copy(
                        gyms = processedGyms.toPersistentList(),
                        images = processedGyms.map { gym ->
                            SwipeImage(
                                id = gym.id,
                                imageUrl = gym.imageUrl,
                                label = buildString {
                                    append(gym.name)
                                    gym.locations.getNearest()?.distanceText?.let {
                                        append(" - $it")
                                    }
                                }
                            )
                        }.toPersistentList()
                    )
                }
            }
        }
    }

    fun like(gymId: Int) {
        // TODO: inform backend for like action on client.
        // TODO randomize match logic. here.
    }

    fun dislike(gymId: Int) {
        // TODO: inform backend for dislike action on client.
    }

    private suspend fun calculateDistance(locations: List<Location>): List<Location> {
        // get device location
        val deviceLocation = locationManager.getCurrentLocation().getOrNull()
        val isPermissionsGranted = permissionManager.isLocationPermissionGranted()

        // verify requirements
        if (!isPermissionsGranted || deviceLocation == null) return locations

        // try to calculate distance to locations, in case of any malfunction skip that item
        return locations.map {
            distanceUtils.calculateDistance(deviceLocation, it.toCoordinate())?.let { distance ->
                it.copy(
                    distance = distance.value,
                    distanceText = distance.valueText,
                )
            } ?: it
        }
    }
}

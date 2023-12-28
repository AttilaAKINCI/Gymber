package com.akinci.gymber.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.reduce
import com.akinci.gymber.core.coroutine.ContextProvider
import com.akinci.gymber.core.location.LocationManager
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.core.utils.DistanceUtils
import com.akinci.gymber.data.GymRepository
import com.akinci.gymber.domain.Gym
import com.akinci.gymber.domain.mapper.toImages
import com.akinci.gymber.ui.ds.components.snackbar.SnackBarState
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DashboardViewModel @Inject constructor(
    permissionManager: PermissionManager,
    private val contextProvider: ContextProvider,
    private val gymRepository: GymRepository,
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

    fun refresh() {
        // reset ui state
        _stateFlow.reduce {
            copy(
                gyms = listOf(),
                imageStates = persistentListOf(),
                isError = false,
            )
        }

        getGyms(delay = 500L)
    }

    private fun getGyms(delay: Long? = null) {
        viewModelScope.launch {
            delay?.let { delay(it) }

            withContext(contextProvider.io) {
                gymRepository.getGyms()
            }.onSuccess { gyms ->
                val processedGyms = processDistance(gyms)
                val finalGyms = processedGyms ?: gyms

                _stateFlow.reduce {
                    copy(
                        gyms = finalGyms,
                        imageStates = finalGyms.toImages().toPersistentList(),
                        isError = false,
                        isDistanceCalculated = processedGyms != null,
                    )
                }
            }.onFailure {
                // our network request is failed, show error state
                _stateFlow.reduce {
                    copy(isError = true)
                }
            }
        }
    }

    fun messageMatch() {
        _stateFlow.reduce {
            copy(
                snackBarState = SnackBarState(
                    messageId = R.string.dashboard_screen_match_message_warning,
                )
            )
        }
    }

    fun callMatch() {
        _stateFlow.reduce {
            copy(
                snackBarState = SnackBarState(
                    messageId = R.string.dashboard_screen_match_call_warning,
                )
            )
        }
    }

    fun dismissMatch() {
        _stateFlow.reduce {
            copy(isMatchOverlayVisible = false)
        }
    }

    fun onGymLike(gymId: Int) {
        // We can send a feedback to backend in terms of LIKE action by user.

        // on this part we are simulating match chance by %20.
        val matchChance = Random.nextInt(0, 100)
        if (matchChance < 20) {
            stateFlow.value.gyms.firstOrNull { it.id == gymId }?.let { matchedGym ->
                _stateFlow.reduce {
                    copy(
                        isMatchOverlayVisible = true,
                        matchedGym = matchedGym,
                    )
                }
            }
        }
    }

    fun onGymDislike(gymId: Int) {
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
        if (isGranted) {
            // now we have location permission, we need to calculate distances to locations.
            viewModelScope.launch {
                val state = stateFlow.value
                if (!state.isDistanceCalculated) {
                    val processedGyms = processDistance(state.gyms)
                    val finalGyms = processedGyms ?: state.gyms

                    _stateFlow.reduce {
                        copy(
                            gyms = finalGyms,
                            imageStates = finalGyms.toImages().toPersistentList(),
                            isError = false,
                            isDistanceCalculated = processedGyms != null,
                        )
                    }
                }
            }
        } else {
            // permission is denied, show rationale dialog
            _stateFlow.reduce {
                copy(shouldShowRationale = true)
            }
        }
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

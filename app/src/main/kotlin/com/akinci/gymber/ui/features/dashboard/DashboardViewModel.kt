package com.akinci.gymber.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.R
import com.akinci.gymber.core.coroutine.ContextProvider
import com.akinci.gymber.core.location.Coordinate
import com.akinci.gymber.core.location.LocationManager
import com.akinci.gymber.core.mvi.MVI
import com.akinci.gymber.core.mvi.mvi
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.core.utils.GymMatchSimulator
import com.akinci.gymber.core.utils.distance.DistanceUtils
import com.akinci.gymber.data.repository.GymRepository
import com.akinci.gymber.domain.data.Gym
import com.akinci.gymber.domain.mapper.toImage
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.Action
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.Effect
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val permissionManager: PermissionManager,
    private val contextProvider: ContextProvider,
    private val gymRepository: GymRepository,
    private val locationManager: LocationManager,
    private val distanceUtils: DistanceUtils,
    private val gymMatchSimulator: GymMatchSimulator,
) : ViewModel(), MVI<State, Action, Effect> by mvi(State()) {

    override fun onAction(action: Action) {
        when (action) {
            is Action.OnPermissionGranted -> onLocationPermissionGranted()
            Action.ShowRationaleDialog -> updateState { copy(isRationaleDialogVisible = true) }
            Action.HideRationaleDialog -> updateState { copy(isRationaleDialogVisible = false) }
            is Action.OnLikeButtonClick -> onGymLike(action.gymId)
            is Action.OnDislikeButtonClick -> onGymDislike(action.gymId)
            Action.OnRefreshButtonClick -> refresh()
            Action.OnDismissMatchButtonClick -> dismissMatch()
            Action.OnCallMatchButtonClick -> callMatch()
            Action.OnMessageMatchButtonClick -> messageMatch()
            is Action.OnDetailButtonClick -> {
                val gym = state.value.getGym(action.gymId) ?: return
                viewModelScope.sendEffect(Effect.OpenDetailScreen(gym = gym))
            }
        }
    }

    init {
        getGyms()

        // TODO permission kismini denemek lazim. Test et!!
        // check permission on dashboard launch, ask permission if it's required.
        viewModelScope.launch {
            if (!permissionManager.isLocationPermissionGranted()) {
                delay(500L)
                sendEffect(Effect.RequestPermission)
            }
        }
    }

    fun refresh() {
        // reset ui state
        updateState {
            copy(
                gyms = listOf(),
                imageStates = persistentListOf(),
                isErrorDialogVisible = false,
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

                updateState {
                    copy(
                        gyms = finalGyms,
                        imageStates = finalGyms.map { it.toImage() }.toPersistentList(),
                        isErrorDialogVisible = false,
                        isDistanceCalculated = processedGyms != null,
                    )
                }
            }.onFailure {
                // our network request is failed, show error state
                updateState {
                    copy(isErrorDialogVisible = true)
                }
            }
        }
    }

    fun messageMatch() {
        viewModelScope.sendEffect(
            Effect.ShowToastMessage(messageId = R.string.dashboard_screen_match_message_warning)
        )
    }

    fun callMatch() {
        viewModelScope.sendEffect(
            Effect.ShowToastMessage(messageId = R.string.dashboard_screen_match_call_warning)
        )
    }

    fun dismissMatch() {
        updateState {
            copy(isMatchOverlayVisible = false)
        }
    }

    fun onGymLike(gymId: Int) {
        // We can send a feedback to backend in terms of LIKE action by user.
        if (gymMatchSimulator.isItAMatch()) {
            state.value.getGym(id = gymId)?.let { matchedGym ->
                updateState {
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
        updateState {
            copy(isRationaleDialogVisible = false)
        }
    }

    fun onLocationPermissionGranted() {
        // now we have location permission, we need to calculate distances to locations.
        viewModelScope.launch {
            val state = state.value
            if (!state.isDistanceCalculated) {
                val processedGyms = processDistance(state.gyms)
                val finalGyms = processedGyms ?: state.gyms

                updateState {
                    copy(
                        gyms = finalGyms,
                        imageStates = finalGyms.map { it.toImage() }.toPersistentList(),
                        isErrorDialogVisible = false,
                        isDistanceCalculated = processedGyms != null,
                    )
                }
            }
        }
    }

    private suspend fun processDistance(gyms: List<Gym>): List<Gym>? {
        // get device location
        val deviceLocation = locationManager.getCurrentLocation().getOrNull() ?: return null

        return gyms.map { gym ->
            gym.copy(
                locations = gym.locations.map { location ->
                    distanceUtils.calculateDistance(
                        from = deviceLocation,
                        to = Coordinate(
                            latitude = location.latitude,
                            longitude = location.longitude
                        ),
                    )?.let {
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

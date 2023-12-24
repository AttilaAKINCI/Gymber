package com.akinci.gymber.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.core.compose.reduce
import com.akinci.gymber.core.coroutine.ContextProvider
import com.akinci.gymber.domain.Location
import com.akinci.gymber.domain.GymUseCase
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
            }.onSuccess {
                val processedGyms = it.map { gym ->
                    gym.copy(
                        distance = calculateDistance(location = gym.locations.firstOrNull())
                    )
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
                                    if (gym.distance.isNotBlank()) {
                                        append(" - ${gym.distance}")
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

    private fun calculateDistance(location: Location?): String {
        // TODO fetch user's location and calculate distance between location and user.
        return "100 km away"
    }


    /* fun getTopElement(): PartnerResponse? {
         return if (partnerListState.isNotEmpty()) {
             partnerListState[0]
         } else {
             null
         }
     }

     fun findClosestLocation(partner: PartnerResponse): String {
         return LocationProvider.findClosest(partner.locations)?.distance ?: ""
     }

     fun like() {
         getTopElement()?.let {
             if (it.isAMatch) {
                 partnerState = it
                 matchState = true
             } else {
                 partnerListState = partnerListState.toMutableList().apply { removeAt(0) }
             }
         }
     }

     fun dislike() {
         partnerState = null
         partnerListState = partnerListState.toMutableList().apply { removeAt(0) }
     }

     fun select() {
         partnerState = getTopElement()
     }

     fun dismissMatchState() {
         matchState = false
         partnerListState = partnerListState.toMutableList().apply { removeAt(0) }
     }

     fun getPartnerList() {
         Timber.d("DashboardViewModel:: getPartnerList called")
         if (partnerListState.isEmpty()) {
             viewModelScope.launch(coroutineContext.IO) {
                 partnerRepository.getPartnerList().collect { networkResponse ->
                     when (networkResponse) {
                         is NetworkResponse.Loading -> {
                             Timber.d("DashboardViewModel:: Partner list loading..")
                             withContext(this@DashboardViewModel.coroutineContext.Main) {
                                 informer = UIState.OnLoading
                             }
                         }

                         is NetworkResponse.Error -> {
                             Timber.d("DashboardViewModel:: Partner list service error..")
                             withContext(this@DashboardViewModel.coroutineContext.Main) {
                                 informer = UIState.OnServiceError
                             }
                         }

                         is NetworkResponse.Success -> {
                             networkResponse.data?.let {
                                 delay(2000) // simulate network delay
                                 Timber.d("Partner list fetched size:-> ${it.data.size}")

                                 // match info inserted.
                                 var partnerList = PartnerMatchProvider.createAMatchPattern(it.data)

                                 // distance calculations inserted.
                                 partnerList = LocationProvider.calculateDistances(partnerList)

                                 // send data to UI
                                 partnerListState = partnerList
                             }
                         }
                     }
                 }
             }
         }
     }*/
}
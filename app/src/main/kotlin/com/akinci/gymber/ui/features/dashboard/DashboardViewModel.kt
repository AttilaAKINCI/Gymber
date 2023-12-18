package com.akinci.gymber.ui.features.dashboard

import androidx.lifecycle.ViewModel
import com.akinci.gymber.core.coroutine.ContextProvider
import com.akinci.gymber.domain.PartnerUseCase
import com.akinci.gymber.ui.features.dashboard.DashboardScreenViewContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val contextProvider: ContextProvider,
    private val partnerUseCase: PartnerUseCase,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        getPartners()
    }

    private fun getPartners() {

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
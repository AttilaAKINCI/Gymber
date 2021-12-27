package com.akinci.gymber.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.common.coroutine.CoroutineContextProvider
import com.akinci.gymber.common.helper.NetworkResponse
import com.akinci.gymber.common.helper.PartnerMatchSimulator
import com.akinci.gymber.common.helper.state.ListState
import com.akinci.gymber.common.helper.state.UIState
import com.akinci.gymber.common.network.NetworkChecker
import com.akinci.gymber.data.output.Partner
import com.akinci.gymber.data.repository.PartnerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val coroutineContext: CoroutineContextProvider,
    private val partnerRepository: PartnerRepository,
    val networkChecker: NetworkChecker
): ViewModel() {

    private var partnerList = mutableListOf<Partner>()
    private var swipedItems = mutableListOf<Partner>()

    /** Fragments are driven with states **/
    private var _partnerListData = MutableStateFlow<ListState<List<Partner>>>(ListState.None)
    var partnerListData: StateFlow<ListState<List<Partner>>> = _partnerListData

    /** works like send and forget **/
    private var _uiState = MutableStateFlow<UIState>(UIState.None)
    var uiState: StateFlow<UIState> = _uiState

    init {
        Timber.d("SharedViewModel created..")
    }

    fun resetUIState(){ _uiState.value = UIState.None }
    fun getTopItem(): Partner{ return partnerList[0] }
    fun getLastSwipedItem(): Partner{ return swipedItems.last() }
    fun removeItem(){
        viewModelScope.launch(coroutineContext.IO) {
            if(partnerList.isNotEmpty()){
                swipedItems.add(partnerList[0])
                partnerList.removeAt(0)
                _partnerListData.emit(ListState.OnData(partnerList))
            }
        }
    }

    fun getPartnerList(){
        viewModelScope.launch(coroutineContext.IO) {
            if(partnerList.isEmpty()){
                partnerRepository.getPartnerList().collect { networkResponse ->
                    when(networkResponse){
                        is NetworkResponse.Loading -> { _partnerListData.emit(ListState.OnLoading) }
                        is NetworkResponse.Error -> { _uiState.emit(UIState.OnServiceError) }
                        is NetworkResponse.NetworkError -> { _uiState.emit(UIState.OnNetworkError) }
                        is NetworkResponse.Success -> {
                            networkResponse.data?.let {
                                partnerList = PartnerMatchSimulator.createAMatchPattern(it.data)
                                _partnerListData.emit(ListState.OnData(partnerList))
                                _uiState.emit(UIState.None) // clears previous error and network errors
                            }
                        }
                    }
                }
            }else{
                _partnerListData.emit(ListState.OnData(partnerList))
            }
        }
    }
}
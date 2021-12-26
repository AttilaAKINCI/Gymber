package com.akinci.gymber.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.common.coroutine.CoroutineContextProvider
import com.akinci.gymber.common.helper.NetworkResponse
import com.akinci.gymber.common.helper.state.ListState
import com.akinci.gymber.common.helper.state.UIState
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
    private val partnerRepository: PartnerRepository
): ViewModel() {

    /** Fragments are driven with states **/
    private var _partnerListData = MutableStateFlow<ListState<List<Partner>>>(ListState.None)
    var partnerListData: StateFlow<ListState<List<Partner>>> = _partnerListData

    /** works like send and forget **/
    private var _uiState = MutableStateFlow<UIState>(UIState.None)
    var uiState: StateFlow<UIState> = _uiState

    init {
        Timber.d("SharedViewModel created..")
    }

    fun getPartnerList(){
        viewModelScope.launch(coroutineContext.IO) {
            partnerRepository.getPartnerList().collect { networkResponse ->
                when(networkResponse){
                    is NetworkResponse.Loading -> { _partnerListData.emit(ListState.OnLoading) }
                    is NetworkResponse.Error -> { _uiState.emit(UIState.OnServiceError) }
                    is NetworkResponse.Success -> {
                        networkResponse.data?.let {
                            _partnerListData.emit(ListState.OnData(it.data))
                        }
                    }
                }
            }
        }
    }

}
package com.akinci.gymber.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akinci.gymber.ui.features.detail.DetailViewContract.ScreenArgs
import com.akinci.gymber.ui.features.detail.DetailViewContract.State
import com.akinci.gymber.ui.features.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val screenArgs by lazy { savedStateHandle.navArgs<ScreenArgs>() }

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    init {

    }
}
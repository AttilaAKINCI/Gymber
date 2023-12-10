package com.akinci.gymbercompose.common.helper.state

sealed class UIState {
    object None: UIState()
    object OnServiceError: UIState()
    object OnLoading: UIState()
}
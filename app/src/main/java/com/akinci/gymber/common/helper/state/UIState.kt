package com.akinci.gymber.common.helper.state

sealed class UIState {
    object None: UIState()
    object OnServiceError: UIState()
}
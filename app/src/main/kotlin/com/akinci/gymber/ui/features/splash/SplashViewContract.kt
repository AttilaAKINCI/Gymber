package com.akinci.gymber.ui.features.splash

import com.akinci.gymber.core.compose.UIState

object SplashViewContract {
    data class State(
        val isCompleted: Boolean = false
    ) : UIState
}
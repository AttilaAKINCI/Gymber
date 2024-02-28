package com.akinci.gymber.ui.features.splash

object SplashViewContract {
    sealed interface Effect {
        data object Completed : Effect
    }
}

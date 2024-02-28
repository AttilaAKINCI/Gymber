package com.akinci.gymber.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.akinci.gymber.ui.ds.components.systembarcontroller.SystemBarColorState
import com.akinci.gymber.ui.ds.theme.halfTransparentSurface
import com.akinci.gymber.ui.features.destinations.Destination
import com.akinci.gymber.ui.features.destinations.DetailScreenDestination

val Destination.getSystemBarColorState
    @Composable
    get() = when (this) {
        is DetailScreenDestination -> SystemBarColorState(
            navigationBarColor = Color.halfTransparentSurface,
            isLightStatusBarContent = false,
        )

        else -> SystemBarColorState(
            statusBarColor = Color.halfTransparentSurface,
            navigationBarColor = Color.halfTransparentSurface,
        )
    }

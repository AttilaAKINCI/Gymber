package com.akinci.gymber.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.akinci.gymber.ui.ds.components.SystemBarColors
import com.akinci.gymber.ui.ds.theme.halfTransparentSurface
import com.akinci.gymber.ui.features.destinations.Destination
import com.akinci.gymber.ui.features.destinations.DetailScreenDestination

val Destination.getSystemBarColors
    @Composable
    get() = when (this) {
        is DetailScreenDestination -> {
            SystemBarColors(
                navigationBarColor = Color.halfTransparentSurface,
                isLightStatusBarContent = true,
            )
        }

        else -> SystemBarColors(
            statusBarColor = Color.halfTransparentSurface,
            navigationBarColor = Color.halfTransparentSurface,
        )
    }
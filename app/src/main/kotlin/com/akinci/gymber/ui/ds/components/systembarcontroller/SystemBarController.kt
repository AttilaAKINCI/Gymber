package com.akinci.gymber.ui.ds.components.systembarcontroller

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SystemBarController(
    systemBarColorState: SystemBarColorState = SystemBarColorState(),
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = systemBarColorState.statusBarColor.toArgb()
            window.navigationBarColor = systemBarColorState.navigationBarColor.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars =
                    !(systemBarColorState.isLightStatusBarContent ?: isDarkTheme)
                isAppearanceLightNavigationBars =
                    !(systemBarColorState.isLightNavigationBarContent ?: isDarkTheme)
            }
        }
    }
}
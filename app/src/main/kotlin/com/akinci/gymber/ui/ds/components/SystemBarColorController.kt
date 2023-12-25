package com.akinci.gymber.ui.ds.components

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

data class SystemBarColors(
    val statusBarColor: Color = Color.Transparent,
    val navigationBarColor: Color = Color.Transparent,
    val isLightStatusBarContent: Boolean? = null,
    val isLightNavigationBarContent: Boolean? = null,
)

@Composable
fun SystemBarColorController(
    systemBarColors: SystemBarColors = SystemBarColors(),
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = systemBarColors.statusBarColor.toArgb()
            window.navigationBarColor = systemBarColors.navigationBarColor.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars =
                    !(systemBarColors.isLightStatusBarContent ?: isDarkTheme)
                isAppearanceLightNavigationBars =
                    !(systemBarColors.isLightNavigationBarContent ?: isDarkTheme)
            }
        }
    }
}
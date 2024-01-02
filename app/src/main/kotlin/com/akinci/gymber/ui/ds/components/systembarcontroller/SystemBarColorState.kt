package com.akinci.gymber.ui.ds.components.systembarcontroller

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.akinci.gymber.ui.ds.theme.halfTransparentSurface
import timber.log.Timber

data class SystemBarColorState(
    val statusBarColor: Color = Color.Transparent,
    val navigationBarColor: Color = Color.Transparent,
    val isLightStatusBarContent: Boolean? = null,
    val isLightNavigationBarContent: Boolean? = null,
)

@Composable
fun rememberSystemBarColors(
    threshold: MutableState<Int>,
    scrollState: ScrollState,
    isDarkMode: Boolean = isSystemInDarkTheme(),
): State<SystemBarColorState> {
    val containerColor = Color.halfTransparentSurface

    return remember {
        derivedStateOf {
            val alphaStartPoint = threshold.value * 0.5f
            val alphaEndPoint = threshold.value * 1f
            val contentAlpha = if (alphaEndPoint != alphaStartPoint) {
                ((scrollState.value - alphaStartPoint) / (alphaEndPoint - alphaStartPoint))
            } else {
                0f
            }.coerceIn(0f, 1f)

            val colorSwapPoint = threshold.value * 0.8f
            if (scrollState.value < colorSwapPoint) {
                SystemBarColorState(
                    statusBarColor = containerColor.copy(alpha = contentAlpha),
                    isLightStatusBarContent = true,
                )
            } else {
                SystemBarColorState(
                    statusBarColor = containerColor,
                    navigationBarColor = containerColor,
                    isLightStatusBarContent = isDarkMode,
                )
            }
        }
    }
}

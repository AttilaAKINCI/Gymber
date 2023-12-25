package com.akinci.gymber.ui.ds.components.systembarcontroller

import androidx.compose.ui.graphics.Color

data class SystemBarColorState(
    val statusBarColor: Color = Color.Transparent,
    val navigationBarColor: Color = Color.Transparent,
    val isLightStatusBarContent: Boolean? = null,
    val isLightNavigationBarContent: Boolean? = null,
)

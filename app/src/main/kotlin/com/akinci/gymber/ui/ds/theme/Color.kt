package com.akinci.gymber.ui.ds.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val WhiteLight = Color(0xFFFFFFFF)
private val WhiteLight_85A = Color(0xD9FFFFFF)
private val WhiteDark = Color(0xF2CCCCCC)

private val BlueLight = Color(0xFF337BE2)
private val BlueDark = Color(0xFF4189EF)

private val GreyLight = Color(0xFFDDDDDD)
private val GreyDark = Color(0xFF555555)

private val Black = Color(0xFF131313)
private val Black_85A = Color(0xA6333333)

val LightColorScheme = lightColorScheme(
    primary = BlueLight,
    onPrimary = WhiteLight,
    secondary = GreyLight,
    background = WhiteLight,
    onBackground = Black,
    surface = WhiteLight,
    onSurface = Black,
    surfaceVariant = WhiteLight_85A,
    onSurfaceVariant = Black,
)

val DarkColorScheme = darkColorScheme(
    primary = BlueDark,
    onPrimary = WhiteDark,
    secondary = GreyDark,
    background = Black,
    onBackground = WhiteDark,
    surface = Black,
    onSurface = WhiteDark,
    surfaceVariant = Black_85A,
    onSurfaceVariant = WhiteDark,
)

val Color.Companion.Purple: Color
    get() = Color(0xFF9C27B0)

val Color.Companion.Teal: Color
    get() = Color(0xFF009688)

val Color.Companion.RedDark: Color
    get() = Color(0xFFE91E63)

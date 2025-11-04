package com.velichkomarija.everydaykit.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Green = Color(0xFF43A047)

@Immutable
data class ExtendedColors(
    val success: Color,
    val warning: Color,
    val info: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        success = Color(0xFF22C55E),        // зелёный
        warning = Color(0xFFF59E0B),        // янтарный
        info = Color(0xFF3B82F6)
    )
}
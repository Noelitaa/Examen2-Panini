package com.moviles.paninisupport.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = PaniniBlue,
    onPrimary = PaniniOnPrimary,
    secondary = PaniniRed,
    onSecondary = PaniniOnPrimary,
    tertiary = PaniniGold,
    background = PaniniBackground,
    surface = PaniniSurface,
    onBackground = PaniniTextPrimary,
    onSurface = PaniniTextPrimary
)

@Composable
fun PaniniSupportTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}

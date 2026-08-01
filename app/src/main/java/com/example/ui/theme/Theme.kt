package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val MidnightColorScheme = darkColorScheme(
    primary = DeepPurple,
    secondary = VioletGlow,
    tertiary = GoldAccent,
    background = MidnightBlue,
    surface = DarkPurpleSurface,
    onPrimary = SoftWhite,
    onSecondary = SoftWhite,
    onBackground = SoftWhite,
    onSurface = SoftWhite
)

@Composable
fun StitchTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MidnightColorScheme,
        typography = AppTypography,
        content = content
    )
}

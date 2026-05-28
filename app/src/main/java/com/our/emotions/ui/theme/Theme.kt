package com.our.emotions.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = SurfaceLight,
    secondary = AccentBlue,
    onSecondary = SurfaceLight,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFF0F2F6),
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    onPrimary = SurfaceLight,
    secondary = AccentBlue,
    onSecondary = SurfaceLight,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF24304A),
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark,
)

@Composable
fun EmotionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = EmotionTypography,
        shapes = EmotionShapes,
        content = content
    )
}

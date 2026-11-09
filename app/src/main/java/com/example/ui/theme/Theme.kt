package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AetherColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = OnSurfaceCyan,
    primaryContainer = SurfaceElevated,
    onPrimaryContainer = NeonCyan,
    secondary = ElectricViolet,
    onSecondary = OnSurfaceViolet,
    secondaryContainer = SurfaceElevated,
    onSecondaryContainer = ElectricViolet,
    tertiary = SolarAmber,
    onTertiary = SpaceBackground,
    background = SpaceBackground,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceBorder,
    error = PlasmaRuby,
    onError = TextPrimary
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AetherColorScheme,
        typography = Typography,
        content = content
    )
}

package com.example.app_02_treinos_performance.core.designSystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = NavyPrimary,
    onPrimary = Color.White,
    secondary = OrangeAccent,
    onSecondary = Color.White,
    tertiary = TealAccent,
    onTertiary = Color.White,
    background = BackgroundGray,
    onBackground = NavyPrimary,
    surface = SurfaceWhite,
    onSurface = NavyPrimary,
    surfaceVariant = DetailBackground,
    onSurfaceVariant = TextSecondaryGray,
    outline = BorderGray
)

@Composable
fun App02treinosperformanceTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
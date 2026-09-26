package com.example.app_01_gestao_leituras.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Modo de tema escolhido pelo usuário na tela de Configurações.
 * SISTEMA acompanha o tema claro/escuro do Android.
 */
enum class ThemeMode(val displayName: String) {
    SISTEMA("Sistema"),
    CLARO("Claro"),
    ESCURO("Escuro");

    companion object {
        val Default = SISTEMA
    }
}

private fun buildColorScheme(accentColor: AccentColor, darkTheme: Boolean) =
    if (darkTheme) {
        darkColorScheme(
            primary = accentColor.dark,
            secondary = PurpleGrey80,
            tertiary = Pink80
        )
    } else {
        lightColorScheme(
            primary = accentColor.light,
            secondary = PurpleGrey40,
            tertiary = Pink40
        )
    }

@Composable
fun App01gestaoleiturasTheme(
    themeMode: ThemeMode = ThemeMode.Default,
    accentColor: AccentColor = AccentColor.Default,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SISTEMA -> isSystemInDarkTheme()
        ThemeMode.CLARO -> false
        ThemeMode.ESCURO -> true
    }

    val colorScheme = buildColorScheme(accentColor, darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

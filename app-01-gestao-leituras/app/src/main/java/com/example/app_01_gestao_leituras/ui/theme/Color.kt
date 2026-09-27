package com.example.app_01_gestao_leituras.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

/**
 * Cores de destaque (accent) que o usuário pode escolher na tela de Configurações.
 * Cada opção define um tom para o modo claro e outro para o modo escuro.
 */
enum class AccentColor(
    val displayName: String,
    val swatch: Color,
    val light: Color,
    val dark: Color
) {
    ROXO(
        displayName = "Roxo",
        swatch = Color(0xFF6650A4),
        light = Color(0xFF6650A4),
        dark = Color(0xFFD0BCFF)
    ),
    VERDE(
        displayName = "Verde",
        swatch = Color(0xFF1B5E20),
        light = Color(0xFF1B5E20),
        dark = Color(0xFF8BC996)
    ),
    AZUL(
        displayName = "Azul",
        swatch = Color(0xFF1565C0),
        light = Color(0xFF1565C0),
        dark = Color(0xFF9EC9FF)
    ),
    LARANJA(
        displayName = "Laranja",
        swatch = Color(0xFFE65100),
        light = Color(0xFFE65100),
        dark = Color(0xFFFFB782)
    ),
    ROSA(
        displayName = "Rosa",
        swatch = Color(0xFFAD1457),
        light = Color(0xFFAD1457),
        dark = Color(0xFFFFA9CE)
    );

    companion object {
        val Default = ROXO
    }
}
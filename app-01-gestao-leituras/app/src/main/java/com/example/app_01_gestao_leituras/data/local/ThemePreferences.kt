package com.example.app_01_gestao_leituras.data.local

import android.content.Context
import com.example.app_01_gestao_leituras.ui.theme.AccentColor
import com.example.app_01_gestao_leituras.ui.theme.ThemeMode

/**
 * Guarda a preferência de tema (modo e cor de destaque) escolhida pelo usuário,
 * para que ela seja lembrada entre as sessões do app.
 */
class ThemePreferences(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getThemeMode(): ThemeMode {
        val saved = prefs.getString(KEY_THEME_MODE, null) ?: return ThemeMode.Default
        return runCatching { ThemeMode.valueOf(saved) }.getOrDefault(ThemeMode.Default)
    }

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
    }

    fun getAccentColor(): AccentColor {
        val saved = prefs.getString(KEY_ACCENT_COLOR, null) ?: return AccentColor.Default
        return runCatching { AccentColor.valueOf(saved) }.getOrDefault(AccentColor.Default)
    }

    fun setAccentColor(accentColor: AccentColor) {
        prefs.edit().putString(KEY_ACCENT_COLOR, accentColor.name).apply()
    }

    companion object {
        private const val PREFS_NAME = "theme_preferences"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_ACCENT_COLOR = "accent_color"
    }
}

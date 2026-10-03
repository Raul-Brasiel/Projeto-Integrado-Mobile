package com.example.app_01_gestao_leituras.feature.configuracoes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_01_gestao_leituras.data.local.ThemePreferences
import com.example.app_01_gestao_leituras.ui.theme.AccentColor
import com.example.app_01_gestao_leituras.ui.theme.ThemeMode

class ThemeViewModel(private val preferences: ThemePreferences) : ViewModel() {

    var themeMode by mutableStateOf(preferences.getThemeMode())
        private set

    var accentColor by mutableStateOf(preferences.getAccentColor())
        private set

    fun updateThemeMode(mode: ThemeMode) {
        themeMode = mode
        preferences.setThemeMode(mode)
    }

    fun updateAccentColor(accentColor: AccentColor) {
        this.accentColor = accentColor
        preferences.setAccentColor(accentColor)
    }
}

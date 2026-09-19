package com.example.app_02_treinos_performance.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.graphics.vector.ImageVector

enum class BarraInferiorNavigation(val rota: String, val label: String, val icone: ImageVector) {
    FICHAS(rota = "fichas", label = "Fichas", icone = Icons.Default.Description),
    CARDIO(rota = "cardio", label = "Cárdio", icone = Icons.AutoMirrored.Filled.DirectionsRun)
}
package com.example.app_02_treinos_performance.feature.home

import com.example.app_02_treinos_performance.data.model.FichaResumo

data class HomeUiState(
    val fichas: List<FichaResumo> = emptyList(),
    val carregando: Boolean = true
)
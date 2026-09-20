package com.example.app_02_treinos_performance.feature.registrarCarga

import com.example.app_02_treinos_performance.data.model.SerieRascunho

data class RegistrarCargaUiState(
    val nomeExercicio: String = "",
    val series: List<SerieRascunho> = emptyList(),
    val carregando: Boolean = true,
    val salvando: Boolean = false,
    val salvo: Boolean = false
)
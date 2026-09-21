package com.example.app_02_treinos_performance.feature.listaCardio

import com.example.app_02_treinos_performance.data.model.Cardio

data class ListaCardioUiState(
    val cardios: List<Cardio> = emptyList(),
    val carregando: Boolean = true
)
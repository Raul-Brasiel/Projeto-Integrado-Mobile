package com.example.app_02_treinos_performance.feature.detalhesFicha

import com.example.app_02_treinos_performance.data.model.ItemFichaComExercicio

data class DetalhesFichaUiState(
    val fichaId: Long = -1,
    val nomeFicha: String = "",
    val itens: List<ItemFichaComExercicio> = emptyList(),
    val carregando: Boolean = true
)
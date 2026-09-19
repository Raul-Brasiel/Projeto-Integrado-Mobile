package com.example.app_02_treinos_performance.feature.novaFicha

import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho

data class NovaFichaUiState(
    val fichaId: Long? = null,
    val nome: String = "",
    val itens: List<ItemFichaRascunho> = emptyList(),
    val carregando: Boolean = false,
    val salvando: Boolean = false,
    val erroNome: String? = null,
    val fichaSalva: Boolean = false
)
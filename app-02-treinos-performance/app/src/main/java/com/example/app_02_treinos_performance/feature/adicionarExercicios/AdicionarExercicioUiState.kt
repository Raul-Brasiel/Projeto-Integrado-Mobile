package com.example.app_02_treinos_performance.feature.adicionarExercicios

import com.example.app_02_treinos_performance.data.model.Exercicio
import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho

data class AdicionarExercicioUiState(
    val textoBusca: String = "",
    val filtroSelecionado: String? = null,
    val catalogo: List<Exercicio> = emptyList(),
    val itensAdicionados: List<ItemFichaRascunho> = emptyList(),
    val mostrarFormularioNovoExercicio: Boolean = false,
    val nomeNovoExercicio: String = "",
    val tipoNovoExercicio: String? = null,
    val mensagemAlerta: String? = null
)
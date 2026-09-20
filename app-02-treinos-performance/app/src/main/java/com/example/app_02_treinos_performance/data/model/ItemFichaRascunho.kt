package com.example.app_02_treinos_performance.data.model

data class ItemFichaRascunho(
    val itemFichaId: Long? = null,
    val exercicioId: Long,
    val nomeExercicio: String,
    val series: Int,
    val repeticoes: Int,
    val cargaKg: Float
)
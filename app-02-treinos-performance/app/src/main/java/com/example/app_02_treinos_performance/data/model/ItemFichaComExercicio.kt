package com.example.app_02_treinos_performance.data.model

data class ItemFichaComExercicio(
    val id: Long,
    val fichaId: Long,
    val series: Int,
    val repeticoes: Int,
    val cargaKg: Float,
    val ordem: Int,
    val exercicioId: Long,
    val nomeExercicio: String,
    val grupoMuscular: String?
)
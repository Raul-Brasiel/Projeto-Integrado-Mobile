package com.example.app_02_treinos_performance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercicios")
data class Exercicio(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val grupoMuscular: String? = null,
    val criadoPeloUsuario: Boolean = false
)
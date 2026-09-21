package com.example.app_02_treinos_performance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardios")
data class Cardio(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val distanciaKm: Float,
    val tempoSegundos: Int,
    val dataEpochMillis: Long
)
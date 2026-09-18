package com.example.app_02_treinos_performance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fichas")
data class Ficha(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val dataCriacao: Long = System.currentTimeMillis(),
    val observacao: String? = null
)
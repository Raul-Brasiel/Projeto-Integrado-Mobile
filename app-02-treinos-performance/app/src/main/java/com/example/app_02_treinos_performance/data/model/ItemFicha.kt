package com.example.app_02_treinos_performance.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "itens_ficha",
    foreignKeys = [
        ForeignKey(
            entity = Ficha::class,
            parentColumns = ["id"],
            childColumns = ["fichaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercicio::class,
            parentColumns = ["id"],
            childColumns = ["exercicioId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("fichaId"), Index("exercicioId")]
)
data class ItemFicha(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fichaId: Long,
    val exercicioId: Long,
    val series: Int,
    val repeticoes: Int,
    val cargaKg: Float,
    val ordem: Int = 0
)
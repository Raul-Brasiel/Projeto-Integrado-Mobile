package com.example.app_02_treinos_performance.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "series",
    foreignKeys = [
        ForeignKey(
            entity = ItemFicha::class,
            parentColumns = ["id"],
            childColumns = ["itemFichaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("itemFichaId")]
)
data class Serie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemFichaId: Long,
    val numero: Int,
    val pesoKg: Float,
    val repeticoes: Int
)
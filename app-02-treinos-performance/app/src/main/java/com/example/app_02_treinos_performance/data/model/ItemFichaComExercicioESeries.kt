package com.example.app_02_treinos_performance.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ItemFichaComExercicioESeries(
    @Embedded val item: ItemFichaComExercicio,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemFichaId"
    )
    val series: List<Serie>
)

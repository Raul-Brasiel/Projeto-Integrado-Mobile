// Arquivo: model/Estante.kt
package com.example.app_01_gestao_leituras.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Relation
import androidx.room.Embedded

@Entity(tableName = "livros")
data class Estante(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val author: String,
    val totalPages: Int = 0,
    val currentPage: Int = 0,
    val category: String,
    val status: String,
    val description: String = "",
    val rating: Int = 4,
    val coverPhotoUri: String? = null
)

@Entity(
    tableName = "reading_logs",
    foreignKeys = [
        ForeignKey(
            entity = Estante::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReadingLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: Long,
    val type: String = "Nota",
    val page: String = "",
    val date: String,
    val notes: String,
    val isFavorite: Boolean = false
)

data class EstanteWithLogs(
    @Embedded val estante: Estante,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId"
    )
    val logs: List<ReadingLogEntity>
)
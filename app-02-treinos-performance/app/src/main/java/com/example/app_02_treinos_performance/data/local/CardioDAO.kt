package com.example.app_02_treinos_performance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_02_treinos_performance.data.model.Cardio
import kotlinx.coroutines.flow.Flow

@Dao
interface CardioDAO {
    @Insert
    suspend fun inserir(cardio: Cardio): Long

    @Update
    suspend fun atualizar(cardio: Cardio)

    @Delete
    suspend fun deletar(cardio: Cardio)

    @Query("SELECT * FROM cardios ORDER BY dataEpochMillis DESC")
    fun listarTodos(): Flow<List<Cardio>>

    @Query("SELECT * FROM cardios WHERE id = :id")
    suspend fun buscarPorId(id: Long): Cardio?
}
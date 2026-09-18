package com.example.app_02_treinos_performance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_02_treinos_performance.data.model.Ficha
import kotlinx.coroutines.flow.Flow

@Dao
interface FichaDAO {
    @Insert
    suspend fun inserir(ficha: Ficha): Long

    @Update
    suspend fun atualizar(ficha: Ficha)

    @Delete
    suspend fun deletar(ficha: Ficha)

    @Query("SELECT * FROM fichas ORDER BY dataCriacao DESC")
    fun listarTodas(): Flow<List<Ficha>>
}
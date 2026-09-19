package com.example.app_02_treinos_performance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_02_treinos_performance.data.model.Ficha
import com.example.app_02_treinos_performance.data.model.FichaResumo
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

    @Query("""
        SELECT f.id AS id, f.nome AS nome, COUNT(it.id) AS quantidadeExercicios
        FROM fichas f
        LEFT JOIN itens_ficha it ON it.fichaId = f.id
        GROUP BY f.id
        ORDER BY f.dataCriacao DESC
    """)
    fun listarResumo(): Flow<List<FichaResumo>>

    @Query("SELECT * FROM fichas WHERE id = :fichaId")
    suspend fun buscarPorId(fichaId: Long): Ficha?
}
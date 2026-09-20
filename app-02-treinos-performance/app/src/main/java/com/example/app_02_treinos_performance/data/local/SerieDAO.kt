package com.example.app_02_treinos_performance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.app_02_treinos_performance.data.model.Serie
import kotlinx.coroutines.flow.Flow

@Dao
interface SerieDAO {
    @Insert
    suspend fun inserir(serie: Serie): Long

    @Update
    suspend fun atualizar(serie: Serie)

    @Delete
    suspend fun deletar(serie: Serie)

    @Query("SELECT * FROM series WHERE itemFichaId = :itemFichaId ORDER BY numero ASC")
    fun listarPorItemFicha(itemFichaId: Long): Flow<List<Serie>>

    @Query("DELETE FROM series WHERE itemFichaId = :itemFichaId")
    suspend fun removerTodasDoItem(itemFichaId: Long)

    @Transaction
    suspend fun substituirSeries(itemFichaId: Long, novasSeries: List<Serie>) {
        removerTodasDoItem(itemFichaId)
        novasSeries.forEach { inserir(it) }
    }
}
package com.example.app_02_treinos_performance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_02_treinos_performance.data.model.ItemFicha
import com.example.app_02_treinos_performance.data.model.ItemFichaComExercicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemFichaDAO {
    @Insert
    suspend fun adicionarExercicioNaFicha(item: ItemFicha): Long

    @Update
    suspend fun atualizarItem(item: ItemFicha)

    @Delete
    suspend fun removerItem(item: ItemFicha)

    @Query("""
        SELECT 
            it.id AS id,
            it.fichaId AS fichaId,
            it.ordem AS ordem,
            ex.id AS exercicioId,
            ex.nome AS nomeExercicio,
            ex.grupoMuscular AS grupoMuscular,
            COALESCE(contagem.totalSeries, it.series) AS series,
            COALESCE(ultima.repeticoes, it.repeticoes) AS repeticoes,
            COALESCE(ultima.pesoKg, it.cargaKg) AS cargaKg
        FROM itens_ficha it
        INNER JOIN exercicios ex ON ex.id = it.exercicioId
        LEFT JOIN (
            SELECT itemFichaId, COUNT(*) AS totalSeries
            FROM series
            GROUP BY itemFichaId
        ) contagem ON contagem.itemFichaId = it.id
        LEFT JOIN (
            SELECT s1.itemFichaId, s1.repeticoes, s1.pesoKg
            FROM series s1
            WHERE s1.numero = (
                SELECT MAX(s2.numero) FROM series s2 WHERE s2.itemFichaId = s1.itemFichaId
            )
        ) ultima ON ultima.itemFichaId = it.id
        WHERE it.fichaId = :fichaId
        ORDER BY it.ordem ASC
    """)
    fun listarItensDaFicha(fichaId: Long): Flow<List<ItemFichaComExercicio>>

    @Query("DELETE FROM itens_ficha WHERE fichaId = :fichaId")
    suspend fun removerItensDaFicha(fichaId: Long)

    @Query("SELECT * FROM itens_ficha WHERE id = :itemFichaId")
    suspend fun buscarPorId(itemFichaId: Long): ItemFicha?
}
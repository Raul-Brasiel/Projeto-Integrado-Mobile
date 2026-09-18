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
        SELECT it.id AS id, it.fichaId AS fichaId, it.series AS series, it.repeticoes AS repeticoes,
               it.cargaKg AS cargaKg, it.ordem AS ordem,
               ex.id AS exercicioId, ex.nome AS nomeExercicio, ex.grupoMuscular AS grupoMuscular
        FROM itens_ficha it
        INNER JOIN exercicios ex ON ex.id = it.exercicioId
        WHERE it.fichaId = :fichaId
        ORDER BY it.ordem ASC
    """)
    fun listarItensDaFicha(fichaId: Long): Flow<List<ItemFichaComExercicio>>
}
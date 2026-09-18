package com.example.app_02_treinos_performance.data.repository

import com.example.app_02_treinos_performance.data.local.ItemFichaDAO
import com.example.app_02_treinos_performance.data.model.ItemFicha
import com.example.app_02_treinos_performance.data.model.ItemFichaComExercicio
import kotlinx.coroutines.flow.Flow

class ItemFichaRepository(private val itemFichaDao: ItemFichaDAO) {
    fun listarItensDaFicha(fichaId: Long): Flow<List<ItemFichaComExercicio>> =
        itemFichaDao.listarItensDaFicha(fichaId)
    suspend fun adicionarExercicioNaFicha(item: ItemFicha): Long =
        itemFichaDao.adicionarExercicioNaFicha(item)
    suspend fun atualizarItem(item: ItemFicha) = itemFichaDao.atualizarItem(item)
    suspend fun removerItem(item: ItemFicha) = itemFichaDao.removerItem(item)
}
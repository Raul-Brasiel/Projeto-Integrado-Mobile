package com.example.app_02_treinos_performance.data.repository

import com.example.app_02_treinos_performance.data.local.ItemFichaDAO
import com.example.app_02_treinos_performance.data.model.ItemFicha

class ItemFichaRepository(private val itemFichaDao: ItemFichaDAO) {
    suspend fun buscarItemPorId(itemFichaId: Long): ItemFicha? = itemFichaDao.buscarPorId(itemFichaId)
}
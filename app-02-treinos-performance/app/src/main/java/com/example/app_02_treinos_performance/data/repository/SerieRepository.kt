package com.example.app_02_treinos_performance.data.repository

import com.example.app_02_treinos_performance.data.local.SerieDAO
import com.example.app_02_treinos_performance.data.model.Serie
import kotlinx.coroutines.flow.Flow

class SerieRepository(private val serieDao: SerieDAO) {
    fun listarPorItemFicha(itemFichaId: Long): Flow<List<Serie>> = serieDao.listarPorItemFicha(itemFichaId)
    suspend fun salvarSeries(itemFichaId: Long, series: List<Serie>) =
        serieDao.substituirSeries(itemFichaId, series)
}
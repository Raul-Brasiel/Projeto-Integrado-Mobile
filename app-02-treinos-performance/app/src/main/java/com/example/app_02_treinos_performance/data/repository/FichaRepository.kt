package com.example.app_02_treinos_performance.data.repository

import com.example.app_02_treinos_performance.data.local.FichaDAO
import com.example.app_02_treinos_performance.data.model.Ficha
import kotlinx.coroutines.flow.Flow

class FichaRepository(private val fichaDao: FichaDAO) {
    fun listarFichas(): Flow<List<Ficha>> = fichaDao.listarTodas()
    suspend fun salvar(ficha: Ficha): Long = fichaDao.inserir(ficha)
    suspend fun atualizar(ficha: Ficha) = fichaDao.atualizar(ficha)
    suspend fun deletar(ficha: Ficha) = fichaDao.deletar(ficha)
}
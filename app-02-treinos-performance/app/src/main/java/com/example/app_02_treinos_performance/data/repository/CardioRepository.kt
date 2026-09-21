package com.example.app_02_treinos_performance.data.repository

import com.example.app_02_treinos_performance.data.local.CardioDAO
import com.example.app_02_treinos_performance.data.model.Cardio
import kotlinx.coroutines.flow.Flow

class CardioRepository(private val cardioDao: CardioDAO) {
    fun listarTodos(): Flow<List<Cardio>> = cardioDao.listarTodos()
    suspend fun buscarPorId(id: Long): Cardio? = cardioDao.buscarPorId(id)
    suspend fun salvar(cardio: Cardio): Long = cardioDao.inserir(cardio)
    suspend fun atualizar(cardio: Cardio) = cardioDao.atualizar(cardio)
    suspend fun deletar(cardio: Cardio) = cardioDao.deletar(cardio)
}
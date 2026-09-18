package com.example.app_02_treinos_performance.data.repository

import com.example.app_02_treinos_performance.data.local.ExercicioDAO
import com.example.app_02_treinos_performance.data.model.Exercicio
import kotlinx.coroutines.flow.Flow

class ExercicioRepository(private val exercicioDao: ExercicioDAO) {
    fun listarCatalogo(): Flow<List<Exercicio>> = exercicioDao.listarTodos()
    suspend fun salvar(exercicio: Exercicio): Long = exercicioDao.inserir(exercicio)
    suspend fun atualizar(exercicio: Exercicio) = exercicioDao.atualizar(exercicio)
    suspend fun deletar(exercicio: Exercicio) = exercicioDao.deletar(exercicio)
}
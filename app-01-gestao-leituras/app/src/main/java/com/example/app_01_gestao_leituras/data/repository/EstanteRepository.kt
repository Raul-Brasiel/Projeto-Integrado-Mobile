// Arquivo: data/repository/EstanteRepository.kt
package com.example.app_01_gestao_leituras.data.repository

import com.example.app_01_gestao_leituras.data.local.EstanteDAO
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.EstanteWithLogs
import com.example.app_01_gestao_leituras.model.ReadingLogEntity
import kotlinx.coroutines.flow.Flow

class EstanteRepository(private val estanteDAO: EstanteDAO) {

    val allBooks: Flow<List<Estante>> = estanteDAO.getAllBooks()
    val booksWithLogs: Flow<List<EstanteWithLogs>> = estanteDAO.getBooksWithLogs()

    suspend fun insertBook(estante: Estante) {
        estanteDAO.insertBook(estante)
    }

    suspend fun updateBook(estante: Estante) {
        estanteDAO.updateBook(estante)
    }

    suspend fun deleteBook(estante: Estante) {
        estanteDAO.deleteBook(estante)
    }

    // --- OPERAÇÕES DE NOTAS E DIÁRIOS (READING LOGS) ---

    suspend fun insertLog(log: ReadingLogEntity) {
        estanteDAO.insertLog(log)
    }

    suspend fun updateLog(log: ReadingLogEntity) {
        estanteDAO.updateLog(log)
    }

    suspend fun deleteLog(log: ReadingLogEntity) {
        estanteDAO.deleteLog(log)
    }
}
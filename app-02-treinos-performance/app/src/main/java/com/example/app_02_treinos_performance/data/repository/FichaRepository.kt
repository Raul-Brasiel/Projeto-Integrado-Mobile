package com.example.app_02_treinos_performance.data.repository

import androidx.room.withTransaction
import com.example.app_02_treinos_performance.data.AppDatabase
import com.example.app_02_treinos_performance.data.local.FichaDAO
import com.example.app_02_treinos_performance.data.model.Ficha
import com.example.app_02_treinos_performance.data.model.FichaResumo
import com.example.app_02_treinos_performance.data.model.ItemFicha
import com.example.app_02_treinos_performance.data.model.ItemFichaComExercicio
import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho
import kotlinx.coroutines.flow.Flow

class FichaRepository(private val database: AppDatabase) {
    private val fichaDao = database.fichaDao()

    fun listarFichas(): Flow<List<Ficha>> = fichaDao.listarTodas()
    fun listarResumo(): Flow<List<FichaResumo>> = fichaDao.listarResumo()
    suspend fun buscarFichaPorId(fichaId: Long): Ficha? = fichaDao.buscarPorId(fichaId)
    fun listarItensDaFicha(fichaId: Long): Flow<List<ItemFichaComExercicio>> = database.itemFichaDao().listarItensDaFicha(fichaId)
    fun listarItensESeriesDaFicha(fichaId: Long): Flow<List<com.example.app_02_treinos_performance.data.model.ItemFichaComExercicioESeries>> = database.itemFichaDao().listarItensESeriesDaFicha(fichaId)
    suspend fun deletar(ficha: Ficha) = fichaDao.deletar(ficha)

    suspend fun salvarFichaComExercicios(nome: String, itens: List<ItemFichaRascunho>): Long {
        return database.withTransaction {
            val fichaId = fichaDao.inserir(Ficha(nome = nome))
            inserirItens(fichaId, itens)
            fichaId
        }
    }

    suspend fun atualizarFichaComExercicios(fichaId: Long, nome: String, itens: List<ItemFichaRascunho>) {
        database.withTransaction {
            val fichaAtual = fichaDao.buscarPorId(fichaId) ?: return@withTransaction
            fichaDao.atualizar(fichaAtual.copy(nome = nome))

            val itemFichaDao = database.itemFichaDao()
            val itensAntigos = itemFichaDao.buscarItensPorFichaSync(fichaId)
            
            val itensIdsParaManter = itens.mapNotNull { it.itemFichaId }.toSet()
            
            itensAntigos.filter { it.id !in itensIdsParaManter }.forEach { itemAntigo ->
                itemFichaDao.removerItem(itemAntigo)
            }
            
            itens.forEachIndexed { indice, rascunho ->
                if (rascunho.itemFichaId != null) {
                    val itemAntigo = itensAntigos.find { it.id == rascunho.itemFichaId }
                    if (itemAntigo != null) {
                        itemFichaDao.atualizarItem(itemAntigo.copy(ordem = indice))
                    }
                } else {
                    itemFichaDao.adicionarExercicioNaFicha(
                        ItemFicha(
                            fichaId = fichaId,
                            exercicioId = rascunho.exercicioId,
                            series = rascunho.series,
                            repeticoes = rascunho.repeticoes,
                            cargaKg = rascunho.cargaKg,
                            ordem = indice
                        )
                    )
                }
            }
        }
    }

    private suspend fun inserirItens(fichaId: Long, itens: List<ItemFichaRascunho>) {
        val itemFichaDao = database.itemFichaDao()
        itens.forEachIndexed { indice, rascunho ->
            itemFichaDao.adicionarExercicioNaFicha(
                ItemFicha(
                    fichaId = fichaId,
                    exercicioId = rascunho.exercicioId,
                    series = rascunho.series,
                    repeticoes = rascunho.repeticoes,
                    cargaKg = rascunho.cargaKg,
                    ordem = indice
                )
            )
        }
    }
}
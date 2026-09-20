package com.example.app_02_treinos_performance.feature.registrarCarga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_02_treinos_performance.data.model.Serie
import com.example.app_02_treinos_performance.data.model.SerieRascunho
import com.example.app_02_treinos_performance.data.repository.ExercicioRepository
import com.example.app_02_treinos_performance.data.repository.ItemFichaRepository
import com.example.app_02_treinos_performance.data.repository.SerieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrarCargaViewModel(
    private val serieRepository: SerieRepository,
    private val itemFichaRepository: ItemFichaRepository,
    private val exercicioRepository: ExercicioRepository,
    private val itemFichaId: Long
) : ViewModel(){
    private val _uiState = MutableStateFlow(RegistrarCargaUiState())
    val uiState: StateFlow<RegistrarCargaUiState> = _uiState

    init {
        viewModelScope.launch {
            val item = itemFichaRepository.buscarItemPorId(itemFichaId)
            val nomeExercicio = item?.let { i ->
                exercicioRepository.listarCatalogo().first().firstOrNull { it.id == i.exercicioId }?.nome
            } ?: ""

            val seriesSalvas = serieRepository.listarPorItemFicha(itemFichaId).first()

            val seriesIniciais = when {
                seriesSalvas.isNotEmpty() -> seriesSalvas.map {
                    SerieRascunho(it.numero, it.pesoKg.toString(), it.repeticoes.toString())
                }
                item != null -> (1..item.series).map { numero ->
                    SerieRascunho(numero, item.cargaKg.toString(), item.repeticoes.toString())
                }
                else -> emptyList()
            }

            _uiState.update { it.copy(nomeExercicio = nomeExercicio, series = seriesIniciais, carregando = false) }
        }
    }

    fun onPesoAlterado(indice: Int, valor: String) {
        _uiState.update { estadoAtual ->
            val novaLista = estadoAtual.series.toMutableList()
            novaLista[indice] = novaLista[indice].copy(pesoKg = valor)
            estadoAtual.copy(series = novaLista)
        }
    }

    fun onRepeticoesAlterado(indice: Int, valor: String) {
        _uiState.update { estadoAtual ->
            val novaLista = estadoAtual.series.toMutableList()
            novaLista[indice] = novaLista[indice].copy(repeticoes = valor)
            estadoAtual.copy(series = novaLista)
        }
    }

    fun adicionarSerie() {
        _uiState.update { estadoAtual ->
            val ultima = estadoAtual.series.lastOrNull()
            val novaSerie = SerieRascunho(
                numero = estadoAtual.series.size + 1,
                pesoKg = ultima?.pesoKg ?: "",
                repeticoes = ultima?.repeticoes ?: ""
            )
            estadoAtual.copy(series = estadoAtual.series + novaSerie)
        }
    }

    fun salvar() {
        _uiState.update { it.copy(salvando = true) }
        viewModelScope.launch {
            val seriesParaSalvar = _uiState.value.series.mapNotNull { rascunho ->
                val peso = rascunho.pesoKg.toFloatOrNull() ?: return@mapNotNull null
                val reps = rascunho.repeticoes.toIntOrNull() ?: return@mapNotNull null
                Serie(
                    itemFichaId = itemFichaId,
                    numero = rascunho.numero,
                    pesoKg = peso,
                    repeticoes = reps
                )
            }
            serieRepository.salvarSeries(itemFichaId, seriesParaSalvar)
            _uiState.update { it.copy(salvando = false, salvo = true) }
        }
    }

    fun removerSerie(indice: Int) {
        val novaLista = _uiState.value.series.toMutableList()
        if (indice !in novaLista.indices) return

        novaLista.removeAt(indice)
        val listaRenumerada = novaLista.mapIndexed { i, serie -> serie.copy(numero = i + 1) }
        _uiState.update { it.copy(series = listaRenumerada) }

        viewModelScope.launch {
            val seriesParaSalvar = listaRenumerada.mapNotNull { rascunho ->
                val peso = rascunho.pesoKg.toFloatOrNull() ?: return@mapNotNull null
                val reps = rascunho.repeticoes.toIntOrNull() ?: return@mapNotNull null
                Serie(itemFichaId = itemFichaId, numero = rascunho.numero, pesoKg = peso, repeticoes = reps)
            }
            serieRepository.salvarSeries(itemFichaId, seriesParaSalvar)
        }
    }
}
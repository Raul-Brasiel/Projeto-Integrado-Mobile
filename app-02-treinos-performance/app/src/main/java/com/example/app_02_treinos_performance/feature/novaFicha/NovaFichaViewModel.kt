package com.example.app_02_treinos_performance.feature.novaFicha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho
import com.example.app_02_treinos_performance.data.repository.FichaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NovaFichaViewModel(
    private val fichaRepository: FichaRepository,
    private val fichaIdParaEditar: Long? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(NovaFichaUiState(fichaId = fichaIdParaEditar))
    val uiState: StateFlow<NovaFichaUiState> = _uiState

    init {
        fichaIdParaEditar?.let { fichaId ->
            _uiState.update { it.copy(carregando = true) }
            viewModelScope.launch {
                val ficha = fichaRepository.buscarFichaPorId(fichaId)
                val itens = fichaRepository.listarItensDaFicha(fichaId).first().map { item ->
                    ItemFichaRascunho(
                        itemFichaId = item.id,
                        exercicioId = item.exercicioId,
                        nomeExercicio = item.nomeExercicio,
                        series = item.series,
                        repeticoes = item.repeticoes,
                        cargaKg = item.cargaKg
                    )
                }
                _uiState.update {
                    it.copy(
                        nome = ficha?.nome ?: "",
                        itens = itens,
                        carregando = false
                    )
                }
            }
        }
    }

    fun onNomeAlterado(nome: String) {
        _uiState.update { it.copy(nome = nome, erroNome = null) }
    }

    fun adicionarItem(item: ItemFichaRascunho) {
        _uiState.update { it.copy(itens = it.itens + item) }
    }

    fun removerItem(indice: Int) {
        _uiState.update { estadoAtual ->
            val novaLista = estadoAtual.itens.toMutableList()
            if (indice in novaLista.indices) novaLista.removeAt(indice)
            estadoAtual.copy(itens = novaLista)
        }
    }

    fun salvar() {
        val estadoAtual = _uiState.value
        if (estadoAtual.nome.isBlank()) {
            _uiState.update { it.copy(erroNome = "Informe um nome para a ficha") }
            return
        }

        _uiState.update { it.copy(salvando = true) }
        viewModelScope.launch {
            val fichaId = estadoAtual.fichaId
            if (fichaId != null) {
                fichaRepository.atualizarFichaComExercicios(fichaId, estadoAtual.nome, estadoAtual.itens)
            } else {
                fichaRepository.salvarFichaComExercicios(estadoAtual.nome, estadoAtual.itens)
            }
            _uiState.update { it.copy(salvando = false, fichaSalva = true) }
        }
    }
}
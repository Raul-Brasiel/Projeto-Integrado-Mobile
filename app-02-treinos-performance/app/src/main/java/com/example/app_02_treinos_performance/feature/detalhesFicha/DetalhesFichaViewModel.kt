package com.example.app_02_treinos_performance.feature.detalhesFicha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_02_treinos_performance.data.repository.FichaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalhesFichaViewModel(
    private val fichaRepository: FichaRepository,
    private val fichaId: Long
) : ViewModel(){
    private val _uiState = MutableStateFlow(DetalhesFichaUiState(fichaId = fichaId))
    val uiState: StateFlow<DetalhesFichaUiState> = _uiState

    init {
        viewModelScope.launch {
            val ficha = fichaRepository.buscarFichaPorId(fichaId)
            _uiState.update { it.copy(nomeFicha = ficha?.nome ?: "") }

            fichaRepository.listarItensDaFicha(fichaId).collect { itens ->
                _uiState.update { it.copy(itens = itens, carregando = false) }
            }
        }
    }
}
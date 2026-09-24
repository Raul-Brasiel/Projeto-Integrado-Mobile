package com.example.app_02_treinos_performance.feature.listaCardio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_02_treinos_performance.data.repository.CardioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaCardioViewModel(
    private val cardioRepository: CardioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaCardioUiState())
    val uiState: StateFlow<ListaCardioUiState> = _uiState

    init {
        viewModelScope.launch {
            cardioRepository.listarTodos().collect { lista ->
                _uiState.update { it.copy(cardios = lista, carregando = false) }
            }
        }
    }
}
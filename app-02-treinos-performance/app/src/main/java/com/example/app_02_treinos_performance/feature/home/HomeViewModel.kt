package com.example.app_02_treinos_performance.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_02_treinos_performance.data.repository.FichaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fichaRepository: FichaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            fichaRepository.listarResumo().collect { fichas ->
                _uiState.update { it.copy(fichas = fichas, carregando = false) }
            }
        }
    }
}
package com.example.app_02_treinos_performance.feature.listaCardio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_02_treinos_performance.data.repository.CardioRepository

class ListaCardioViewModelFactory(
    private val cardioRepository: CardioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ListaCardioViewModel(cardioRepository) as T
    }
}
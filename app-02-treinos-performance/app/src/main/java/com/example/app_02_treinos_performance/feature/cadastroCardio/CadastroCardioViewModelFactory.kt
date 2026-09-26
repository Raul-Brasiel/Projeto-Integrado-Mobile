package com.example.app_02_treinos_performance.feature.cadastroCardio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_02_treinos_performance.data.repository.CardioRepository

class CadastroCardioViewModelFactory(
    private val cardioRepository: CardioRepository,
    private val cardioIdParaEditar: Long? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CadastroCardioViewModel(cardioRepository, cardioIdParaEditar) as T
    }
}

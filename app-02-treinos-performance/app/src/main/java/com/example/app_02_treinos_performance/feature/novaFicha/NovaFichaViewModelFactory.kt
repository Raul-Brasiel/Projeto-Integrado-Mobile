package com.example.app_02_treinos_performance.feature.novaFicha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_02_treinos_performance.data.repository.FichaRepository

class NovaFichaViewModelFactory(
    private val fichaRepository: FichaRepository,
    private val fichaIdParaEditar: Long? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return NovaFichaViewModel(fichaRepository, fichaIdParaEditar) as T
    }
}
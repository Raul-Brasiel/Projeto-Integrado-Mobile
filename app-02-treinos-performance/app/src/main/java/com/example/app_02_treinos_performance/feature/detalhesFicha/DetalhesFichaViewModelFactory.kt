package com.example.app_02_treinos_performance.feature.detalhesFicha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_02_treinos_performance.data.repository.FichaRepository

class DetalhesFichaViewModelFactory(
    private val fichaRepository: FichaRepository,
    private val fichaId: Long
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        return DetalhesFichaViewModel(fichaRepository, fichaId) as T
    }
}
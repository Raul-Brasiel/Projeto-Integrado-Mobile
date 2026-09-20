package com.example.app_02_treinos_performance.feature.registrarCarga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_02_treinos_performance.data.repository.ExercicioRepository
import com.example.app_02_treinos_performance.data.repository.ItemFichaRepository
import com.example.app_02_treinos_performance.data.repository.SerieRepository

class RegistrarCargaViewModelFactory(
    private val serieRepository: SerieRepository,
    private val itemFichaRepository: ItemFichaRepository,
    private val exercicioRepository: ExercicioRepository,
    private val itemFichaId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RegistrarCargaViewModel(serieRepository, itemFichaRepository, exercicioRepository, itemFichaId) as T
    }
}
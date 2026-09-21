package com.example.app_02_treinos_performance.feature.adicionarExercicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_02_treinos_performance.data.repository.ExercicioRepository

class AdicionarExercicioViewModelFactory(
    private val exercicioRepository: ExercicioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AdicionarExercicioViewModel(exercicioRepository) as T
    }
}
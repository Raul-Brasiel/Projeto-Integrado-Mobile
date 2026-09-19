package com.example.app_02_treinos_performance.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_02_treinos_performance.data.repository.FichaRepository

class HomeViewModelFactory(
    private val fichaRepository: FichaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(fichaRepository) as T
    }
}
package com.example.app_01_gestao_leituras.feature.estante
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.EstanteWithLogs
import com.example.app_01_gestao_leituras.model.ReadingLogEntity
import com.example.app_01_gestao_leituras.data.repository.EstanteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExibirEstante(private val repository: EstanteRepository) : ViewModel() {

    // Estados de Filtros para a Estante
    var selectedGenresFilter by mutableStateOf<List<String>>(emptyList())
        private set

    var selectedStatusFilter by mutableStateOf<String?>(null)
        private set

    var sortByOption by mutableStateOf("Título (A-Z)")
        private set

    fun applyFilters(genres: List<String>, status: String, sortBy: String) {
        selectedGenresFilter = genres
        selectedStatusFilter = if (status.isBlank()) null else status
        sortByOption = sortBy
    }

    fun clearFilters() {
        selectedGenresFilter = emptyList()
        selectedStatusFilter = null
        sortByOption = "Título (A-Z)"
    }

    val books: StateFlow<List<Estante>> = repository.allBooks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val booksWithLogs: StateFlow<List<EstanteWithLogs>> = repository.booksWithLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

       fun registerBook(
        title: String,
        author: String,
        totalPages: Int,
        currentPage: Int,
        category: String,
        status: String,
        description: String,
        rating: Int,
        coverPhotoUri: String? = null
    ) {
        viewModelScope.launch {
            val newBook = Estante(
                title = title,
                author = author,
                totalPages = totalPages,
                currentPage = currentPage,
                category = category,
                status = status,
                description = description,
                rating = rating,
                coverPhotoUri = coverPhotoUri
            )
            repository.insertBook(newBook)
        }
    }

    fun updateBook(estante: Estante) {
        viewModelScope.launch {
            repository.updateBook(estante)
        }
    }
    fun deleteBook(estante: Estante) {
        viewModelScope.launch {
            repository.deleteBook(estante)
        }
    }

      fun insertReadingLog(log: ReadingLogEntity) {
        viewModelScope.launch {
            repository.insertLog(log)
        }
    }

    fun updateReadingLog(log: ReadingLogEntity) {
        viewModelScope.launch {
            repository.updateLog(log)
        }
    }

    fun deleteLog(log: ReadingLogEntity) {
        viewModelScope.launch {
            repository.deleteLog(log)
        }
    }
}
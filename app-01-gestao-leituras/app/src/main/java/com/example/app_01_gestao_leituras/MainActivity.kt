// Arquivo: MainActivity.kt
package com.example.app_01_gestao_leituras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_01_gestao_leituras.data.local.AppDatabase
import com.example.app_01_gestao_leituras.data.repository.EstanteRepository
import com.example.app_01_gestao_leituras.feature.estante.telaEstante
import com.example.app_01_gestao_leituras.feature.estante.AddBookScreen
import com.example.app_01_gestao_leituras.feature.estante.ExibirEstante
import com.example.app_01_gestao_leituras.feature.estante.BookDetailsScreen
import com.example.app_01_gestao_leituras.feature.estante.AtualizarProgressoScreen
import com.example.app_01_gestao_leituras.feature.estante.FiltrosScreen
import com.example.app_01_gestao_leituras.feature.estante.NovoRegistroScreen
import com.example.app_01_gestao_leituras.feature.diario.DiarioScreen
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.ReadingLogEntity
import com.example.app_01_gestao_leituras.ui.theme.App01gestaoleiturasTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val shelfViewModel: ExibirEstante by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = AppDatabase.getDatabase(applicationContext)
                val repository = EstanteRepository(database.estanteDao())
                @Suppress("UNCHECKED_CAST")
                return ExibirEstante(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App01gestaoleiturasTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentScreen by remember { mutableStateOf("shelf") }
                    var selectedBook by remember { mutableStateOf<Estante?>(null) }
                    var selectedLogToEdit by remember { mutableStateOf<ReadingLogEntity?>(null) }

                    // Coleta os livros com seus respectivos logs do banco de dados Room
                    val booksWithLogs by shelfViewModel.booksWithLogs.collectAsState(initial = emptyList())

                    val showBottomBar = currentScreen == "shelf" || currentScreen == "diario" || currentScreen == "filtros"

                    Scaffold(
                        bottomBar = {
                            if (showBottomBar) {
                                NavigationBar(
                                    containerColor = Color(0xFFFBF9F1)
                                ) {
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Home, contentDescription = "Estante") },
                                        label = { Text("Estante") },
                                        selected = currentScreen == "shelf",
                                        onClick = { currentScreen = "shelf" },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF1B5E20),
                                            selectedTextColor = Color(0xFF1B5E20),
                                            indicatorColor = Color(0xFFDCD6D0)
                                        )
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = "Diário") },
                                        label = { Text("Diário") },
                                        selected = currentScreen == "diario",
                                        onClick = { currentScreen = "diario" },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF1B5E20),
                                            selectedTextColor = Color(0xFF1B5E20),
                                            indicatorColor = Color(0xFFDCD6D0)
                                        )
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Tune, contentDescription = "Filtros") },
                                        label = { Text("Filtros") },
                                        selected = currentScreen == "filtros",
                                        onClick = { currentScreen = "filtros" },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF1B5E20),
                                            selectedTextColor = Color(0xFF1B5E20),
                                            indicatorColor = Color(0xFFDCD6D0)
                                        )
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (currentScreen) {
                                "shelf" -> {
                                    telaEstante(
                                        viewModel = shelfViewModel,
                                        onNavigateToAddBook = { currentScreen = "add_book" },
                                        onNavigateToDetails = { book ->
                                            selectedBook = book
                                            currentScreen = "book_details"
                                        }
                                    )
                                }
                                "diario" -> {
                                    DiarioScreen(
                                        booksWithLogs = booksWithLogs,
                                        onAddNoteClick = {
                                            selectedLogToEdit = null
                                            selectedBook = null
                                            currentScreen = "novo_registro"
                                        },
                                        onEditLog = { logToEdit ->
                                            selectedLogToEdit = logToEdit
                                            currentScreen = "novo_registro"
                                        },
                                        onDeleteLog = { logToDelete ->
                                            shelfViewModel.deleteLog(logToDelete)
                                        },
                                        onSelectBookDetails = { book ->
                                            selectedBook = book
                                            currentScreen = "book_details"
                                        }
                                    )
                                }
                                "filtros" -> {
                                    FiltrosScreen(
                                        onCloseClick = { currentScreen = "shelf" },
                                        onApplyFilters = { selectedGenres, selectedStatus, sortBy ->
                                            // Passa os filtros selecionados para o ViewModel tratar na estante
                                            shelfViewModel.applyFilters(selectedGenres, selectedStatus, sortBy)
                                            currentScreen = "shelf"
                                        },
                                        onClearFilters = {
                                            // Limpa os filtros no ViewModel e retorna à estante completa
                                            shelfViewModel.clearFilters()
                                            currentScreen = "shelf"
                                        }
                                    )
                                }
                                "add_book" -> {
                                    AddBookScreen(
                                        viewModel = shelfViewModel,
                                        onBookSaved = { currentScreen = "shelf" },
                                        onBack = { currentScreen = "shelf" }
                                    )
                                }
                                "book_details" -> {
                                    selectedBook?.let { book ->
                                        val currentBookLogs = booksWithLogs
                                            .find { it.estante.id == book.id }
                                            ?.logs ?: emptyList()

                                        BookDetailsScreen(
                                            book = book,
                                            bookLogs = currentBookLogs,
                                            onBackClick = { currentScreen = "shelf" },
                                            onNavigateToUpdateProgress = {
                                                currentScreen = "update_progress"
                                            },
                                            onNavigateToAddLog = {
                                                selectedLogToEdit = null
                                                currentScreen = "novo_registro"
                                            },
                                            onEditLog = { logToEdit ->
                                                selectedLogToEdit = logToEdit
                                                currentScreen = "novo_registro"
                                            },
                                            onDeleteLog = { logToDelete ->
                                                shelfViewModel.deleteLog(logToDelete)
                                            }
                                        )
                                    }
                                }
                                "update_progress" -> {
                                    selectedBook?.let { book ->
                                        AtualizarProgressoScreen(
                                            book = book,
                                            viewModel = shelfViewModel,
                                            onBackClick = { currentScreen = "book_details" },
                                            onProgressSaved = { currentScreen = "shelf" }
                                        )
                                    }
                                }
                                "novo_registro" -> {
                                    NovoRegistroScreen(
                                        viewModel = shelfViewModel,
                                        logToEdit = selectedLogToEdit,
                                        onBackClick = {
                                            selectedLogToEdit = null
                                            currentScreen = if (selectedBook != null) "book_details" else "diario"
                                        },
                                        onSaveRegistro = { tipo, livro, pagina, nota, favorita ->
                                            if (livro != null && nota.isNotBlank()) {
                                                val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                                                if (selectedLogToEdit == null) {
                                                    val newLog = ReadingLogEntity(
                                                        bookId = livro.id,
                                                        type = tipo,
                                                        page = pagina,
                                                        date = currentDate,
                                                        notes = nota,
                                                        isFavorite = favorita
                                                    )
                                                    shelfViewModel.insertReadingLog(newLog)
                                                } else {
                                                    val updatedLog = selectedLogToEdit!!.copy(
                                                        bookId = livro.id,
                                                        type = tipo,
                                                        page = pagina,
                                                        date = currentDate,
                                                        notes = nota,
                                                        isFavorite = favorita
                                                    )
                                                    shelfViewModel.updateReadingLog(updatedLog)
                                                }
                                            }
                                            selectedLogToEdit = null
                                            currentScreen = if (selectedBook != null) "book_details" else "diario"
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
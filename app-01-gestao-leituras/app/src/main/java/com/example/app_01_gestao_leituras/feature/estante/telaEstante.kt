package com.example.app_01_gestao_leituras.feature.estante
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_01_gestao_leituras.model.Estante

// Cores de status/marca fixas — não acompanham o tema claro/escuro por serem cores semânticas.
private val RedBadge = Color(0xFFD9534F)
private val GreenChip = Color(0xFF1B5E20)
private val SoftRed = Color(0xFFE57373)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun telaEstante(
    viewModel: ExibirEstante,
    onNavigateToAddBook: () -> Unit,
    onNavigateToDetails: (Estante) -> Unit,
    onNavigateToSettings: () -> Unit = {}
) {
    val books by viewModel.books.collectAsState()

    val selectedGenres = viewModel.selectedGenresFilter
    val selectedStatus = viewModel.selectedStatusFilter
    val sortBy = viewModel.sortByOption

    var selectedQuickFilter by remember { mutableStateOf("Todos") }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Estante",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configurações",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddBook,
                containerColor = GreenChip,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Livro")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChipItem("Todos", selectedQuickFilter == "Todos") { selectedQuickFilter = "Todos" }
                FilterChipItem("Quero ler", selectedQuickFilter == "Quero ler") { selectedQuickFilter = "Quero ler" }
                FilterChipItem("Lendo", selectedQuickFilter == "Lendo") { selectedQuickFilter = "Lendo" }
                FilterChipItem("Lido", selectedQuickFilter == "Lido") { selectedQuickFilter = "Lido" }
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                placeholder = { Text("Buscar por título, autor ou gênero") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpar busca")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val processedBooks = remember(books, selectedQuickFilter, selectedGenres, selectedStatus, sortBy, searchQuery) {
                var list = books

                if (selectedQuickFilter != "Todos") {
                    list = list.filter { it.status.equals(selectedQuickFilter, ignoreCase = true) }
                }

                if (!selectedStatus.isNullOrBlank()) {
                    list = list.filter { it.status.equals(selectedStatus, ignoreCase = true) }
                }

                if (selectedGenres.isNotEmpty()) {
                    list = list.filter { book ->
                        selectedGenres.any { genre -> book.category.contains(genre, ignoreCase = true) }
                    }
                }

                if (searchQuery.isNotBlank()) {
                    val query = searchQuery.trim()
                    list = list.filter { book ->
                        book.title.contains(query, ignoreCase = true) ||
                                book.author.contains(query, ignoreCase = true) ||
                                book.category.contains(query, ignoreCase = true)
                    }
                }

                list = when (sortBy) {
                    "Título (A-Z)" -> list.sortedBy { it.title }
                    "Autor(A-Z)" -> list.sortedBy { it.author }
                    "Progresso de Leitura" -> list.sortedByDescending {
                        if (it.totalPages > 0) (it.currentPage * 100) / it.totalPages else 0
                    }
                    else -> list
                }

                list
            }

            if (processedBooks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Nenhum livro encontrado",
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        if (searchQuery.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tente buscar por outro título, autor ou gênero",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(processedBooks) { book ->
                        BookCardItem(
                            book = book,
                            onDetail = { selectedBook ->
                                onNavigateToDetails(selectedBook)
                            },
                            onEdit = { selectedBook ->
                                onNavigateToDetails(selectedBook)
                            },
                            onDelete = { selectedBook ->
                                viewModel.deleteBook(selectedBook)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChipItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) GreenChip else MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun BookCardItem(
    book: Estante,
    onDetail: (Estante) -> Unit,
    onEdit: (Estante) -> Unit,
    onDelete: (Estante) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val initials = remember(book.title) {
        book.title.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercase() }.joinToString("")
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(width = 65.dp, height = 85.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SoftRed),
                    contentAlignment = Alignment.Center
                ) {
                    if (!book.coverPhotoUri.isNullOrBlank()) {
                        AsyncImage(
                            model = book.coverPhotoUri,
                            contentDescription = "Capa do Livro",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(
                            text = initials,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = book.author,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RedBadge.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = book.status,
                                color = RedBadge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                maxLines = 1
                            )
                        }

                        Text(
                            text = book.category,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onDetail(book) }, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "Detalhes",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = { onEdit(book) }, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = { showDeleteDialog = true }, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Excluir",
                        tint = RedBadge
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Excluir Livro") },
            text = { Text(text = "Tem certeza de que deseja excluir '${book.title}' da sua estante?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete(book)
                    }
                ) {
                    Text("Excluir", color = RedBadge, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
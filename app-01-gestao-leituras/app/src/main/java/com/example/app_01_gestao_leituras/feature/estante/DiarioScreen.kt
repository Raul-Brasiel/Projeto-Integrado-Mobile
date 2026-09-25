package com.example.app_01_gestao_leituras.feature.diario
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.EstanteWithLogs
import com.example.app_01_gestao_leituras.model.ReadingLogEntity

private val PrimaryPurple = Color(0xFF4A2B6F)
private val BackgroundCream = Color(0xFFFBF9F1)
private val CardBackground = Color(0xFFFFFFFF)
private val GreenButton = Color(0xFF1B5E20)
private val SoftRed = Color(0xFFE57373)
private val SoftYellow = Color(0xFFFFF9C4)

@Composable
fun DiarioScreen(
    booksWithLogs: List<EstanteWithLogs>,
    onAddNoteClick: () -> Unit,
    onEditLog: (ReadingLogEntity) -> Unit,
    onDeleteLog: (ReadingLogEntity) -> Unit,
    onSelectBookDetails: (Estante) -> Unit
) {
    var selectedFilterBookTitle by remember { mutableStateOf<String?>("Todos os livros") }

    val allLogsWithBook = remember(booksWithLogs) {
        booksWithLogs.flatMap { item ->
            item.logs.map { log -> Pair(item.estante, log) }
        }
    }

    val filteredLogs = remember(allLogsWithBook, selectedFilterBookTitle) {
        if (selectedFilterBookTitle == "Todos os livros" || selectedFilterBookTitle == null) {
            allLogsWithBook
        } else {
            allLogsWithBook.filter { it.first.title == selectedFilterBookTitle }
        }
    }

    val availableBooks = booksWithLogs.map { it.estante }

    Scaffold(
        containerColor = BackgroundCream,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick,
                containerColor = GreenButton,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Registro")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Diário & notas",
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilterBookTitle == "Todos os livros",
                    onClick = { selectedFilterBookTitle = "Todos os livros" },
                    label = { Text("Todos os livros") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GreenButton,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(20.dp)
                )

                availableBooks.forEach { book ->
                    val isSelected = selectedFilterBookTitle == book.title
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilterBookTitle = book.title },
                        label = { Text(book.title, maxLines = 1) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GreenButton,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (filteredLogs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum registro encontrado.",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    filteredLogs.forEach { (book, log) ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (log.isFavorite) SoftYellow else CardBackground
                            ),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onSelectBookDetails(book)
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = PrimaryPurple.copy(alpha = 0.1f)
                                    ) {
                                        Text(
                                            text = log.type,
                                            color = PrimaryPurple,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }

                                    Row {
                                        IconButton(
                                            onClick = { onEditLog(log) },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Editar",
                                                tint = PrimaryPurple
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(4.dp))
                                        IconButton(
                                            onClick = { onDeleteLog(log) },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Excluir",
                                                tint = SoftRed
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "“${log.notes}”",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = book.title,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (log.page.isNotBlank()) {
                                        Text(
                                            text = "Pág. ${log.page}",
                                            fontSize = 11.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
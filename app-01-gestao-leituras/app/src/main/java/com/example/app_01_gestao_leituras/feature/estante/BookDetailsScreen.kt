// Arquivo: feature/estante/BookDetailsScreen.kt
package com.example.app_01_gestao_leituras.feature.estante

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.ReadingLogEntity

private val PrimaryPurple = Color(0xFF4A2B6F)
private val BackgroundCream = Color(0xFFFBF9F1)
private val SoftRed = Color(0xFFE57373)
private val StarYellow = Color(0xFFFFC107)
private val GreenButton = Color(0xFF1B5E20)
private val TabContainerColor = Color(0xFFDCD6D0)
private val ChipSelectedColor = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    book: Estante,
    bookLogs: List<ReadingLogEntity> = emptyList(),
    onBackClick: () -> Unit,
    onNavigateToUpdateProgress: () -> Unit,
    onNavigateToAddLog: () -> Unit,
    onEditLog: (ReadingLogEntity) -> Unit = {},
    onDeleteLog: (ReadingLogEntity) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("Sobre") }

    val totalPages = book.totalPages.coerceAtLeast(1)
    val currentPage = book.currentPage.coerceIn(0, totalPages)
    val progressPercentage = ((currentPage.toFloat() / totalPages.toFloat()) * 100).coerceIn(0f, 100f).toInt()
    val pagesRemaining = (totalPages - currentPage).coerceAtLeast(0)

    val initials = remember(book.title) {
        book.title.split(" ").take(3).mapNotNull { it.firstOrNull()?.uppercase() }.joinToString("")
    }

    Scaffold(
        containerColor = BackgroundCream,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalhes",
                        color = PrimaryPurple,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = PrimaryPurple
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(Color.Transparent, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Concluído",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCream)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Cabeçalho: Capa (com suporte a imagem do Coil) + Informações principais
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 110.dp, height = 150.dp)
                        .clip(RoundedCornerShape(12.dp))
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
                            fontSize = 28.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = book.author,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row {
                        for (i in 1..5) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (i <= book.rating) StarYellow else Color.LightGray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SoftRed.copy(alpha = 0.4f),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = book.status,
                                color = SoftRed,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Seção de Progresso
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$progressPercentage%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Página $currentPage de $totalPages",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Progresso de Leitura",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Abas (Sobre, Progresso, Diário)
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = TabContainerColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("Sobre", "Progresso", "Diário").forEach { tab ->
                        val isSelected = selectedTab == tab
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) ChipSelectedColor else Color.Transparent,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                TextButton(
                                    onClick = { selectedTab = tab },
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = tab,
                                        color = if (isSelected) Color.Black else Color.DarkGray,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Conteúdo da Aba Ativa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.TopStart
            ) {
                when (selectedTab) {
                    "Sobre" -> {
                        val synopsis = book.description
                        Text(
                            text = if (synopsis.isNotBlank()) synopsis else "Nenhuma sinopse cadastrada para este livro.",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            lineHeight = 20.sp
                        )
                    }
                    "Progresso" -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Visão Geral do Progresso",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PrimaryPurple
                            )

                            LinearProgressIndicator(
                                progress = { (currentPage.toFloat() / totalPages.toFloat()).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = GreenButton,
                                trackColor = Color.LightGray
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Lidas: $currentPage pág.",
                                    fontSize = 13.sp,
                                    color = Color.DarkGray
                                )
                                Text(
                                    text = "Faltam: $pagesRemaining pág.",
                                    fontSize = 13.sp,
                                    color = Color.DarkGray
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Status Atual: ${book.status}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (progressPercentage == 100)
                                            "Parabéns! Você concluiu a leitura deste livro."
                                        else
                                            "Continue avançando para alcançar sua meta!",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                    "Diário" -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = onNavigateToAddLog,
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Adicionar registro",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Adicionar Nota / Diário",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            if (bookLogs.isEmpty()) {
                                Text(
                                    text = "Nenhuma nota ou diário registrado para este livro.",
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    bookLogs.forEach { log ->
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = Color.White),
                                            shape = RoundedCornerShape(12.dp),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                            modifier = Modifier.fillMaxWidth()
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
                                                    text = book.title,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                    color = Color.Black
                                                )

                                                if (log.type == "Nota" && log.page.isNotBlank()) {
                                                    Text(
                                                        text = "Página: ${log.page}",
                                                        fontSize = 12.sp,
                                                        color = Color.Gray
                                                    )
                                                }

                                                Spacer(modifier = Modifier.height(6.dp))

                                                Text(
                                                    text = log.notes,
                                                    fontSize = 13.sp,
                                                    color = Color.DarkGray
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Text(
                                                    text = "Modificado em: ${log.date}",
                                                    fontSize = 11.sp,
                                                    color = Color.LightGray
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

            Spacer(modifier = Modifier.height(30.dp))

            if (selectedTab == "Progresso") {
                Button(
                    onClick = onNavigateToUpdateProgress,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenButton),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Atualizar Progresso",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
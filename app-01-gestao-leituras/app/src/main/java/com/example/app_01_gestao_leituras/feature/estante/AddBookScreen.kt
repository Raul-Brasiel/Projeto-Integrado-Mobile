// Arquivo: feature/Estante/AddBookScreen.kt
package com.example.app_01_gestao_leituras.feature.estante

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val PrimaryPurple = Color(0xFF4A2B6F)
private val BackgroundCream = Color(0xFFFBF9F1)
private val GreenChip = Color(0xFF1B5E20)
private val StarYellow = Color(0xFFFFC107)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    viewModel: ExibirEstante,
    onBookSaved: () -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var totalPages by remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(4) }

    var selectedCategory by remember { mutableStateOf("Fantasia") }
    var selectedStatus by remember { mutableStateOf("Quero ler") }

    var coverPhotoUri by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coverPhotoUri = it.toString()
        }
    }

    val categories = listOf("Fantasia", "Romance", "Ficção", "Biografia", "Ficção Científica", "Poesia")
    val statuses = listOf("Quero ler", "Lendo", "Lido")

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0xFFEFECE6),
        unfocusedContainerColor = Color(0xFFEFECE6),
        focusedBorderColor = PrimaryPurple,
        unfocusedBorderColor = Color.Transparent
    )

    Scaffold(
        containerColor = BackgroundCream,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Novo Livro",
                        color = PrimaryPurple,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = PrimaryPurple
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
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.4f))
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (coverPhotoUri != null) {
                    AsyncImage(
                        model = coverPhotoUri,
                        contentDescription = "Capa do Livro",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Adicionar Capa",
                        tint = PrimaryPurple,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Título", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("ex: Harry Potter e a Pedra Filosofal", color = Color.Gray) },
                    shape = RoundedCornerShape(16.dp),
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Autor", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    placeholder = { Text("ex: J. K. Rowling", color = Color.Gray) },
                    shape = RoundedCornerShape(16.dp),
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Lista / Status", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    statuses.forEach { status ->
                        FilterChip(
                            selected = selectedStatus == status,
                            onClick = { selectedStatus = status },
                            label = { Text(status) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryPurple,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total de Páginas e Página Atual
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Total de Páginas", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = totalPages,
                        onValueChange = { totalPages = it },
                        placeholder = { Text("ex: 224", color = Color.Gray) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(16.dp),
                        colors = textFieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (selectedStatus == "Lendo" || selectedStatus == "Lido") {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Página Atual", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = currentPage,
                            onValueChange = { currentPage = it },
                            placeholder = { Text("ex: 50", color = Color.Gray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(16.dp),
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Avaliação em Estrelas
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Avaliação", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (i in 1..5) {
                        IconButton(
                            onClick = { rating = i },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "$i estrelas",
                                tint = if (i <= rating) StarYellow else Color.LightGray,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descrição / Sinopse
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Descrição / Sinopse", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Escreva uma breve sinopse sobre o livro...", color = Color.Gray) },
                    shape = RoundedCornerShape(16.dp),
                    colors = textFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    maxLines = 4
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gênero
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Gênero", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    categories.chunked(3).forEach { rowCategories ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowCategories.forEach { category ->
                                Surface(
                                    onClick = { selectedCategory = category },
                                    shape = RoundedCornerShape(16.dp),
                                    color = if (selectedCategory == category) PrimaryPurple else Color.White,
                                    shadowElevation = 1.dp,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = category,
                                            color = if (selectedCategory == category) Color.White else Color.Black,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botão Salvar Livro
            Button(
                onClick = {
                    if (title.isNotBlank() && author.isNotBlank()) {
                        val pagesInt = totalPages.toIntOrNull() ?: 0
                        val currentPagesInt = if (selectedStatus == "Lido") {
                            pagesInt
                        } else {
                            currentPage.toIntOrNull() ?: 0
                        }

                        viewModel.registerBook(
                            title = title,
                            author = author,
                            totalPages = pagesInt,
                            currentPage = currentPagesInt,
                            category = selectedCategory,
                            status = selectedStatus,
                            description = description,
                            rating = rating,
                            coverPhotoUri = coverPhotoUri
                        )
                        onBookSaved()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenChip),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Salvar Livro", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
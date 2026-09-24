package com.example.app_01_gestao_leituras.feature.estante
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_01_gestao_leituras.model.Estante
import com.example.app_01_gestao_leituras.model.ReadingLogEntity

private val PrimaryPurple = Color(0xFF4A2B6F)
private val BackgroundCream = Color(0xFFFBF9F1)
private val CardBackground = Color(0xFFFFFFFF)
private val SoftInputBackground = Color(0xFFF3EFE6)
private val SoftRed = Color(0xFFE57373)
private val GreenButton = Color(0xFF1B5E20)
private val TabContainerColor = Color(0xFFDCD6D0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoRegistroScreen(
    viewModel: ExibirEstante,
    logToEdit: ReadingLogEntity? = null,
    onBackClick: () -> Unit,
    onSaveRegistro: (tipo: String, livro: Estante?, pagina: String, nota: String, favorita: Boolean) -> Unit
) {
    val books by viewModel.books.collectAsState()

    var selectedType by remember { mutableStateOf(logToEdit?.type ?: "Nota") }
    var selectedBook by remember { mutableStateOf<Estante?>(null) }
    var expandedBookMenu by remember { mutableStateOf(false) }

    var paginaText by remember { mutableStateOf(logToEdit?.page ?: "") }
    var notaText by remember { mutableStateOf(logToEdit?.notes ?: "") }
    var isFavorite by remember { mutableStateOf(logToEdit?.isFavorite ?: false) }

    LaunchedEffect(books, logToEdit) {
        if (logToEdit != null) {
            selectedBook = books.find { it.id == logToEdit.bookId }
        } else if (selectedBook == null && books.isNotEmpty()) {
            selectedBook = books.first()
        }
    }

    Scaffold(
        containerColor = BackgroundCream,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(40.dp)
                                .background(TabContainerColor.copy(alpha = 0.4f), shape = CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = PrimaryPurple
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (logToEdit == null) "Novo registro" else "Editar registro",
                            color = PrimaryPurple,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {},
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(24.dp),
                color = TabContainerColor.copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("Diário", "Nota").forEach { tipo ->
                        val isSelected = selectedType == tipo
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) CardBackground else Color.Transparent,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onClick = { selectedType = tipo }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tipo,
                                    color = if (isSelected) PrimaryPurple else Color.DarkGray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Livro",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = CardBackground,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        onClick = { expandedBookMenu = true }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 40.dp, height = 48.dp)
                                        .background(SoftRed, shape = RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val initials = selectedBook?.title?.split(" ")
                                        ?.take(2)
                                        ?.mapNotNull { it.firstOrNull()?.uppercase() }
                                        ?.joinToString("") ?: "LV"
                                    Text(
                                        text = initials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = selectedBook?.title ?: "Selecione um livro",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    maxLines = 1
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Expandir",
                                tint = Color.Gray
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = expandedBookMenu,
                        onDismissRequest = { expandedBookMenu = false },
                        modifier = Modifier.background(CardBackground)
                    ) {
                        if (books.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("Nenhum livro cadastrado") },
                                onClick = { expandedBookMenu = false }
                            )
                        } else {
                            books.forEach { book ->
                                DropdownMenuItem(
                                    text = { Text(book.title, fontWeight = FontWeight.Medium) },
                                    onClick = {
                                        selectedBook = book
                                        expandedBookMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (selectedType == "Nota") {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Página",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    OutlinedTextField(
                        value = paginaText,
                        onValueChange = { paginaText = it },
                        placeholder = { Text("ex: 154", color = Color.Gray) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SoftInputBackground,
                            unfocusedContainerColor = SoftInputBackground,
                            disabledContainerColor = SoftInputBackground,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = if (selectedType == "Nota") "Sua nota" else "Anotação do Diário",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                OutlinedTextField(
                    value = notaText,
                    onValueChange = { notaText = it },
                    placeholder = {
                        Text(
                            text = if (selectedType == "Nota")
                                "Escreva sua anotação, reflexão ou citação..."
                            else
                                "Como foi sua leitura hoje?",
                            color = Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SoftInputBackground,
                        unfocusedContainerColor = SoftInputBackground,
                        disabledContainerColor = SoftInputBackground,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                )
            }

            if (selectedType == "Nota") {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SoftInputBackground,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = PrimaryPurple,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Marcar como citação favorita",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }

                        Switch(
                            checked = isFavorite,
                            onCheckedChange = { isFavorite = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = GreenButton,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color.LightGray
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    onSaveRegistro(
                        selectedType,
                        selectedBook,
                        paginaText,
                        notaText,
                        isFavorite
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (logToEdit == null) "Salvar registro" else "Salvar alterações",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
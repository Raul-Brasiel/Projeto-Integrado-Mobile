
package com.example.app_01_gestao_leituras.feature.estante

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BackgroundCream = Color(0xFFFBF9F1)
private val DarkGreen = Color(0xFF1B5E20)
private val LightGrayButton = Color(0xFFF0F0F0)
private val ChipSelectedColor = Color(0xFF1B5E20)
private val ChipUnselectedColor = Color(0xFFFFFFFF)

@Composable
fun FiltrosScreen(
    onCloseClick: () -> Unit,
    onApplyFilters: (selectedGenres: List<String>, selectedStatus: String, sortBy: String) -> Unit,
    onClearFilters: () -> Unit
) {
    val genresList = listOf("Ficção", "Fantasia", "Romance", "Não Ficção", "Biografia", "Poesia", "Ficção Cientifica", "Terror")
    val selectedGenres = remember { mutableStateMapOf<String, Boolean>().apply { genresList.forEach { this[it] = (it == "Ficção" || it == "Fantasia") } } }

    var selectedStatus by remember { mutableStateOf("Lendo") }
    val statusList = listOf("Quero ler", "Lido", "Lendo")

    var sortBy by remember { mutableStateOf("Título (A-Z)") }
    val sortOptions = listOf("Título (A-Z)", "Autor(A-Z)", "Progresso de Leitura")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundCream
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtros",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFEFECE6), shape = RoundedCornerShape(12.dp))
                ) {
                    Text(text = "✕", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                val rows = genresList.chunked(3)
                rows.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { genre ->
                            val isSelected = selectedGenres[genre] == true
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedGenres[genre] = !isSelected },
                                label = {
                                    Text(
                                        text = genre,
                                        color = if (isSelected) Color.White else Color.Black,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ChipSelectedColor,
                                    containerColor = ChipUnselectedColor
                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Status",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    statusList.forEach { status ->
                        val isSelected = selectedStatus == status
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedStatus = status },
                            label = {
                                Text(
                                    text = status,
                                    color = if (isSelected) Color.White else Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ChipSelectedColor,
                                containerColor = ChipUnselectedColor
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Ordenar por",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                sortOptions.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (sortBy == option),
                            onClick = { sortBy = option },
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        onClearFilters()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = LightGrayButton),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Limpar",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Button(
                    onClick = {
                        val activeGenres = selectedGenres.filter { it.value }.keys.toList()
                        onApplyFilters(activeGenres, selectedStatus, sortBy)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Aplicar\nFiltros",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
package com.example.app_02_treinos_performance.feature.detalhesFicha

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.example.app_02_treinos_performance.core.designSystem.components.ItemDetalheExercicioCard
import com.example.app_02_treinos_performance.data.model.ItemFichaComExercicio

@Composable
fun DetalhesFichaScreen(
    viewModel: DetalhesFichaViewModel,
    onVoltar: () -> Unit,
    onEditarFicha: (Long) -> Unit
){
    val uiState by viewModel.uiState.collectAsState()

    DetalhesFichaContent(
        uiState = uiState,
        onVoltar = onVoltar,
        onEditarFicha = { onEditarFicha(uiState.fichaId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetalhesFichaContent(
    uiState: DetalhesFichaUiState,
    onVoltar: () -> Unit,
    onEditarFicha: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.nomeFicha, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditarFicha,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Editar ficha")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.itens, key = { it.id }) { item ->
                ItemDetalheExercicioCard(
                    titulo = item.nomeExercicio,
                    subtitulo = formatarSerieRepeticaoCarga(item)
                )
            }
        }
    }
}

private fun formatarSerieRepeticaoCarga(item: ItemFichaComExercicio): String {
    val carga = if (item.cargaKg == item.cargaKg.toInt().toFloat()) {
        item.cargaKg.toInt().toString()
    } else {
        item.cargaKg.toString()
    }
    return "${item.series}×${item.repeticoes} - ${carga}kg"
}
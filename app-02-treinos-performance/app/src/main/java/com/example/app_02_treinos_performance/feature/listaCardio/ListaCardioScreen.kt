package com.example.app_02_treinos_performance.feature.listaCardio

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_02_treinos_performance.data.model.Cardio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListaCardioScreen(
    viewModel: ListaCardioViewModel,
    onAdicionarCardio: () -> Unit,
    onEditarCardio: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ListaCardioContent(
        uiState = uiState,
        onAdicionarCardio = onAdicionarCardio,
        onEditarCardio = onEditarCardio
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListaCardioContent(
    uiState: ListaCardioUiState,
    onAdicionarCardio: () -> Unit,
    onEditarCardio: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Cárdios", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdicionarCardio,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar cárdio")
            }
        }
    ) { innerPadding ->
        if (uiState.cardios.isEmpty() && !uiState.carregando) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum cárdio registrado ainda.\nToque em + para começar.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.cardios, key = { it.id }) { cardio ->
                    CardioCard(cardio = cardio, onEditarClick = { onEditarCardio(cardio.id) })
                }
            }
        }
    }
}

@Composable
private fun CardioCard(cardio: Cardio, onEditarClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 4.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${cardio.nome} - ${formatarDistancia(cardio.distanciaKm)}km",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${formatarData(cardio.dataEpochMillis)} - pace ${formatarPace(cardio.distanciaKm, cardio.tempoSegundos)} / km",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            IconButton(onClick = onEditarClick) {
                Icon(Icons.Default.Edit, contentDescription = "Editar cárdio", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

private fun formatarDistancia(distanciaKm: Float): String {
    return if (distanciaKm == distanciaKm.toInt().toFloat()) {
        distanciaKm.toInt().toString()
    } else {
        distanciaKm.toString()
    }
}

private fun formatarData(epochMillis: Long): String {
    val formato = SimpleDateFormat("dd MMM", Locale("pt", "BR"))
    return formato.format(Date(epochMillis)).removeSuffix(".")
}

private fun formatarPace(distanciaKm: Float, tempoSegundos: Int): String {
    if (distanciaKm <= 0f) return "--:--"
    val paceSegundos = (tempoSegundos / distanciaKm).toInt()
    val minutos = paceSegundos / 60
    val segundos = paceSegundos % 60
    return "%d:%02d".format(minutos, segundos)
}
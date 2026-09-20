package com.example.app_02_treinos_performance.feature.registrarCarga

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app_02_treinos_performance.data.model.SerieRascunho

@Composable
fun RegistrarCargaScreen(
    viewModel: RegistrarCargaViewModel,
    onVoltar: () -> Unit,
    onSalvo: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.salvo) {
        if (uiState.salvo) onSalvo()
    }

    RegistrarCargaContent(
        uiState = uiState,
        onVoltar = onVoltar,
        onPesoAlterado = viewModel::onPesoAlterado,
        onRepeticoesAlterado = viewModel::onRepeticoesAlterado,
        onAdicionarSerie = viewModel::adicionarSerie,
        onSalvar = viewModel::salvar,
        onExcluirSerie = viewModel::removerSerie
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrarCargaContent(
    uiState: RegistrarCargaUiState,
    onVoltar: () -> Unit,
    onPesoAlterado: (Int, String) -> Unit,
    onRepeticoesAlterado: (Int, String) -> Unit,
    onAdicionarSerie: () -> Unit,
    onSalvar: () -> Unit,
    onExcluirSerie: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.nomeExercicio, fontWeight = FontWeight.Bold) },
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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 4.dp)) {
                    Text("Série", modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Peso(kg)", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Reps", modifier = Modifier.weight(1f), textAlign = TextAlign.End, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            itemsIndexed(uiState.series) { indice, serie ->
                LinhaSerie(
                    serie = serie,
                    onPesoAlterado = { onPesoAlterado(indice, it) },
                    onRepeticoesAlterado = { onRepeticoesAlterado(indice, it) },
                    onExcluirClick = { onExcluirSerie(indice) }
                )
            }

            item {
                Column {
                    OutlinedButton(
                        onClick = onAdicionarSerie,
                        modifier = Modifier.fillMaxWidth().height(48.dp).padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        Text("  Adicionar série", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onSalvar,
                        enabled = !uiState.salvando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 24.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Salvar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun LinhaSerie(
    serie: SerieRascunho,
    onPesoAlterado: (String) -> Unit,
    onRepeticoesAlterado: (String) -> Unit,
    onExcluirClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(serie.numero.toString(), modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.primary)
            TextField(
                value = serie.pesoKg,
                onValueChange = onPesoAlterado,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            TextField(
                value = serie.repeticoes,
                onValueChange = onRepeticoesAlterado,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(textAlign = TextAlign.End),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = onExcluirClick) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir série", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
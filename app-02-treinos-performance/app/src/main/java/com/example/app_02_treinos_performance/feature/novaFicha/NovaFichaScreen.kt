package com.example.app_02_treinos_performance.feature.novaFicha

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_02_treinos_performance.core.designSystem.components.ItemExercicioCard
import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho

@Composable
fun NovaFichaScreen(
    viewModel: NovaFichaViewModel,
    onVoltar: () -> Unit,
    onFichaSalva: () -> Unit,
    onAdicionarExercicio: () -> Unit,
    onEditarExercicio: (indice: Int, item: ItemFichaRascunho) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.fichaSalva) {
        if (uiState.fichaSalva) onFichaSalva()
    }

    NovaFichaContent(
        uiState = uiState,
        onVoltar = onVoltar,
        onNomeAlterado = viewModel::onNomeAlterado,
        onAdicionarExercicio = onAdicionarExercicio,
        onEditarExercicio = onEditarExercicio,
        onRemoverExercicio = viewModel::removerItem,
        onSalvar = viewModel::salvar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NovaFichaContent(
    uiState: NovaFichaUiState,
    onVoltar: () -> Unit,
    onNomeAlterado: (String) -> Unit,
    onAdicionarExercicio: () -> Unit,
    onEditarExercicio: (Int, ItemFichaRascunho) -> Unit,
    onRemoverExercicio: (Int) -> Unit,
    onSalvar: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (uiState.fichaId != null) "Editar ficha" else "Nova ficha",
                        fontWeight = FontWeight.Bold
                    )
                },
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
                Text(
                    text = "Nome da rotina",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 24.dp)
                )
                OutlinedTextField(
                    value = uiState.nome,
                    onValueChange = onNomeAlterado,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = uiState.erroNome != null,
                    supportingText = uiState.erroNome?.let { { Text(it) } },
                    singleLine = true
                )
                Text(
                    text = "Exercícios",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )
            }

            itemsIndexed(uiState.itens) { indice, item ->
                ItemExercicioCard(
                    titulo = item.nomeExercicio,
                    onEditarClick = { onEditarExercicio(indice, item) },
                    onExcluirClick = { onRemoverExercicio(indice) }
                )
            }

            item {
                Column {
                    OutlinedButton(
                        onClick = onAdicionarExercicio,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        Text(
                            text = "  Adicionar exercício",
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
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
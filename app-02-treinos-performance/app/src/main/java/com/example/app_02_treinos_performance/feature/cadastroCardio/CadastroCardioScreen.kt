package com.example.app_02_treinos_performance.feature.cadastroCardio

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CadastroCardioScreen(
    viewModel: CadastroCardioViewModel,
    onVoltar: () -> Unit,
    onSalvo: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.salvo) {
        if (uiState.salvo) onSalvo()
    }

    CadastroCardioContent(
        uiState = uiState,
        onVoltar = onVoltar,
        onNomeAlterado = viewModel::onNomeAlterado,
        onDistanciaAlterada = viewModel::onDistanciaAlterada,
        onTempoMinutosAlterado = viewModel::onTempoMinutosAlterado,
        onTempoSegundosAlterado = viewModel::onTempoSegundosAlterado,
        onSalvar = viewModel::salvar,
        onExcluir = viewModel::excluir
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CadastroCardioContent(
    uiState: CadastroCardioUiState,
    onVoltar: () -> Unit,
    onNomeAlterado: (String) -> Unit,
    onDistanciaAlterada: (String) -> Unit,
    onTempoMinutosAlterado: (String) -> Unit,
    onTempoSegundosAlterado: (String) -> Unit,
    onSalvar: () -> Unit,
    onExcluir: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (uiState.emEdicao) "Editar cárdio" else "Novo cárdio",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    if (uiState.emEdicao) {
                        IconButton(onClick = onExcluir) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Excluir cárdio",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
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
                    text = "Tipo de cárdio",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 24.dp)
                )
                OutlinedTextField(
                    value = uiState.nome,
                    onValueChange = onNomeAlterado,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Corrida, ciclismo, natação...") },
                    shape = RoundedCornerShape(12.dp),
                    isError = uiState.erroNome != null,
                    supportingText = uiState.erroNome?.let { { Text(it) } },
                    singleLine = true
                )

                Text(
                    text = "Distância (km)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 24.dp)
                )
                OutlinedTextField(
                    value = uiState.distanciaKm,
                    onValueChange = onDistanciaAlterada,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ex: 5.0") },
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = uiState.erroDistancia != null,
                    supportingText = uiState.erroDistancia?.let { { Text(it) } },
                    singleLine = true
                )

                Text(
                    text = "Duração",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.tempoMinutos,
                        onValueChange = onTempoMinutosAlterado,
                        modifier = Modifier.weight(1f),
                        label = { Text("Minutos") },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.erroTempo != null,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.tempoSegundos,
                        onValueChange = onTempoSegundosAlterado,
                        modifier = Modifier.weight(1f),
                        label = { Text("Segundos") },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.erroTempo != null,
                        supportingText = uiState.erroTempo?.let { { Text(it) } },
                        singleLine = true
                    )
                }

                Column {
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

                    if (uiState.emEdicao) {
                        OutlinedButton(
                            onClick = onExcluir,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(bottom = 24.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                        ) {
                            Text("Excluir cárdio", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

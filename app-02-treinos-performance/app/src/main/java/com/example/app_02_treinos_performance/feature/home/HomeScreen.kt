package com.example.app_02_treinos_performance.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_02_treinos_performance.data.model.FichaResumo
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_02_treinos_performance.core.designSystem.App02treinosperformanceTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAdicionarFicha: () -> Unit,
    onFichaClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        onAdicionarFicha = onAdicionarFicha,
        onFichaClick = onFichaClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onAdicionarFicha: () -> Unit,
    onFichaClick: (Long) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meus treinos",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdicionarFicha,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar ficha")
            }
        }
    ) { innerPadding ->
        if (uiState.fichas.isEmpty() && !uiState.carregando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhuma ficha criada ainda.\nToque em + para começar.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.fichas, key = { it.id }) { ficha ->
                    FichaCard(ficha = ficha, onClick = { onFichaClick(ficha.id) })
                }
            }
        }
    }
}

@Composable
private fun FichaCard(ficha: FichaResumo, onClick: () -> Unit) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = ficha.nome,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (ficha.quantidadeExercicios == 1) "1 exercício" else "${ficha.quantidadeExercicios} exercícios",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview() {
    App02treinosperformanceTheme {
        HomeContent(
            uiState = HomeUiState(
                fichas = listOf(
                    FichaResumo(id = 1, nome = "Treino A", quantidadeExercicios = 6),
                    FichaResumo(id = 2, nome = "Treino B", quantidadeExercicios = 6),
                    FichaResumo(id = 3, nome = "Treino C", quantidadeExercicios = 6)
                ),
                carregando = false
            ),
            onAdicionarFicha = {},
            onFichaClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Sem fichas")
@Composable
private fun HomeContentVazioPreview() {
    App02treinosperformanceTheme {
        HomeContent(
            uiState = HomeUiState(fichas = emptyList(), carregando = false),
            onAdicionarFicha = {},
            onFichaClick = {}
        )
    }
}
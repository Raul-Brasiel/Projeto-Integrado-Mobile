package com.example.app_02_treinos_performance.feature.adicionarExercicios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_02_treinos_performance.data.model.Exercicio
import com.example.app_02_treinos_performance.data.model.GruposMusculares
import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho

@Composable
fun AdicionarExercicioScreen(
    viewModel: AdicionarExercicioViewModel,
    onVoltar: (itensAdicionados: List<ItemFichaRascunho>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(uiState.mensagemAlerta) {
            uiState.mensagemAlerta?.let { msg ->
                snackbarHostState.showSnackbar(msg)
                viewModel.limparMensagemAlerta()
            }
        }

        AdicionarExercicioContent(
            uiState = uiState,
            snackbarHostState = snackbarHostState,
            onVoltar = { onVoltar(uiState.itensAdicionados) },
            onBuscaAlterada = viewModel::onBuscaAlterada,
            onFiltroClicado = viewModel::onFiltroClicado,
            onExercicioClicado = viewModel::onExercicioClicado,
            onAbrirFormularioNovoExercicio = viewModel::abrirFormularioNovoExercicio,
            onFecharFormularioNovoExercicio = viewModel::fecharFormularioNovoExercicio,
            onNomeNovoExercicioAlterado = viewModel::onNomeNovoExercicioAlterado,
            onTipoNovoExercicioSelecionado = viewModel::onTipoNovoExercicioSelecionado,
            onConfirmarNovoExercicio = viewModel::confirmarNovoExercicio
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AdicionarExercicioContent(
        uiState: AdicionarExercicioUiState,
        snackbarHostState: SnackbarHostState,
        onVoltar: () -> Unit,
        onBuscaAlterada: (String) -> Unit,
        onFiltroClicado: (String) -> Unit,
        onExercicioClicado: (Exercicio) -> Unit,
        onAbrirFormularioNovoExercicio: () -> Unit,
        onFecharFormularioNovoExercicio: () -> Unit,
        onNomeNovoExercicioAlterado: (String) -> Unit,
        onTipoNovoExercicioSelecionado: (String) -> Unit,
        onConfirmarNovoExercicio: () -> Unit
    ) {
        val listaFiltrada = uiState.catalogo.filter { exercicio ->
            val bateBusca = exercicio.nome.contains(uiState.textoBusca, ignoreCase = true)
            val bateFiltro = uiState.filtroSelecionado == null || exercicio.grupoMuscular == uiState.filtroSelecionado
            bateBusca && bateFiltro
        }

        Scaffold(
            snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) },
            topBar = {
            TopAppBar(
                title = { Text("Adicionar exercícios", fontWeight = FontWeight.Bold) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.textoBusca,
                onValueChange = onBuscaAlterada,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            LinhasDeFiltros(
                filtroSelecionado = uiState.filtroSelecionado,
                onFiltroClicado = onFiltroClicado
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (listaFiltrada.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum exercício encontrado",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                } else {
                    items(listaFiltrada, key = { it.id }) { exercicio ->
                        OutlinedCard(
                            onClick = { onExercicioClicado(exercicio) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Text(
                                text = exercicio.nome,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                item {
                    OutlinedButton(
                        onClick = onAbrirFormularioNovoExercicio,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        Text(
                            text = "  Adicionar novo exercício",
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (uiState.mostrarFormularioNovoExercicio) {
        ModalBottomSheet(
            onDismissRequest = onFecharFormularioNovoExercicio,
            sheetState = rememberModalBottomSheetState()
        ) {
            FormularioNovoExercicio(
                nome = uiState.nomeNovoExercicio,
                tipoSelecionado = uiState.tipoNovoExercicio,
                onNomeAlterado = onNomeNovoExercicioAlterado,
                onTipoSelecionado = onTipoNovoExercicioSelecionado,
                onConfirmar = onConfirmarNovoExercicio
            )
        }
    }
}

@Composable
private fun LinhasDeFiltros(
    filtroSelecionado: String?,
    onFiltroClicado: (String) -> Unit
) {
    val grupos = GruposMusculares.lista
    val primeiraLinha = grupos.take(4)
    val segundaLinha = grupos.drop(4)

    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            primeiraLinha.forEach { grupo ->
                ChipDeFiltro(grupo, grupo == filtroSelecionado, onFiltroClicado)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            segundaLinha.forEach { grupo ->
                ChipDeFiltro(grupo, grupo == filtroSelecionado, onFiltroClicado)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChipDeFiltro(
    texto: String,
    selecionado: Boolean,
    onClick: (String) -> Unit
) {
    FilterChip(
        selected = selecionado,
        onClick = { onClick(texto) },
        label = { Text(texto) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
private fun FormularioNovoExercicio(
    nome: String,
    tipoSelecionado: String?,
    onNomeAlterado: (String) -> Unit,
    onTipoSelecionado: (String) -> Unit,
    onConfirmar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Novo exercício",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = onNomeAlterado,
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tipo",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinhasDeFiltros(
            filtroSelecionado = tipoSelecionado,
            onFiltroClicado = onTipoSelecionado
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onConfirmar,
            enabled = nome.isNotBlank() && tipoSelecionado != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text("Adicionar", fontWeight = FontWeight.Bold)
        }
    }
}
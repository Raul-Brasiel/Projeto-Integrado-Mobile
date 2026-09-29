package com.example.app_02_treinos_performance.feature.adicionarExercicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_02_treinos_performance.data.model.Exercicio
import com.example.app_02_treinos_performance.data.model.ItemFichaRascunho
import com.example.app_02_treinos_performance.data.repository.ExercicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdicionarExercicioViewModel(
    private val exercicioRepository: ExercicioRepository,
    private val idsAdicionadosIniciais: List<Long>
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdicionarExercicioUiState())
    val uiState: StateFlow<AdicionarExercicioUiState> = _uiState

    init {
        viewModelScope.launch {
            exercicioRepository.listarCatalogo().collect { lista ->
                val idsAdicionadosAgora = _uiState.value.itensAdicionados.map { it.exercicioId }
                val idsFiltrados = idsAdicionadosIniciais + idsAdicionadosAgora
                val catalogoFiltrado = lista.filter { it.id !in idsFiltrados }
                _uiState.update { it.copy(catalogo = catalogoFiltrado) }
            }
        }
    }

    fun onBuscaAlterada(texto: String) {
        _uiState.update { it.copy(textoBusca = texto) }
    }

    fun onFiltroClicado(grupo: String) {
        _uiState.update {
            it.copy(filtroSelecionado = if (it.filtroSelecionado == grupo) null else grupo)
        }
    }

    fun onExercicioClicado(exercicio: Exercicio) {
        val novoItem = ItemFichaRascunho(
            exercicioId = exercicio.id,
            nomeExercicio = exercicio.nome,
            series = 0,
            repeticoes = 0,
            cargaKg = 0f
        )
        _uiState.update { estado ->
            val novoCatalogo = estado.catalogo.filter { it.id != exercicio.id }
            estado.copy(
                itensAdicionados = estado.itensAdicionados + novoItem,
                catalogo = novoCatalogo,
                mensagemAlerta = "${exercicio.nome} adicionado à ficha!"
            )
        }
    }

    fun limparMensagemAlerta() {
        _uiState.update { it.copy(mensagemAlerta = null) }
    }

    fun abrirFormularioNovoExercicio() {
        _uiState.update { it.copy(mostrarFormularioNovoExercicio = true) }
    }

    fun fecharFormularioNovoExercicio() {
        _uiState.update {
            it.copy(mostrarFormularioNovoExercicio = false, nomeNovoExercicio = "", tipoNovoExercicio = null)
        }
    }

    fun onNomeNovoExercicioAlterado(nome: String) {
        _uiState.update { it.copy(nomeNovoExercicio = nome) }
    }

    fun onTipoNovoExercicioSelecionado(tipo: String) {
        _uiState.update { it.copy(tipoNovoExercicio = tipo) }
    }

    fun confirmarNovoExercicio() {
        val estadoAtual = _uiState.value
        val nome = estadoAtual.nomeNovoExercicio.trim()
        if (nome.isBlank()) return

        viewModelScope.launch {
            exercicioRepository.salvar(
                Exercicio(nome = nome, grupoMuscular = estadoAtual.tipoNovoExercicio, criadoPeloUsuario = true)
            )
            _uiState.update {
                it.copy(mostrarFormularioNovoExercicio = false, nomeNovoExercicio = "", tipoNovoExercicio = null)
            }
        }
    }
}
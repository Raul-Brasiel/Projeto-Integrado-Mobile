package com.example.app_02_treinos_performance.feature.cadastroCardio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_02_treinos_performance.data.model.Cardio
import com.example.app_02_treinos_performance.data.repository.CardioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CadastroCardioViewModel(
    private val cardioRepository: CardioRepository,
    private val cardioIdParaEditar: Long? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(CadastroCardioUiState(cardioId = cardioIdParaEditar))
    val uiState: StateFlow<CadastroCardioUiState> = _uiState

    init {
        cardioIdParaEditar?.let { id ->
            _uiState.update { it.copy(carregando = true) }
            viewModelScope.launch {
                val cardio = cardioRepository.buscarPorId(id)
                if (cardio != null) {
                    val minutos = cardio.tempoSegundos / 60
                    val segundos = cardio.tempoSegundos % 60
                    _uiState.update {
                        it.copy(
                            cardioCarregado = cardio,
                            nome = cardio.nome,
                            distanciaKm = formatarDistancia(cardio.distanciaKm),
                            tempoMinutos = minutos.toString(),
                            tempoSegundos = segundos.toString(),
                            dataEpochMillis = cardio.dataEpochMillis,
                            carregando = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(carregando = false) }
                }
            }
        }
    }

    fun onNomeAlterado(nome: String) {
        _uiState.update { it.copy(nome = nome, erroNome = null) }
    }

    fun onDistanciaAlterada(valor: String) {
        _uiState.update { it.copy(distanciaKm = valor, erroDistancia = null) }
    }

    fun onTempoMinutosAlterado(valor: String) {
        _uiState.update { it.copy(tempoMinutos = valor, erroTempo = null) }
    }

    fun onTempoSegundosAlterado(valor: String) {
        _uiState.update { it.copy(tempoSegundos = valor, erroTempo = null) }
    }

    fun salvar() {
        val estadoAtual = _uiState.value

        val nome = estadoAtual.nome.trim()
        val distancia = estadoAtual.distanciaKm.replace(",", ".").toFloatOrNull()
        val minutos = estadoAtual.tempoMinutos.toIntOrNull() ?: 0
        val segundos = estadoAtual.tempoSegundos.toIntOrNull() ?: 0
        val tempoTotalSegundos = minutos * 60 + segundos

        var temErro = false
        if (nome.isBlank()) {
            _uiState.update { it.copy(erroNome = "Informe o tipo de cárdio") }
            temErro = true
        }
        if (distancia == null || distancia <= 0f) {
            _uiState.update { it.copy(erroDistancia = "Informe uma distância válida") }
            temErro = true
        }
        if (tempoTotalSegundos <= 0) {
            _uiState.update { it.copy(erroTempo = "Informe a duração") }
            temErro = true
        }
        if (temErro) return

        _uiState.update { it.copy(salvando = true) }
        viewModelScope.launch {
            val cardio = Cardio(
                id = estadoAtual.cardioId ?: 0,
                nome = nome,
                distanciaKm = distancia!!,
                tempoSegundos = tempoTotalSegundos,
                dataEpochMillis = estadoAtual.dataEpochMillis
            )

            if (estadoAtual.emEdicao) {
                cardioRepository.atualizar(cardio)
            } else {
                cardioRepository.salvar(cardio)
            }

            _uiState.update { it.copy(salvando = false, salvo = true) }
        }
    }

    fun excluir() {
        val cardio = _uiState.value.cardioCarregado ?: return
        viewModelScope.launch {
            cardioRepository.deletar(cardio)
            _uiState.update { it.copy(salvo = true) }
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

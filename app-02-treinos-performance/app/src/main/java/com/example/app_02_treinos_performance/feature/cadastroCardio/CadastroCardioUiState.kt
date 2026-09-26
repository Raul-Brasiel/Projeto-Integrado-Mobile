package com.example.app_02_treinos_performance.feature.cadastroCardio

import com.example.app_02_treinos_performance.data.model.Cardio

data class CadastroCardioUiState(
    val cardioId: Long? = null,
    val cardioCarregado: Cardio? = null,
    val nome: String = "",
    val distanciaKm: String = "",
    val tempoMinutos: String = "",
    val tempoSegundos: String = "",
    val dataEpochMillis: Long = System.currentTimeMillis(),
    val carregando: Boolean = false,
    val salvando: Boolean = false,
    val salvo: Boolean = false,
    val erroNome: String? = null,
    val erroDistancia: String? = null,
    val erroTempo: String? = null
) {
    val emEdicao: Boolean
        get() = cardioId != null
}

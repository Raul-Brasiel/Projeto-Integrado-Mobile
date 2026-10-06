package com.paulo.obrigacoes.ui.notificacao

import androidx.lifecycle.ViewModel
import com.paulo.obrigacoes.data.repository.ObligationRepository

class NotificacaoViewModel : ViewModel() {

    private var obligationId: Long = -1L

    fun carregar(id: Long) {
        obligationId = id
    }

    fun ativar(label: String, diasAntes: Int) {
        if (obligationId != -1L) {
            ObligationRepository.ativarNotificacao(obligationId, label, diasAntes)
        }
    }

    fun getObligationId(): Long = obligationId

    fun getObligation() = ObligationRepository.getById(obligationId)
}

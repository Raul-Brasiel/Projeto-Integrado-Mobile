package com.paulo.obrigacoes.ui.detalhes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.paulo.obrigacoes.data.model.Obligation
import com.paulo.obrigacoes.data.repository.ObligationRepository

import androidx.lifecycle.switchMap

class DetalhesViewModel : ViewModel() {

    private val id = MutableLiveData<Long>()

    val obrigacao: LiveData<Obligation?> =
        id.switchMap { obligationId ->
            ObligationRepository.observeById(obligationId)
        }

    fun carregar(obligationId: Long) {
        id.value = obligationId
    }

    fun marcarComoPaga() {
        id.value?.let {
            ObligationRepository.marcarComoPaga(it)
        }
    }
}

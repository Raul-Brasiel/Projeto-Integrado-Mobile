package com.paulo.obrigacoes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.paulo.obrigacoes.data.model.Obligation
import com.paulo.obrigacoes.data.model.Status
import com.paulo.obrigacoes.data.model.Tipo

/**
 * Camada de dados.
 *
 * Neste projeto os dados ainda ficam em memória. Se no futuro for usado Room,
 * esta é a principal camada que precisará ser substituída.
 */
object ObligationRepository {

    private val lista = mutableListOf<Obligation>()
    private var proximoId = 1L

    private val _obrigacoes = MutableLiveData<List<Obligation>>()
    val obrigacoes: LiveData<List<Obligation>> = _obrigacoes

    init {
        val now = System.currentTimeMillis()

        lista.add(
            Obligation(
                proximoId++, "Fatura energia", 210.0, "Companhia elétrica",
                Tipo.PAGAR, now - 2 * 24 * 60 * 60 * 1000, Status.ATRASADO
            )
        )
        lista.add(
            Obligation(
                proximoId++, "Cliente Raul", 850.0, "Raul Castro",
                Tipo.RECEBER, now + 5 * 24 * 60 * 60 * 1000, Status.PENDENTE
            )
        )
        lista.add(
            Obligation(
                proximoId++, "Aluguel sala", 1200.0, "Imobiliária Central",
                Tipo.PAGAR, now - 10 * 24 * 60 * 60 * 1000, Status.PAGA
            )
        )

        publicar()
    }

    fun getAll(): List<Obligation> =
        lista.sortedBy { it.vencimentoMillis }

    fun getById(id: Long): Obligation? =
        lista.find { it.id == id }

    fun observeById(id: Long): LiveData<Obligation?> =
        obrigacoes.map { items -> items.find { it.id == id } }

    fun add(
        descricao: String,
        valor: Double,
        favorecido: String,
        tipo: Tipo,
        vencimentoMillis: Long
    ) {
        val status =
            if (vencimentoMillis < System.currentTimeMillis()) Status.ATRASADO
            else Status.PENDENTE

        lista.add(
            Obligation(
                proximoId++,
                descricao,
                valor,
                favorecido,
                tipo,
                vencimentoMillis,
                status
            )
        )

        publicar()
    }

    fun marcarComoPaga(id: Long) {
        getById(id)?.status = Status.PAGA
        publicar()
    }

    fun ativarNotificacao(id: Long, label: String, diasAntes: Int = 1) {
        getById(id)?.let {
            it.notificacaoAtiva = true
            it.notificacaoLabel = label
            it.notificacaoDiasAntes = diasAntes
        }
        publicar()
    }

    fun cancelarNotificacao(id: Long) {
        getById(id)?.let {
            it.notificacaoAtiva = false
        }
        publicar()
    }

    fun getComNotificacaoAtiva(): List<Obligation> =
        lista.filter { it.notificacaoAtiva }

    fun observeNotificacoesAtivas(): LiveData<List<Obligation>> =
        obrigacoes.map { items -> items.filter { it.notificacaoAtiva } }

    private fun publicar() {
        _obrigacoes.value = getAll().toList()
    }
}

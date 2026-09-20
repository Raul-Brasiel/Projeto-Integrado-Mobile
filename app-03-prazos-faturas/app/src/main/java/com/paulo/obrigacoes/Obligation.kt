package com.paulo.obrigacoes

import java.io.Serializable

enum class Status { PENDENTE, ATRASADO, PAGA }
enum class Tipo { PAGAR, RECEBER }

data class Obligation(
    val id: Long,
    var descricao: String,
    var valor: Double,
    var favorecido: String,
    var tipo: Tipo,
    var vencimentoMillis: Long,
    var status: Status,
    var notificacaoAtiva: Boolean = false,
    var notificacaoLabel: String = ""
) : Serializable

object ObligationRepository {
    private val lista = mutableListOf<Obligation>()
    private var proximoId = 1L

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
    }

    fun getAll(): List<Obligation> = lista.sortedBy { it.vencimentoMillis }

    fun getById(id: Long): Obligation? = lista.find { it.id == id }

    fun add(descricao: String, valor: Double, favorecido: String, tipo: Tipo, vencimentoMillis: Long) {
        val status = if (vencimentoMillis < System.currentTimeMillis()) Status.ATRASADO else Status.PENDENTE
        lista.add(Obligation(proximoId++, descricao, valor, favorecido, tipo, vencimentoMillis, status))
    }

    fun marcarComoPaga(id: Long) {
        getById(id)?.status = Status.PAGA
    }

    fun ativarNotificacao(id: Long, label: String) {
        getById(id)?.let {
            it.notificacaoAtiva = true
            it.notificacaoLabel = label
        }
    }

    fun cancelarNotificacao(id: Long) {
        getById(id)?.let {
            it.notificacaoAtiva = false
        }
    }

    fun getComNotificacaoAtiva(): List<Obligation> = lista.filter { it.notificacaoAtiva }
}

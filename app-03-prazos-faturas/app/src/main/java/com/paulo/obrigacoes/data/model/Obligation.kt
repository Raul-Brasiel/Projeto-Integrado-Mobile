package com.paulo.obrigacoes.data.model

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
    var notificacaoLabel: String = "",
    var notificacaoDiasAntes: Int = 1
) : Serializable

package com.paulo.obrigacoes.ui.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulo.obrigacoes.data.model.Tipo
import com.paulo.obrigacoes.data.repository.ObligationRepository

class CadastroViewModel : ViewModel() {

    private val _tipoSelecionado = MutableLiveData(Tipo.PAGAR)
    val tipoSelecionado: LiveData<Tipo> = _tipoSelecionado

    private val _vencimentoSelecionado = MutableLiveData<Long?>()
    val vencimentoSelecionado: LiveData<Long?> = _vencimentoSelecionado

    fun selecionarTipo(tipo: Tipo) {
        _tipoSelecionado.value = tipo
    }

    fun selecionarVencimento(millis: Long) {
        _vencimentoSelecionado.value = millis
    }

    fun salvar(
        descricao: String,
        valor: Double?,
        favorecido: String
    ): Boolean {
        val vencimento = _vencimentoSelecionado.value ?: return false
        if (descricao.isBlank() || favorecido.isBlank() || valor == null) {
            return false
        }

        ObligationRepository.add(
            descricao = descricao,
            valor = valor,
            favorecido = favorecido,
            tipo = _tipoSelecionado.value ?: Tipo.PAGAR,
            vencimentoMillis = vencimento
        )

        return true
    }
}

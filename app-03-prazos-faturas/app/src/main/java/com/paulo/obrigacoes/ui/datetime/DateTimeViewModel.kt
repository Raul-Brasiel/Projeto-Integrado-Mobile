package com.paulo.obrigacoes.ui.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class DateTimeViewModel : ViewModel() {

    private val _dataSelecionada = MutableLiveData<Long>()
    val dataSelecionada: LiveData<Long> = _dataSelecionada

    fun confirmar(dataMillis: Long, hora: Int, minuto: Int) {
        val cal = Calendar.getInstance().apply {
            timeInMillis = dataMillis
            set(Calendar.HOUR_OF_DAY, hora)
            set(Calendar.MINUTE, minuto)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        _dataSelecionada.value = cal.timeInMillis
    }
}

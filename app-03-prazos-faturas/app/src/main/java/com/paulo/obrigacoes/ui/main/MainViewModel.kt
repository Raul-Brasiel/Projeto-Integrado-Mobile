package com.paulo.obrigacoes.ui.main

import androidx.lifecycle.ViewModel
import com.paulo.obrigacoes.data.repository.ObligationRepository

class MainViewModel : ViewModel() {

    val obrigacoes = ObligationRepository.obrigacoes
}

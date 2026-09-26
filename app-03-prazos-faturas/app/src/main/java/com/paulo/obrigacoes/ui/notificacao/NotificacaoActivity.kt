package com.paulo.obrigacoes.ui.notificacao

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.paulo.obrigacoes.R
import com.paulo.obrigacoes.databinding.ActivityNotificacaoBinding
import com.paulo.obrigacoes.notification.NotificationScheduler
import com.paulo.obrigacoes.ui.main.MainActivity

class NotificacaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacaoBinding
    private lateinit var viewModel: NotificacaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NotificacaoViewModel::class.java]

        val id = intent.getLongExtra(MainActivity.EXTRA_ID, -1L)
        viewModel.carregar(id)
        
        viewModel.getObligation()?.let { obligation ->
            if (obligation.notificacaoAtiva) {
                when (obligation.notificacaoLabel) {
                    getString(R.string.opcao_no_dia) -> binding.radioGroupOpcoes.check(R.id.opcaoNoDia)
                    getString(R.string.opcao_3_dias_antes) -> binding.radioGroupOpcoes.check(R.id.opcao3Dias)
                    getString(R.string.opcao_personalizado) -> binding.radioGroupOpcoes.check(R.id.opcaoPersonalizado)
                    else -> binding.radioGroupOpcoes.check(R.id.opcao1Dia)
                }
            }
        }

        configurarEventos()
    }

    private fun configurarEventos() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnAtivar.setOnClickListener {
            val label = when (binding.radioGroupOpcoes.checkedRadioButtonId) {
                R.id.opcaoNoDia -> getString(R.string.opcao_no_dia)
                R.id.opcao3Dias -> getString(R.string.opcao_3_dias_antes)
                R.id.opcaoPersonalizado -> getString(R.string.opcao_personalizado)
                else -> getString(R.string.opcao_1_dia_antes)
            }

            viewModel.ativar(label)

            NotificationScheduler.agendar(
                this,
                viewModel.getObligationId(),
                label
            )

            Toast.makeText(
                this,
                "Notificação ativada",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}

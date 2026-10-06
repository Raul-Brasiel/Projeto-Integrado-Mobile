package com.paulo.obrigacoes.ui.notificacao

import android.os.Bundle
import android.view.View
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
                    getString(R.string.opcao_personalizado) -> {
                        binding.radioGroupOpcoes.check(R.id.opcaoPersonalizado)
                        binding.inputDiasAntes.setText(obligation.notificacaoDiasAntes.toString())
                    }
                    else -> binding.radioGroupOpcoes.check(R.id.opcao1Dia)
                }
            }
        }

        atualizarCampoPersonalizado()
        configurarEventos()
    }

    private fun atualizarCampoPersonalizado() {
        val personalizado =
            binding.radioGroupOpcoes.checkedRadioButtonId == R.id.opcaoPersonalizado
        binding.inputDiasAntes.visibility = if (personalizado) View.VISIBLE else View.GONE
    }

    private fun configurarEventos() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.radioGroupOpcoes.setOnCheckedChangeListener { _, _ ->
            atualizarCampoPersonalizado()
        }

        binding.btnAtivar.setOnClickListener {
            val (label, diasAntes) = when (binding.radioGroupOpcoes.checkedRadioButtonId) {
                R.id.opcaoNoDia -> getString(R.string.opcao_no_dia) to 0
                R.id.opcao3Dias -> getString(R.string.opcao_3_dias_antes) to 3
                R.id.opcaoPersonalizado -> {
                    val dias = binding.inputDiasAntes.text.toString().trim().toIntOrNull()
                    if (dias == null || dias !in 1..365) {
                        binding.inputDiasAntes.error = getString(R.string.erro_dias_antes)
                        return@setOnClickListener
                    }
                    getString(R.string.opcao_personalizado) to dias
                }
                else -> getString(R.string.opcao_1_dia_antes) to 1
            }

            viewModel.ativar(label, diasAntes)

            NotificationScheduler.agendar(this, viewModel.getObligationId())

            Toast.makeText(
                this,
                "Notificação ativada",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}

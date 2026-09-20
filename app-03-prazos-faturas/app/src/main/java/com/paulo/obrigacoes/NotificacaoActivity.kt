package com.paulo.obrigacoes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paulo.obrigacoes.databinding.ActivityNotificacaoBinding

class NotificacaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacaoBinding
    private var obligationId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        obligationId = intent.getLongExtra(MainActivity.EXTRA_ID, -1)

        binding.btnAtivar.setOnClickListener {
            val label = when (binding.radioGroupOpcoes.checkedRadioButtonId) {
                R.id.opcaoNoDia -> getString(R.string.opcao_no_dia)
                R.id.opcao3Dias -> getString(R.string.opcao_3_dias_antes)
                R.id.opcaoPersonalizado -> getString(R.string.opcao_personalizado)
                else -> getString(R.string.opcao_1_dia_antes)
            }

            ObligationRepository.ativarNotificacao(obligationId, label)
            NotificationScheduler.agendar(this, obligationId, label)
            Toast.makeText(this, "Notificação ativada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

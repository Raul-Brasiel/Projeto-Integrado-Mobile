package com.paulo.obrigacoes.ui.detalhes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.paulo.obrigacoes.R
import com.paulo.obrigacoes.data.model.Status
import com.paulo.obrigacoes.data.model.Tipo
import com.paulo.obrigacoes.databinding.ActivityDetalhesBinding
import com.paulo.obrigacoes.ui.main.MainActivity
import com.paulo.obrigacoes.ui.notificacao.NotificacaoActivity
import java.text.SimpleDateFormat
import java.util.Locale

class DetalhesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesBinding
    private lateinit var viewModel: DetalhesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetalhesViewModel::class.java]

        val id = intent.getLongExtra(MainActivity.EXTRA_ID, -1L)
        viewModel.carregar(id)

        configurarEventos()
        observarDados()
    }

    private fun configurarEventos() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnMarcarPaga.setOnClickListener {
            viewModel.marcarComoPaga()
        }

        binding.btnConfigurarNotificacao.setOnClickListener {
            val id = intent.getLongExtra(MainActivity.EXTRA_ID, -1L)

            startActivity(
                Intent(this, NotificacaoActivity::class.java)
                    .putExtra(MainActivity.EXTRA_ID, id)
            )
        }
    }

    private fun observarDados() {
        viewModel.obrigacao.observe(this) { item ->
            item ?: return@observe

            val sdf = SimpleDateFormat(
                "dd/MM/yyyy · HH:mm",
                Locale("pt", "BR")
            )

            binding.textDescricao.text = item.descricao
            binding.textValor.text = "R$ ${"%.2f".format(item.valor)}"
            binding.textFavorecido.text = item.favorecido
            binding.textVencimento.text = sdf.format(item.vencimentoMillis)
            binding.textTipo.text =
                if (item.tipo == Tipo.PAGAR)
                    getString(R.string.tipo_pagar)
                else
                    getString(R.string.tipo_receber)

            when (item.status) {
                Status.ATRASADO -> {
                    binding.textStatus.text =
                        getString(R.string.status_atrasado)
                    binding.textStatus.setBackgroundResource(
                        R.drawable.bg_badge_late
                    )
                    binding.textStatus.setTextColor(
                        getColor(R.color.late_text)
                    )
                }

                Status.PENDENTE -> {
                    binding.textStatus.text =
                        getString(R.string.status_pendente)
                    binding.textStatus.setBackgroundResource(
                        R.drawable.bg_badge_pending
                    )
                    binding.textStatus.setTextColor(
                        getColor(R.color.pending_text)
                    )
                }

                Status.PAGA -> {
                    binding.textStatus.text =
                        getString(R.string.status_pago)
                    binding.textStatus.setBackgroundResource(
                        R.drawable.bg_badge_paid
                    )
                    binding.textStatus.setTextColor(
                        getColor(R.color.paid_text)
                    )
                }
            }

            binding.btnMarcarPaga.isEnabled = item.status != Status.PAGA
            binding.btnMarcarPaga.alpha =
                if (item.status != Status.PAGA) 1f else 0.5f
        }
    }
}

package com.paulo.obrigacoes.ui.cadastro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.paulo.obrigacoes.R
import com.paulo.obrigacoes.data.model.Tipo
import com.paulo.obrigacoes.databinding.ActivityCadastroBinding
import com.paulo.obrigacoes.ui.datetime.DateTimeActivity
import java.text.SimpleDateFormat
import java.util.Locale

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var viewModel: CadastroViewModel

    private val dateTimeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val millis =
                    result.data?.getLongExtra(DateTimeActivity.EXTRA_MILLIS, -1L) ?: -1L

                if (millis > 0) {
                    viewModel.selecionarVencimento(millis)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CadastroViewModel::class.java]

        configurarEventos()
        observarEstado()
    }

    private fun configurarEventos() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnTipoPagar.setOnClickListener {
            viewModel.selecionarTipo(Tipo.PAGAR)
        }

        binding.btnTipoReceber.setOnClickListener {
            viewModel.selecionarTipo(Tipo.RECEBER)
        }

        binding.campoData.setOnClickListener {
            dateTimeLauncher.launch(
                Intent(this, DateTimeActivity::class.java)
            )
        }

        binding.btnSalvar.setOnClickListener {
            salvar()
        }
    }

    private fun observarEstado() {
        viewModel.tipoSelecionado.observe(this) { tipo ->
            atualizarTipoSelecionado(tipo)
        }

        viewModel.vencimentoSelecionado.observe(this) { millis ->
            millis ?: return@observe

            val sdf = SimpleDateFormat(
                "dd/MM/yyyy · HH:mm",
                Locale("pt", "BR")
            )

            binding.textDataSelecionada.text = sdf.format(millis)
            binding.textDataSelecionada.setTextColor(
                getColor(R.color.text_primary)
            )
        }
    }

    private fun atualizarTipoSelecionado(tipo: Tipo) {
        val pagarSelecionado = tipo == Tipo.PAGAR

        binding.btnTipoPagar.setBackgroundResource(
            if (pagarSelecionado)
                R.drawable.bg_toggle_selected
            else
                R.drawable.bg_toggle_unselected
        )
        binding.btnTipoPagar.setTextColor(
            getColor(
                if (pagarSelecionado)
                    R.color.primary_dark
                else
                    R.color.text_secondary
            )
        )

        binding.btnTipoReceber.setBackgroundResource(
            if (!pagarSelecionado)
                R.drawable.bg_toggle_selected
            else
                R.drawable.bg_toggle_unselected
        )
        binding.btnTipoReceber.setTextColor(
            getColor(
                if (!pagarSelecionado)
                    R.color.primary_dark
                else
                    R.color.text_secondary
            )
        )
    }

    private fun salvar() {
        val descricao = binding.inputDescricao.text.toString().trim()
        val valorTexto = binding.inputValor.text.toString()
            .trim()
            .replace(",", ".")
        val favorecido = binding.inputFavorecido.text.toString().trim()
        val valor = valorTexto.toDoubleOrNull()

        val salvo = viewModel.salvar(
            descricao = descricao,
            valor = valor,
            favorecido = favorecido
        )

        if (!salvo) {
            Toast.makeText(
                this,
                "Preencha todos os campos e selecione a data",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Toast.makeText(
            this,
            "Obrigação salva",
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }
}

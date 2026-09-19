package com.paulo.obrigacoes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.paulo.obrigacoes.databinding.ActivityCadastroBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private var tipoSelecionado = Tipo.PAGAR
    private var vencimentoSelecionado: Long? = null

    private val dateTimeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val millis = result.data?.getLongExtra(DateTimeActivity.EXTRA_MILLIS, -1L) ?: -1L
            if (millis > 0) {
                vencimentoSelecionado = millis
                val sdf = SimpleDateFormat("dd/MM/yyyy · HH:mm", Locale("pt", "BR"))
                binding.textDataSelecionada.text = sdf.format(millis)
                binding.textDataSelecionada.setTextColor(getColor(R.color.text_primary))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        selecionarTipo(Tipo.PAGAR)
        binding.btnTipoPagar.setOnClickListener { selecionarTipo(Tipo.PAGAR) }
        binding.btnTipoReceber.setOnClickListener { selecionarTipo(Tipo.RECEBER) }

        binding.campoData.setOnClickListener {
            dateTimeLauncher.launch(Intent(this, DateTimeActivity::class.java))
        }

        binding.btnSalvar.setOnClickListener { salvar() }
    }

    private fun selecionarTipo(tipo: Tipo) {
        tipoSelecionado = tipo
        if (tipo == Tipo.PAGAR) {
            binding.btnTipoPagar.setBackgroundResource(R.drawable.bg_toggle_selected)
            binding.btnTipoPagar.setTextColor(getColor(R.color.primary_dark))
            binding.btnTipoReceber.setBackgroundResource(R.drawable.bg_toggle_unselected)
            binding.btnTipoReceber.setTextColor(getColor(R.color.text_secondary))
        } else {
            binding.btnTipoReceber.setBackgroundResource(R.drawable.bg_toggle_selected)
            binding.btnTipoReceber.setTextColor(getColor(R.color.primary_dark))
            binding.btnTipoPagar.setBackgroundResource(R.drawable.bg_toggle_unselected)
            binding.btnTipoPagar.setTextColor(getColor(R.color.text_secondary))
        }
    }

    private fun salvar() {
        val descricao = binding.inputDescricao.text.toString().trim()
        val valorTexto = binding.inputValor.text.toString().trim().replace(",", ".")
        val favorecido = binding.inputFavorecido.text.toString().trim()
        val valor = valorTexto.toDoubleOrNull()

        if (descricao.isEmpty() || favorecido.isEmpty() || valor == null || vencimentoSelecionado == null) {
            Toast.makeText(this, "Preencha todos os campos e selecione a data", Toast.LENGTH_SHORT).show()
            return
        }

        ObligationRepository.add(descricao, valor, favorecido, tipoSelecionado, vencimentoSelecionado!!)
        Toast.makeText(this, "Obrigação salva", Toast.LENGTH_SHORT).show()
        finish()
    }
}

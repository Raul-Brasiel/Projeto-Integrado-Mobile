package com.paulo.obrigacoes.ui.main
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.paulo.obrigacoes.ListaNotificacoesActivity
import com.paulo.obrigacoes.R
import com.paulo.obrigacoes.data.model.Obligation
import com.paulo.obrigacoes.data.model.Status
import com.paulo.obrigacoes.databinding.ActivityMainBinding
import com.paulo.obrigacoes.ui.adapter.ObligationAdapter
import com.paulo.obrigacoes.ui.cadastro.CadastroActivity
import com.paulo.obrigacoes.ui.detalhes.DetalhesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ObligationAdapter
    private val viewModel = MainViewModel()


    private var listaCompleta: List<Obligation> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarLista()
        configurarEventos()
        configurarFiltros()
        configurarCoresDosChips()
        observarDados()
    }

    private fun configurarLista() {
        adapter = ObligationAdapter { obligation ->
            startActivity(
                Intent(this, DetalhesActivity::class.java)
                    .putExtra(EXTRA_ID, obligation.id)
            )
        }

        binding.recyclerObrigacoes.layoutManager = LinearLayoutManager(this)
        binding.recyclerObrigacoes.adapter = adapter
    }

    private fun configurarEventos() {
        binding.fabAdicionar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        binding.btnNotificacoes.setOnClickListener {
            startActivity(Intent(this, ListaNotificacoesActivity::class.java))
        }
    }


    private fun configurarCoresDosChips() {
        fun aplicarEstilo(chip: Chip, corForte: Int, corSuave: Int, corTextoChecked: Int = Color.WHITE) {
            val bgStates = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_checked)
                ),
                intArrayOf(corForte, corSuave)
            )

            val textStates = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_checked)
                ),
                intArrayOf(corTextoChecked, corForte)
            )

            chip.chipBackgroundColor = bgStates
            chip.chipStrokeColor = ColorStateList.valueOf(corForte)
            chip.chipStrokeWidth = resources.displayMetrics.density * 1f
            chip.setTextColor(textStates)
            chip.checkedIconTint = textStates
        }


        aplicarEstilo(
            binding.chipTodas,
            corForte = Color.parseColor("#7C4DFF"),
            corSuave = Color.parseColor("#EDE7F6")
        )

        aplicarEstilo(
            binding.chipPago,
            corForte = Color.parseColor("#2E7D32"),
            corSuave = Color.parseColor("#E8F5E9")
        )


        aplicarEstilo(
            binding.chipPendente,
            corForte = Color.parseColor("#F57F17"),
            corSuave = Color.parseColor("#FFF8E1"),
            corTextoChecked = Color.parseColor("#3E2723")
        )

        // Atrasado -> Vermelho
        aplicarEstilo(
            binding.chipAtrasado,
            corForte = Color.parseColor("#D32F2F"),
            corSuave = Color.parseColor("#FFEBEE")
        )
    }

    private fun configurarFiltros() {
        binding.chipGroupFiltros.setOnCheckedStateChangeListener { _, checkedIds ->
            val chipSelecionadoId = checkedIds.firstOrNull() ?: R.id.chipTodas
            aplicarFiltro(chipSelecionadoId)
        }
    }

    private fun observarDados() {
        viewModel.obrigacoes.observe(this) { items ->
            listaCompleta = items ?: emptyList()


            val chipAtual = binding.chipGroupFiltros.checkedChipId
            aplicarFiltro(if (chipAtual != View.NO_ID) chipAtual else R.id.chipTodas)
        }
    }

    private fun aplicarFiltro(chipId: Int) {
        val listaFiltrada = when (chipId) {
            R.id.chipPago -> {
                listaCompleta.filter { it.status == Status.PAGA }
            }
            R.id.chipPendente -> {
                listaCompleta.filter { it.status == Status.PENDENTE }
            }
            R.id.chipAtrasado -> {
                listaCompleta.filter { it.status == Status.ATRASADO }
            }
            else -> { // R.id.chipTodas
                listaCompleta
            }
        }


        adapter.updateData(listaFiltrada)
        binding.textVazio.visibility =
            if (listaFiltrada.isEmpty()) View.VISIBLE
            else View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
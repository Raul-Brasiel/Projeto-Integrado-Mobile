package com.paulo.obrigacoes.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulo.obrigacoes.R
import com.paulo.obrigacoes.databinding.ActivityMainBinding
import com.paulo.obrigacoes.ui.adapter.ObligationAdapter
import com.paulo.obrigacoes.ui.cadastro.CadastroActivity
import com.paulo.obrigacoes.ui.detalhes.DetalhesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ObligationAdapter
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarLista()
        configurarEventos()
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
    }

    private fun observarDados() {
        viewModel.obrigacoes.observe(this) { items ->
            adapter.updateData(items)
            binding.textVazio.visibility =
                if (items.isEmpty()) android.view.View.VISIBLE
                else android.view.View.GONE
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}

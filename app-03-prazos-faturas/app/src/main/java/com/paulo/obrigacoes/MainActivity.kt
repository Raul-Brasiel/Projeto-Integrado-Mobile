package com.paulo.obrigacoes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulo.obrigacoes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ObligationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ObligationAdapter(ObligationRepository.getAll()) { obligation ->
            val intent = Intent(this, DetalhesActivity::class.java)
            intent.putExtra(EXTRA_ID, obligation.id)
            startActivity(intent)
        }

        binding.recyclerObrigacoes.layoutManager = LinearLayoutManager(this)
        binding.recyclerObrigacoes.adapter = adapter

        binding.fabAdicionar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        binding.btnNotificacoes.setOnClickListener {
            startActivity(Intent(this, ListaNotificacoesActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val items = ObligationRepository.getAll()
        adapter.updateData(items)
        binding.textVazio.visibility = if (items.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}

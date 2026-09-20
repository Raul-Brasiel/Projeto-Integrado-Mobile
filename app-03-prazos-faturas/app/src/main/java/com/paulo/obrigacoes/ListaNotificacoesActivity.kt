package com.paulo.obrigacoes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulo.obrigacoes.databinding.ActivityListaNotificacoesBinding

class ListaNotificacoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaNotificacoesBinding
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaNotificacoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        adapter = NotificationAdapter(ObligationRepository.getComNotificacaoAtiva()) { item ->
            ObligationRepository.cancelarNotificacao(item.id)
            NotificationScheduler.cancelar(this, item.id)
            atualizarLista()
        }
        binding.recyclerNotificacoes.layoutManager = LinearLayoutManager(this)
        binding.recyclerNotificacoes.adapter = adapter
        atualizarLista()
    }

    private fun atualizarLista() {
        val items = ObligationRepository.getComNotificacaoAtiva()
        adapter.updateData(items)
        binding.textVazio.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }
}

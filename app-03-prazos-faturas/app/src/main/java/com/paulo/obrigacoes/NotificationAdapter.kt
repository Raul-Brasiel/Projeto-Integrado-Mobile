package com.paulo.obrigacoes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale
import com.paulo.obrigacoes.data.model.Obligation

class NotificationAdapter(
    private var items: List<Obligation>,
    private val onRemove: (Obligation) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val sdf = SimpleDateFormat("dd/MM · HH:mm", Locale("pt", "BR"))

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.textNomeObrigacao)
        val dataHora: TextView = view.findViewById(R.id.textDataHora)
        val remover: ImageButton = view.findViewById(R.id.btnRemover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notificacao, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nome.text = item.descricao
        
        val disparoMillis = item.vencimentoMillis - item.notificacaoDiasAntes * 24L * 60 * 60 * 1000
        
        holder.dataHora.text = sdf.format(disparoMillis)
        holder.remover.setOnClickListener { onRemove(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Obligation>) {
        items = newItems
        notifyDataSetChanged()
    }
}

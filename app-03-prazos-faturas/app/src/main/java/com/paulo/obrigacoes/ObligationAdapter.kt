package com.paulo.obrigacoes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class ObligationAdapter(
    private var items: List<Obligation>,
    private val onClick: (Obligation) -> Unit
) : RecyclerView.Adapter<ObligationAdapter.ViewHolder>() {

    private val sdf = SimpleDateFormat("dd/MM", Locale("pt", "BR"))

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: View = view.findViewById(R.id.rootItem)
        val descricao: TextView = view.findViewById(R.id.textDescricao)
        val info: TextView = view.findViewById(R.id.textInfo)
        val status: TextView = view.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_obligation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.descricao.text = item.descricao
        val prefixo = if (item.status == Status.PAGA) "Pago" else "Vence"
        holder.info.text = "$prefixo ${sdf.format(item.vencimentoMillis)} · R$ ${"%.2f".format(item.valor)}"

        when (item.status) {
            Status.ATRASADO -> {
                holder.root.setBackgroundResource(R.drawable.bg_item_late)
                holder.status.setBackgroundResource(R.drawable.bg_badge_late)
                holder.status.setTextColor(holder.itemView.context.getColor(R.color.late_text))
                holder.status.text = holder.itemView.context.getString(R.string.status_atrasado)
            }
            Status.PENDENTE -> {
                holder.root.setBackgroundResource(R.drawable.bg_item_pending)
                holder.status.setBackgroundResource(R.drawable.bg_badge_pending)
                holder.status.setTextColor(holder.itemView.context.getColor(R.color.pending_text))
                holder.status.text = holder.itemView.context.getString(R.string.status_pendente)
            }
            Status.PAGA -> {
                holder.root.setBackgroundResource(R.drawable.bg_item_paid)
                holder.status.setBackgroundResource(R.drawable.bg_badge_paid)
                holder.status.setTextColor(holder.itemView.context.getColor(R.color.paid_text))
                holder.status.text = holder.itemView.context.getString(R.string.status_pago)
            }
        }

        holder.root.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Obligation>) {
        items = newItems
        notifyDataSetChanged()
    }
}

package com.example.eznstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriaAdapter(
    private val categorias: List<String>,
    private val onCategoriaClick: (String) -> Unit // Listener para clics
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(categorias[position])
    }

    override fun getItemCount(): Int = categorias.size

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreCategoria: TextView = itemView.findViewById(R.id.tvNombreCategoria)

        fun bind(nombreCategoria: String) {
            tvNombreCategoria.text = nombreCategoria
            itemView.setOnClickListener {
                onCategoriaClick(nombreCategoria) // Llamar al listener con la categoría seleccionada
            }
        }
    }
}

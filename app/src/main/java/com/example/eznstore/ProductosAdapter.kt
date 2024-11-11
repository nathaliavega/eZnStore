package com.example.eznstore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductosAdapter(
    private val productos: List<Producto>,
    private val context: Context // Agrega el contexto
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvPrecioProducto: TextView = itemView.findViewById(R.id.tvPrecioProducto)
        val ivImagenProducto: ImageView = itemView.findViewById(R.id.ivImagenProducto)

        fun bind(producto: Producto) {
            tvNombreProducto.text = producto.title
            tvPrecioProducto.text = "$${producto.price}"

            Glide.with(itemView.context)
                .load(producto.image)
                .into(ivImagenProducto)

            itemView.setOnClickListener {
                val intent = Intent(context, DetallesActivity::class.java)
                intent.putExtra("PRODUCT_ID", producto.id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}

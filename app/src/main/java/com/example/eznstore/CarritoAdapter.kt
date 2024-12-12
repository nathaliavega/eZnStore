package com.example.eznstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.BaseAdapter
import com.bumptech.glide.Glide

class CarritoAdapter(
    private val context: Context,
    private val productos: List<Producto>,
    private val eliminarProducto: (Producto) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = productos.size

    override fun getItem(position: Int): Any = productos[position]

    override fun getItemId(position: Int): Long = productos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Usar una vista reciclada si existe, si no, inflar una nueva vista
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_carrito, parent, false)
        val producto = productos[position]

        // Referencias a los elementos de la vista
        val ivProducto = view.findViewById<ImageView>(R.id.ivProducto)
        val tvNombreProducto = view.findViewById<TextView>(R.id.tvNombreProducto)
        val tvPrecioProducto = view.findViewById<TextView>(R.id.tvPrecioProducto)
        val btnEliminar = view.findViewById<ImageButton>(R.id.btnEliminar)

        // Configurar los datos del producto
        tvNombreProducto.text = producto.title
        tvPrecioProducto.text = "$${producto.price}"

        // Usar Glide para cargar la imagen o colocar un color de fondo si no hay imagen
        Glide.with(context)
            .load(producto.image)
            .placeholder(android.R.color.darker_gray) // Usar un color de fondo si no se encuentra la imagen
            .error(android.R.color.darker_gray) // Usar un color de fondo si hay un error
            .into(ivProducto)

        // Configurar la acción del botón eliminar
        btnEliminar.setOnClickListener {
            eliminarProducto(producto)
        }

        return view
    }
}

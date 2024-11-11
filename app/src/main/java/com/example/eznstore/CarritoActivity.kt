package com.example.eznstore

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CarritoActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var finalizarCompraButton: Button
    private lateinit var goToProductsButton: ImageButton
    private var productosCarrito = Carrito.obtenerProductos().toMutableList() // Usar lista mutable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        listView = findViewById(R.id.listViewCarrito)
        finalizarCompraButton = findViewById(R.id.finalizar_compra_button)
        goToProductsButton = findViewById(R.id.go_to_products_button)

        // Mapea productos a una lista de strings
        val productosString = productosCarrito.map { producto ->
            "${producto.title} - \$${producto.price}"
        }

        // Inicializa el adaptador y establece la lista
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productosString)
        listView.adapter = adapter

        // Listener para finalizar compra
        finalizarCompraButton.setOnClickListener {
            if (productosCarrito.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            } else {
                // Simulación de compra
                val totalCompra = productosCarrito.sumOf { it.price }
                Toast.makeText(this, "Compra finalizada. Total: \$${totalCompra}", Toast.LENGTH_SHORT).show()

                // Limpiar el carrito y actualizar la lista
                productosCarrito.clear()
                adapter.clear() // Limpia los datos del adaptador
                adapter.notifyDataSetChanged() // Actualiza la lista visualmente
            }
        }

        // Listener para regresar a la actividad de productos
        goToProductsButton.setOnClickListener {
            finish() // Cierra CarritoActivity y regresa a la anterior
        }
    }
}

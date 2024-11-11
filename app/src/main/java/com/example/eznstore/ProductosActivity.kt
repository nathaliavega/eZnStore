package com.example.eznstore

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eznstore.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        recyclerView = findViewById(R.id.recyclerViewProductos)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Configura el botón de categorías
        val btnCategorias = findViewById<Button>(R.id.btnCategorias)
        btnCategorias.setOnClickListener {
            val intent = Intent(this, CategoriaActivity::class.java)
            startActivity(intent)
        }

        // Configura el botón para ir al carrito
        val goToCartButton = findViewById<ImageButton>(R.id.go_to_cart_button)
        goToCartButton.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }

        obtenerProductos()
    }

    private fun obtenerProductos() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    response.body()?.let { productos ->
                        adapter = ProductosAdapter(productos, this@ProductosActivity)
                        recyclerView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@ProductosActivity, "Error al obtener productos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(this@ProductosActivity, "Fallo en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

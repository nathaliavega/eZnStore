package com.example.eznstore

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eznstore.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductosCategoriaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductosAdapter
    private lateinit var tvTituloCategoria: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_categoria)

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewProductosCategoria)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el título dinámico
        tvTituloCategoria = findViewById(R.id.tvTituloCategoria)
        val categoria = intent.getStringExtra("CATEGORIA") ?: "Categoría"
        tvTituloCategoria.text = categoria

        // Configurar el botón de regresar
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish() // Finaliza esta actividad y regresa a la anterior
        }

        // Cargar productos por categoría
        obtenerProductosPorCategoria(categoria)
    }

    private fun obtenerProductosPorCategoria(categoria: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.obtenerProductosPorCategoria(categoria).enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    response.body()?.let { productos ->
                        adapter = ProductosAdapter(productos, this@ProductosCategoriaActivity)
                        recyclerView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@ProductosCategoriaActivity, "Error al cargar productos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(this@ProductosCategoriaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

package com.example.eznstore

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
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

class CategoriaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCategorias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el botón de regresar
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            // Iniciar ProductosActivity y finalizar CategoriaActivity
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
            finish() // Finalizar esta actividad para no acumularla en la pila
        }

        // Cargar categorías de la API
        obtenerCategorias()
    }

    private fun obtenerCategorias() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.obtenerCategorias().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    response.body()?.let { categorias ->
                        // Configurar el adaptador con un listener para los clics
                        adapter = CategoriaAdapter(categorias) { categoria ->
                            // Navegar a ProductosCategoriaActivity al hacer clic en una categoría
                            val intent = Intent(this@CategoriaActivity, ProductosCategoriaActivity::class.java)
                            intent.putExtra("CATEGORIA", categoria)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@CategoriaActivity, "Error al cargar categorías", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(this@CategoriaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

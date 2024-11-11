package com.example.eznstore

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.eznstore.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.ImageButton

class DetallesActivity : AppCompatActivity() {

    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productDescription: TextView
    private lateinit var productPrice: TextView
    private lateinit var backButton: ImageButton
    private lateinit var addToCartButton: ImageButton
    private lateinit var goToCartButton: ImageButton // Nueva variable para el botón de ir al carrito
    private lateinit var currentProducto: Producto // Variable para almacenar el producto actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        // Inicialización de vistas
        productImage = findViewById(R.id.product_image)
        productName = findViewById(R.id.product_name)
        productDescription = findViewById(R.id.product_description)
        productPrice = findViewById(R.id.product_price)
        backButton = findViewById(R.id.back_button)
        addToCartButton = findViewById(R.id.add_to_cart_button)
        goToCartButton = findViewById(R.id.go_to_cart_button) // Inicializa el botón de ir al carrito

        // Configura el listener para el botón de retroceso
        backButton.setOnClickListener {
            finish() // Cierra la actividad y vuelve a la anterior
        }

        // Configura el listener para agregar al carrito
        addToCartButton.setOnClickListener {
            agregarAlCarrito()
        }

        // Configura el listener para ir al carrito
        goToCartButton.setOnClickListener {
            irACarrito() // Llama a la función para ir al carrito
        }

        // Recibir el ID del producto seleccionado
        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        if (productId != -1) {
            obtenerDetallesProducto(productId)
        }
    }

    private fun obtenerDetallesProducto(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.obtenerProducto(id).enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if (response.isSuccessful) {
                    response.body()?.let { producto ->
                        currentProducto = producto // Guarda el producto actual
                        productName.text = producto.title
                        productDescription.text = producto.description
                        productPrice.text = "$${producto.price}"

                        Glide.with(this@DetallesActivity)
                            .load(producto.image)
                            .into(productImage)
                    }
                } else {
                    Toast.makeText(this@DetallesActivity, "Error al cargar el producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                Toast.makeText(this@DetallesActivity, "Error al cargar el producto: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun agregarAlCarrito() {
        // Agrega el producto al carrito usando el producto actual
        Carrito.agregarProducto(currentProducto)
        Toast.makeText(this, "${currentProducto.title} agregado al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun irACarrito() {
        // Navega a CarritoActivity
        val intent = Intent(this, CarritoActivity::class.java)
        startActivity(intent)
    }
}

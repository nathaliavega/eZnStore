package com.example.eznstore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    // Simulando una lista de usuarios
    private val simulatedUsers = listOf(
        User("johnd", "m38rmF$"),
        User("mor_2314", "83r5^_"),
        User("kevinryan", "kev02937@"),
        User("donero", "ewedon"),
        User("derek", "jklg*_56"),
        User("david_r", "3478*#54"),
        User("snyder", "f238&@*$"),
        User("hopkins", "William56$"),
        User("kate_h", "kfejk@*_"),
        User("jimmie_k", "klein*#%*")
    )

    data class User(val username: String, val password: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializando los elementos de la interfaz
        etUsername = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.loginButton)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validar entradas
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                iniciarSesion(username, password)
            }
        }
    }

    private fun iniciarSesion(username: String, password: String) {
        // Verifica si el usuario y la contraseña son correctos
        val user = simulatedUsers.find { it.username == username && it.password == password }

        if (user != null) {
            // Si la respuesta es exitosa, redirigir a ProductosActivity
            Log.d("LoginActivity", "Inicio de sesión exitoso")
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
            // finish() // NO llamamos a finish() para mantener la actividad de Productos
        } else {
            // Si hay un error, mostrar un mensaje
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

}

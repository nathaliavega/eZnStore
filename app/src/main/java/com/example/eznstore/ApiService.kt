package com.example.eznstore.api

import com.example.eznstore.LoginResponse
import com.example.eznstore.Producto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    /**
     * Método para iniciar sesión en la aplicación.
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto [Call] que se puede usar para ejecutar la llamada de inicio de sesión.
     */
    @FormUrlEncoded
    @POST("auth/login")
    fun iniciarSesion(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    /**
     * Método para obtener todos los productos.
     * @return Un objeto [Call] que retorna una lista de [Producto].
     */
    @GET("products")
    fun obtenerProductos(): Call<List<Producto>>

    /**
     * Método para obtener un producto específico por ID.
     * @param id El ID del producto.
     * @return Un objeto [Call] que retorna un [Producto].
     */
    @GET("products/{id}")
    fun obtenerProducto(@Path("id") id: Int): Call<Producto>

    /**
     * Método para obtener todas las categorías de productos.
     * @return Un objeto [Call] que retorna una lista de cadenas que representan las categorías.
     */
    @GET("products/categories")
    fun obtenerCategorias(): Call<List<String>>
}

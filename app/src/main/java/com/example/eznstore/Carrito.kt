package com.example.eznstore

object Carrito {
    private val productosEnCarrito = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        productosEnCarrito.add(producto)
    }

    fun obtenerProductos(): List<Producto> {
        return productosEnCarrito
    }

    fun vaciarCarrito() {
        productosEnCarrito.clear()
    }
}


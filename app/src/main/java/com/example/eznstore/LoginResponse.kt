package com.example.eznstore

data class LoginResponse(
    val token: String,
    val username: String,
    val email: String
)

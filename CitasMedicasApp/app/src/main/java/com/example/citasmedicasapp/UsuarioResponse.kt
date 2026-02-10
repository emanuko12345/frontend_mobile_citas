package com.example.citasmedicasapp

data class UsuarioResponse(
    val id: Int,
    val nombre: String,
    val apellido: String, //
    val email: String,
    val rol: String      // ¡ESTO ES LO IMPORTANTE!
)
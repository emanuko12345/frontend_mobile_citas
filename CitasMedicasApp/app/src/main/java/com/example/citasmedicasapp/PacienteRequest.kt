package com.example.citasmedicasapp

data class PacienteRequest(
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String,
    val cedula: String,
    val telefono: String,
    val fechaNacimiento: String,
    val direccion: String
)
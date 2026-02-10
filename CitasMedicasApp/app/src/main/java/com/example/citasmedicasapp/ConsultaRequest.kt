package com.example.citasmedicasapp

data class ConsultaRequest(
    val citaId: Int,
    val peso: String,
    val altura: String,
    val presion: String,
    val glucosa: String,
    val ritmo: String,
    val diagnostico: String
)
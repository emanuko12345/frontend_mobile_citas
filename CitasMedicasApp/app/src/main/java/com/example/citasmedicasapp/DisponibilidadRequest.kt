package com.example.citasmedicasapp

import com.google.gson.annotations.SerializedName

data class DisponibilidadRequest(
    @SerializedName("fecha")
    val fecha: String, // Formato "yyyy-MM-dd"

    @SerializedName("hora")
    val hora: String,  // Formato "HH:mm"

    @SerializedName("medicoId")
    val medicoId: Int  // ID 3 para Dr. House
)
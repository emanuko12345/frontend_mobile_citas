package com.example.citasmedicasapp

import com.google.gson.annotations.SerializedName

data class ReservarCitaRequest(
    @SerializedName("citaId")
    val citaId: Int,

    @SerializedName("pacienteId")
    val pacienteId: Int
)
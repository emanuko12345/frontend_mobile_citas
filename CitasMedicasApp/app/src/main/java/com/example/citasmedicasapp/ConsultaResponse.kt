package com.example.citasmedicasapp

import com.google.gson.annotations.SerializedName

data class ConsultaResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("diagnostico")
    val diagnostico: String,

    @SerializedName("peso")
    val peso: String,

    @SerializedName("presionArterial")
    val presionArterial: String,

    // Campos adicionales
    @SerializedName("altura")
    val altura: String?,

    @SerializedName("glucosa")
    val glucosa: String?,

    @SerializedName("ritmoCardiaco")
    val ritmoCardiaco: String?,

    @SerializedName("cita")
    val cita: CitaResponse?
)
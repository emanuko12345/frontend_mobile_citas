package com.example.citasmedicasapp

import com.google.gson.annotations.SerializedName

data class AnalisisResponse(
    @SerializedName("dieta") val dieta: String,
    @SerializedName("ejercicio") val ejercicio: String,
    @SerializedName("advertencia") val advertencia: String
)
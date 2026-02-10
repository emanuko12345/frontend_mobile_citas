package com.example.citasmedicasapp

import com.google.gson.annotations.SerializedName

// Es una "data class" para que Kotlin maneje los datos del JSON
data class CitaResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("fecha")
    val fecha: String,

    @SerializedName("hora")
    val hora: String,

    @SerializedName("estado")
    val estado: String,

    @SerializedName("medico")
    val medico: MedicoResponse?,

    // 👇 NUEVO: Agregamos el paciente para poder leer su nombre
    @SerializedName("paciente")
    val paciente: UsuarioSimpleResponse?
)

data class MedicoResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("especialidad")
    val especialidad: String?,

    @SerializedName("usuario")
    val usuario: UsuarioSimpleResponse?
)

data class UsuarioSimpleResponse(
    // Agregamos ID por si acaso lo necesitamos luego
    @SerializedName("id")
    val id: Int?,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido")
    val apellido: String
)
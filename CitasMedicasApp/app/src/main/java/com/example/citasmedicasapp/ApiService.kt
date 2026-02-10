package com.example.citasmedicasapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    // El que ya tenías para registrar
    @POST("/api/pacientes")
    fun registrarPaciente(@Body request: PacienteRequest): Call<Void>

    // PARA EL LOGIN
    @POST("/api/auth/login")
    fun login(@Body request: LoginRequest): Call<UsuarioResponse>

    @POST("/api/citas/crear-disponibilidad")
    fun crearDisponibilidad(@Body request: DisponibilidadRequest): Call<Void>

    // Agrega esto a tu ApiService.kt
    @GET("api/citas")
    fun obtenerCitasDisponibles(): Call<List<CitaResponse>>

    @POST("api/citas/reservar")
    fun reservarCita(@Body request: ReservarCitaRequest): Call<Void>
    // 👇 NUEVO: Obtener solo las citas de un usuario específico
    @GET("api/citas/mis-citas/{id}")
    fun obtenerMisCitas(@Path("id") pacienteId: Int): Call<List<CitaResponse>>


    // 👇 Para que el médico vea sus pacientes
    @GET("api/citas/medico/{id}/agendadas")
    fun obtenerPacientesDelDia(@Path("id") medicoUsuarioId: Int): Call<List<CitaResponse>>

    // En ApiService.kt, agrega esto al final:

    @POST("api/consultas/guardar")
    fun registrarConsulta(@Body consulta: ConsultaRequest): Call<String>

    // 👇 AGREGA ESTA LÍNEA
    @GET("api/consultas/paciente/{id}")
    fun obtenerHistorial(@Path("id") pacienteId: Int): Call<List<ConsultaResponse>>

    // Asegúrate de tener la clase AnalisisResponse creada (ver Paso 3)
    @GET("api/consultas/analisis-ia/{id}")
    fun obtenerAnalisisIA(@Path("id") pacienteId: Int): Call<AnalisisResponse>

}
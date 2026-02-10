package com.example.citasmedicasapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerCitasActivity : AppCompatActivity() {

    private lateinit var adapter: CitaAdapter
    private var userId: Int = -1 // Variable para guardar el ID de Alejandro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        // 1. RECIBIMOS EL ID DEL USUARIO (Importante para saber quién reserva)
        userId = intent.getIntExtra("USER_ID", -1)

        val rv = findViewById<RecyclerView>(R.id.recyclerViewCitas)
        rv.layoutManager = LinearLayoutManager(this)

        // 2. CONFIGURAMOS EL BOTÓN DE RESERVA
        adapter = CitaAdapter(emptyList()) { cita ->
            // En lugar de mostrar solo el mensaje, llamamos a la función de reservar
            reservarCitaEnServidor(cita.id)
        }
        rv.adapter = adapter

        cargarCitas()
    }

    // LÓGICA ENVÍA LA RESERVA AL SERVIDOR
    private fun reservarCitaEnServidor(citaId: Int) {
        if (userId == -1) {
            Toast.makeText(this, "Error: No se identificó al usuario", Toast.LENGTH_SHORT).show()
            return
        }

        // Preparamos el paquete de datos
        val request = ReservarCitaRequest(citaId, userId)

        // Enviamos la petición
        RetrofitClient.instance.reservarCita(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // SI TODO SALIÓ BIEN:
                    Toast.makeText(applicationContext, "¡Cita Reservada con Éxito!", Toast.LENGTH_LONG).show()

                    // Recargamos la lista para que la cita reservada desaparezca
                    cargarCitas()
                } else {
                    // Si el servidor dice que no (ej: alguien más la ganó)
                    Toast.makeText(applicationContext, "No se pudo reservar. Intenta otra.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext, "Fallo de conexión con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarCitas() {
        RetrofitClient.instance.obtenerCitasDisponibles().enqueue(object : Callback<List<CitaResponse>> {
            override fun onResponse(call: Call<List<CitaResponse>>, response: Response<List<CitaResponse>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    // Filtramos solo las que están DISPONIBLES
                    val disponibles = lista.filter { it.estado == "DISPONIBLE" }
                    adapter.actualizarLista(disponibles)
                }
            }

            override fun onFailure(call: Call<List<CitaResponse>>, t: Throwable) {
                Toast.makeText(this@VerCitasActivity, "Error al conectar: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
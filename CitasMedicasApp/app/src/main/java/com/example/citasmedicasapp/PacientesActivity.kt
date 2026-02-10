package com.example.citasmedicasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PacientesActivity : AppCompatActivity() {

    private lateinit var adapter: PacientesAdapter
    private var medicoUserId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes_dia)

        medicoUserId = intent.getIntExtra("USER_ID", -1)

        val rv = findViewById<RecyclerView>(R.id.recyclerViewPacientes)
        rv.layoutManager = LinearLayoutManager(this)

        // CAMBIO CLAVE:
        adapter = PacientesAdapter(emptyList()) { cita ->

            // Al hacer click en "ATENDER", abrimos la hoja clínica
            val intent = Intent(this, AtenderPacienteActivity::class.java)

            // Pasamos el ID de la cita (¡Vital para guardar los datos!)
            intent.putExtra("CITA_ID", cita.id)

            // Pasamos el nombre para mostrarlo en el título
            val nombrePac = cita.paciente?.nombre ?: "Paciente"
            intent.putExtra("NOMBRE_PACIENTE", nombrePac)

            startActivity(intent)
        }

        rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Cargamos la lista cada vez que entras (útil si acabas de finalizar una cita)
        cargarPacientes()
    }

    private fun cargarPacientes() {
        if (medicoUserId == -1) return

        RetrofitClient.instance.obtenerPacientesDelDia(medicoUserId).enqueue(object : Callback<List<CitaResponse>> {
            override fun onResponse(call: Call<List<CitaResponse>>, response: Response<List<CitaResponse>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    adapter.actualizarLista(lista)
                } else {
                    Toast.makeText(applicationContext, "No hay pacientes pendientes", Toast.LENGTH_SHORT).show()
                    // Si no hay pacientes, limpiamos la lista
                    adapter.actualizarLista(emptyList())
                }
            }
            override fun onFailure(call: Call<List<CitaResponse>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
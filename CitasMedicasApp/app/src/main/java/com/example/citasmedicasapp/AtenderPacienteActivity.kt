package com.example.citasmedicasapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtenderPacienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atender_paciente)

        // 1. Recibir datos de la pantalla anterior
        val citaId = intent.getIntExtra("CITA_ID", -1)
        val nombrePaciente = intent.getStringExtra("NOMBRE_PACIENTE") ?: "Paciente"

        findViewById<TextView>(R.id.tvNombrePaciente).text = "Atendiendo a: $nombrePaciente"

        // 2. Botón Finalizar
        findViewById<Button>(R.id.btnFinalizar).setOnClickListener {
            guardarConsulta(citaId)
        }
    }

    private fun guardarConsulta(citaId: Int) {
        // Recolectar datos de las cajas de texto
        val peso = findViewById<EditText>(R.id.etPeso).text.toString()
        val altura = findViewById<EditText>(R.id.etAltura).text.toString()
        val presion = findViewById<EditText>(R.id.etPresion).text.toString()
        val glucosa = findViewById<EditText>(R.id.etGlucosa).text.toString()
        val ritmo = findViewById<EditText>(R.id.etRitmo).text.toString()
        val diagnostico = findViewById<EditText>(R.id.etDiagnostico).text.toString()

        if (diagnostico.isEmpty()) {
            Toast.makeText(this, "El diagnóstico es obligatorio", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el objeto para enviar
        val consulta = ConsultaRequest(citaId, peso, altura, presion, glucosa, ritmo, diagnostico)

        // Enviar al servidor
        RetrofitClient.instance.registrarConsulta(consulta).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "¡Consulta Finalizada!", Toast.LENGTH_LONG).show()
                    finish() // Cierra la pantalla y vuelve a la lista
                } else {
                    Toast.makeText(applicationContext, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(applicationContext, "Fallo de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
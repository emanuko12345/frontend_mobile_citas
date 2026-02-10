package com.example.citasmedicasapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CrearHorarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_horario)

        val etFecha = findViewById<EditText>(R.id.etFechaDisp)
        val etHora = findViewById<EditText>(R.id.etHoraDisp)
        val etId = findViewById<EditText>(R.id.etMedicoIdDisp)
        val btnPublicar = findViewById<Button>(R.id.btnPublicar)
        val btnVolver = findViewById<Button>(R.id.btnVolverMed)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)

        btnPublicar.setOnClickListener {
            val fecha = etFecha.text.toString().trim()
            val horaRaw = etHora.text.toString().trim()
            val idStr = etId.text.toString().trim()

            if (fecha.isEmpty() || horaRaw.isEmpty() || idStr.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Normalización: Asegurar formato HH:mm (ej. "8:30" -> "08:30")
            val horaFormateada = if (horaRaw.length == 4 && horaRaw.contains(":")) {
                "0$horaRaw"
            } else {
                horaRaw
            }

            // el Dr. House es el ID 3
            val request = DisponibilidadRequest(fecha, horaFormateada, idStr.toInt())

            api.crearDisponibilidad(request).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CrearHorarioActivity, "¡Horario Publicado!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        // Si da Error 400, aquí veremos el código exacto
                        Toast.makeText(this@CrearHorarioActivity, "Error ${response.code()}: Revisa el formato o ID", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@CrearHorarioActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnVolver.setOnClickListener { finish() }
    }
}
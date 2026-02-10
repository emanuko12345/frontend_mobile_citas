package com.example.citasmedicasapp

import android.os.Bundle
import android.util.Log // 👈 Para ver en el Logcat de Android Studio
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes_dia)

        val tvTitulo = findViewById<TextView>(R.id.tvTituloGenerico)
        tvTitulo.text = "Mi Historial Médico"

        // 1. Verificamos si el ID llega desde el Home
        val userId = intent.getIntExtra("USER_ID", -1)

        // Mensaje en pantalla para saber qué ID estamos consultando
        Toast.makeText(this, "Consultando historial para ID: $userId", Toast.LENGTH_SHORT).show()
        Log.d("DEBUG_HISTORIAL", "ID recibido del intent: $userId")

        val rv = findViewById<RecyclerView>(R.id.recyclerViewPacientes)
        rv.layoutManager = LinearLayoutManager(this)

        if (userId != -1) {
            cargarDatosConDepuracion(userId, rv)
        } else {
            Toast.makeText(this, "ERROR: No se recibió un ID de usuario válido", Toast.LENGTH_LONG).show()
        }
    }

    private fun cargarDatosConDepuracion(pacienteId: Int, recyclerView: RecyclerView) {
        Log.d("DEBUG_HISTORIAL", "Iniciando llamada a ApiService para ID: $pacienteId")

        RetrofitClient.instance.obtenerHistorial(pacienteId).enqueue(object : Callback<List<ConsultaResponse>> {
            override fun onResponse(call: Call<List<ConsultaResponse>>, response: Response<List<ConsultaResponse>>) {

                // 2. ¿El servidor respondió (aunque sea con error)?
                if (response.isSuccessful) {
                    val historial = response.body() ?: emptyList()

                    Log.d("DEBUG_HISTORIAL", "Servidor respondió. Cantidad de registros: ${historial.size}")
                    Toast.makeText(applicationContext, "Éxito: ${historial.size} registros encontrados", Toast.LENGTH_SHORT).show()

                    if (historial.isEmpty()) {
                        Log.w("DEBUG_HISTORIAL", "La lista llegó vacía del backend")
                    } else {
                        recyclerView.adapter = HistorialAdapter(historial)
                    }
                } else {
                    // 3. El servidor respondió con un error (ej. 404, 500)
                    val errorMsg = "Error del servidor: ${response.code()}"
                    Log.e("DEBUG_HISTORIAL", errorMsg)
                    Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<ConsultaResponse>>, t: Throwable) {
                // 4. Fallo total de conexión (Server apagado, IP mal configurada, etc.)
                val failMsg = "Fallo de conexión: ${t.message}"
                Log.e("DEBUG_HISTORIAL", failMsg)
                Toast.makeText(applicationContext, failMsg, Toast.LENGTH_LONG).show()
            }
        })
    }
}
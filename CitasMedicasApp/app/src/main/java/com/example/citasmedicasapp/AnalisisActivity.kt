package com.example.citasmedicasapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnalisisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analisis_ia)

        // 1. Recibimos el ID de Emanuel (2)
        val userId = intent.getIntExtra("USER_ID", -1)

        val tvAlerta = findViewById<TextView>(R.id.tvAlertaIA)
        val tvDieta = findViewById<TextView>(R.id.tvDietaIA)
        val tvEjercicio = findViewById<TextView>(R.id.tvEjercicioIA)

        if (userId != -1) {
            // 2. Llamamos al Backend
            RetrofitClient.instance.obtenerAnalisisIA(userId).enqueue(object : Callback<AnalisisResponse> {
                override fun onResponse(call: Call<AnalisisResponse>, response: Response<AnalisisResponse>) {
                    if (response.isSuccessful) {
                        val analisis = response.body()

                        // 3. Pintamos la respuesta inteligente
                        tvAlerta.text = analisis?.advertencia ?: "Todo en orden."
                        tvDieta.text = analisis?.dieta ?: "Sin recomendaciones."
                        tvEjercicio.text = analisis?.ejercicio ?: "Mantente activo."
                    } else {
                        tvAlerta.text = "Error al analizar datos: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<AnalisisResponse>, t: Throwable) {
                    tvAlerta.text = "Fallo de conexión con el cerebro IA."
                    Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "Error de usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
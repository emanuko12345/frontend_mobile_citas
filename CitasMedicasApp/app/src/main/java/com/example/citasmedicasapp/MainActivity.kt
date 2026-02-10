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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Configurar Retrofit (IP 10.0.2.2 es para el emulador)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        // 2. Vincular elementos del diseño XML
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etCedula = findViewById<EditText>(R.id.etCedula)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etDireccion = findViewById<EditText>(R.id.etDireccion)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        // 3. Acción al presionar el botón
        btnRegistrar.setOnClickListener {
            val datos = PacienteRequest(
                nombre = etNombre.text.toString(),
                apellido = etApellido.text.toString(),
                email = etEmail.text.toString(),
                password = etPassword.text.toString(),
                cedula = etCedula.text.toString(),
                telefono = etTelefono.text.toString(),
                fechaNacimiento = etFecha.text.toString(),
                direccion = etDireccion.text.toString()
            )

            // Enviar la petición al Backend
            api.registrarPaciente(datos).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "✅ ¡Paciente registrado en la DB!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MainActivity, "❌ Error del servidor: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "⚠️ Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
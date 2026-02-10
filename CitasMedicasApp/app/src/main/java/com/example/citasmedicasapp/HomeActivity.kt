package com.example.citasmedicasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 1. Vincular vistas existentes
        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val btnCerrar = findViewById<Button>(R.id.btnCerrarSesion)
        val btnAgendar = findViewById<Button>(R.id.btnAgendarCita)
        val btnMisCitas = findViewById<Button>(R.id.btnMisCitas)
        val btnHistorial = findViewById<Button>(R.id.btnVerHistorial)

        // Botón para la IA (Asegúrate de que existe en el XML)
        val btnAnalisisIA = findViewById<Button>(R.id.btnAnalisisIA)

        // 2. Recibir datos del Login (Emanuel con ID 2)
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        val userId = intent.getIntExtra("USER_ID", -1)

        tvBienvenida.text = "¡Hola, $nombreUsuario!"

        // --- 3. FIX: LOGIC DE CERRAR SESIÓN SEGURA ---
        btnCerrar.setOnClickListener {
            // Creamos un intento para ir al LoginActivity
            val intent = Intent(this, LoginActivity::class.java)

            // ESTAS BANDERAS SON LA CLAVE:
            // FLAG_ACTIVITY_NEW_TASK: Inicia el Login como una tarea nueva
            // FLAG_ACTIVITY_CLEAR_TASK: Borra todas las pantallas anteriores (Home, etc.)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish() // Destruimos la actividad actual para liberar memoria
        }

        // 4. Botón Agendar Cita (Verde)
        btnAgendar.setOnClickListener {
            val intent = Intent(this, VerCitasActivity::class.java)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            startActivity(intent)
        }

        // 5. Botón Mis Citas (Azul)
        btnMisCitas.setOnClickListener {
            val intent = Intent(this, MisCitasActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

        // 6. Botón Historial Médico (Turquesa)
        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

        // 7. Botón Análisis IA (Violeta/Mágico)
        btnAnalisisIA.setOnClickListener {
            val intent = Intent(this, AnalisisActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
    }
}
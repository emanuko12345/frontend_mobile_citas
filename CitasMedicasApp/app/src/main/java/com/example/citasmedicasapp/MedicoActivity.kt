package com.example.citasmedicasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MedicoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)

        // 1. Vincular las vistas (Tus IDs originales)
        val tvSaludo = findViewById<TextView>(R.id.tvSaludoMedico)
        val btnCerrar = findViewById<Button>(R.id.btnCerrarSesionMed)
        val btnPublicar = findViewById<Button>(R.id.btnIrPublicar)
        val btnVerPacientes = findViewById<Button>(R.id.btnVerPacientes)

        // 2. Recibir datos del Login (Apellido y ID)
        val apellido = intent.getStringExtra("APELLIDO_MEDICO")
        val userId = intent.getIntExtra("USER_ID", -1)

        tvSaludo.text = "Bienvenido, Dr. $apellido"

        // 3. Botón Publicar (Mantenemos tu lógica)
        btnPublicar.setOnClickListener {
            val intent = Intent(this, CrearHorarioActivity::class.java)
            intent.putExtra("MEDICO_ID", userId)
            startActivity(intent)
        }

        // 4. Botón Ver Pacientes (Mantenemos tu lógica)
        btnVerPacientes.setOnClickListener {
            val intent = Intent(this, PacientesActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

        // 5. 🔥 BOTÓN CERRAR SESIÓN (CORREGIDO) 🔥
        btnCerrar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            // Estas banderas son clave: Borran todo el historial de pantallas
            // para que la app no se cuelgue y vaya directo al Login.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
    }
}
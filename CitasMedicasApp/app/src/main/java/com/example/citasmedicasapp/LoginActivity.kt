package com.example.citasmedicasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegistro = findViewById<TextView>(R.id.tvIrRegistro)

        // Usamos la instancia Singleton de Retrofit que ya tienes configurada
        // (Si no tienes RetrofitClient, usa tu código anterior, pero lo ideal es usar el cliente global)
        val api = RetrofitClient.instance

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            api.login(LoginRequest(email, pass)).enqueue(object : Callback<UsuarioResponse> {
                override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        if (usuario != null) {

                            val rolRecibido = usuario.rol

                            // Lógica de navegación
                            val intent = if (rolRecibido == "MEDICO") {
                                Intent(this@LoginActivity, MedicoActivity::class.java).apply {
                                    putExtra("USER_ID", usuario.id) // También le pasamos ID al médico por si acaso
                                    putExtra("APELLIDO_MEDICO", usuario.apellido)
                                }
                            } else {
                                Intent(this@LoginActivity, HomeActivity::class.java).apply {
                                    // 👇 ¡AQUÍ ESTÁ LA SOLUCIÓN!
                                    // Pasamos el ID para que HomeActivity lo tenga y pueda reservar
                                    putExtra("USER_ID", usuario.id)
                                    putExtra("NOMBRE_USUARIO", usuario.nombre)
                                }
                            }
                            startActivity(intent)
                            finish() // Cerramos login para que no pueda volver atrás
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        tvRegistro.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
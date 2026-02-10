package com.example.citasmedicasapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MisCitasActivity : AppCompatActivity() {

    private lateinit var adapter: MisCitasAdapter
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_citas)

        userId = intent.getIntExtra("USER_ID", -1)

        val rv = findViewById<RecyclerView>(R.id.recyclerViewMisCitas)
        rv.layoutManager = LinearLayoutManager(this)

        // Por ahora el botón Cancelar solo mostrará un mensaje
        adapter = MisCitasAdapter(emptyList()) {
            Toast.makeText(this, "Opción de cancelar próximamente...", Toast.LENGTH_SHORT).show()
        }
        rv.adapter = adapter

        cargarMisCitas()
    }

    private fun cargarMisCitas() {
        if (userId == -1) return

        RetrofitClient.instance.obtenerMisCitas(userId).enqueue(object : Callback<List<CitaResponse>> {
            override fun onResponse(call: Call<List<CitaResponse>>, response: Response<List<CitaResponse>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    adapter.actualizarLista(lista)
                } else {
                    Toast.makeText(applicationContext, "No tienes citas aún", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CitaResponse>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
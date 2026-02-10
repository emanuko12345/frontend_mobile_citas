package com.example.citasmedicasapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PacientesAdapter(
    private var citas: List<CitaResponse>,
    private val onAtenderClick: (CitaResponse) -> Unit
) : RecyclerView.Adapter<PacientesAdapter.PacienteViewHolder>() {

    class PacienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Reutilizamos los mismos IDs del diseño item_cita.xml para no trabajar doble
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvDatos: TextView = view.findViewById(R.id.tvMedico)
        val btnAccion: Button = view.findViewById(R.id.btnReservar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return PacienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        val cita = citas[position]

        // 1. Hora
        holder.tvFechaHora.text = "${cita.hora} - ${cita.fecha}"

        // 2. DATOS DEL PACIENTE (Aquí está el cambio mágico) 🪄
        val nombrePaciente = cita.paciente?.nombre ?: "Desconocido"
        val apellidoPaciente = cita.paciente?.apellido ?: ""

        holder.tvDatos.text = "Paciente: $nombrePaciente $apellidoPaciente"

        // 3. Botón "ATENDER"
        holder.btnAccion.text = "ATENDER"
        holder.btnAccion.setBackgroundColor(Color.parseColor("#673AB7")) // Morado Médico

        holder.btnAccion.setOnClickListener { onAtenderClick(cita) }
    }

    override fun getItemCount() = citas.size

    fun actualizarLista(nuevaLista: List<CitaResponse>) {
        citas = nuevaLista
        notifyDataSetChanged()
    }
}
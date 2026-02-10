package com.example.citasmedicasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitaAdapter(
    private var citas: List<CitaResponse>,
    private val onReservarClick: (CitaResponse) -> Unit
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvMedico: TextView = view.findViewById(R.id.tvMedico)
        val btnReservar: Button = view.findViewById(R.id.btnReservar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]

        // 1. Fecha y Hora
        holder.tvFechaHora.text = "${cita.fecha} | ${cita.hora}"

        // 2. Datos del Médico
        val nombre = cita.medico?.usuario?.nombre ?: "Médico"
        val apellido = cita.medico?.usuario?.apellido ?: ""


        // Obtenemos la especialidad. Si viene nula, ponemos "General".
        val especialidad = cita.medico?.especialidad ?: "General"

        // 3. Mostramos: "CARDIOLOGÍA - Dr. Gregory House"
        holder.tvMedico.text = "${especialidad.uppercase()} - Dr. $nombre $apellido"

        // Configuración del botón
        holder.btnReservar.setOnClickListener { onReservarClick(cita) }
    }

    override fun getItemCount() = citas.size

    fun actualizarLista(nuevaLista: List<CitaResponse>) {
        citas = nuevaLista
        notifyDataSetChanged()
    }
}
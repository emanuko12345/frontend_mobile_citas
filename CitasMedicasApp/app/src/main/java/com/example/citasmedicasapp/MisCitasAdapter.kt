package com.example.citasmedicasapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MisCitasAdapter(
    private var citas: List<CitaResponse>,
    private val onCancelarClick: (CitaResponse) -> Unit
) : RecyclerView.Adapter<MisCitasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvMedico: TextView = view.findViewById(R.id.tvMedico)
        val btnAccion: Button = view.findViewById(R.id.btnReservar) // Usamos el mismo botón del XML
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cita = citas[position]

        holder.tvFechaHora.text = "${cita.fecha} | ${cita.hora}"

        val nombre = cita.medico?.usuario?.nombre ?: "Médico"
        val especialidad = cita.medico?.especialidad ?: "General"
        holder.tvMedico.text = "$especialidad - Dr. $nombre"

        // 👇 CAMBIOS VISUALES PARA "MIS CITAS"
        holder.btnAccion.text = "CANCELAR"
        holder.btnAccion.setBackgroundColor(Color.RED) // Botón Rojo de Peligro

        holder.btnAccion.setOnClickListener { onCancelarClick(cita) }
    }

    override fun getItemCount() = citas.size

    fun actualizarLista(nuevaLista: List<CitaResponse>) {
        citas = nuevaLista
        notifyDataSetChanged()
    }
}
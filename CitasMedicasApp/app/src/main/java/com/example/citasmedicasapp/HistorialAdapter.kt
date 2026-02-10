package com.example.citasmedicasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistorialAdapter(private var lista: List<ConsultaResponse>) : RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFecha: TextView = view.findViewById(R.id.tvFechaHora)
        val tvDetalle: TextView = view.findViewById(R.id.tvMedico)
        val btnAccion: View = view.findViewById(R.id.btnReservar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val consulta = lista[position]

        // 1. Datos de la Cita (Fecha y Médico)
        val fecha = consulta.cita?.fecha ?: "---"
        val hora = consulta.cita?.hora ?: ""
        val nombreDoc = consulta.cita?.medico?.usuario?.nombre ?: "Médico"
        val apellidoDoc = consulta.cita?.medico?.usuario?.apellido ?: ""

        holder.tvFecha.text = "$fecha $hora | Dr. $nombreDoc $apellidoDoc"

        // 2. Datos Médicos COMPLETOS
        val diagnostico = consulta.diagnostico ?: "Sin diagnóstico"
        val presion = consulta.presionArterial ?: "N/A"
        val peso = consulta.peso ?: "N/A"
        val altura = consulta.altura ?: "N/A"
        val glucosa = consulta.glucosa ?: "N/A"
        val ritmo = consulta.ritmoCardiaco ?: "N/A"

        // Construimos el texto multilínea para que se vea todo en la tarjeta
        holder.tvDetalle.text = """
            Dx: $diagnostico
            Signos: PA: $presion | Peso: ${peso}kg | Altura: ${altura}cm
            Extras: Glucosa: $glucosa | Ritmo: $ritmo
        """.trimIndent()

        // 3. Ocultamos el botón de acción ya que es solo lectura
        holder.btnAccion.visibility = View.GONE
    }

    override fun getItemCount() = lista.size

    fun actualizarLista(nuevaLista: List<ConsultaResponse>) {
        this.lista = nuevaLista
        notifyDataSetChanged()
    }
}
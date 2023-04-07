package com.example.pharmkare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.pharmkare.datos.Medicamentos

class AdaptadorMedicamento(private val context: Activity, var medicamentos: List<Medicamentos>) :
        ArrayAdapter<Medicamentos?>(context, R.layout.activity_adaptador_medicamento,medicamentos){

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {


        val layoutInflater = context.layoutInflater
        var rowview: View? = null

        rowview = view ?: layoutInflater.inflate(R.layout.activity_adaptador_medicamento, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvPrecio = rowview.findViewById<TextView>(R.id.tvPrecio)
        val tvIndicaciones = rowview.findViewById<TextView>(R.id.tvIndicaciones)
        val tvContraIndicaciones = rowview.findViewById<TextView>(R.id.tvContraIndicaciones)


        // Obtener el producto en la posición actual
        val medicamento = getItem(position)!!

        // Mostrar el nombre del producto
        val textViewNombre = rowview.findViewById<TextView>(R.id.tvNombre)
        textViewNombre.text = medicamento.nombre

        val textViewPrecio = rowview.findViewById<TextView>(R.id.tvPrecio)
        textViewNombre.text = medicamento.precio

        // Agregar un listener al botón para realizar la acción deseada
        val btnAgregar = rowview.findViewById<Button>(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            val intent = Intent(context, DetalleCompraActivity::class.java)

            intent.putExtra("nombre", medicamento.nombre)
            intent.putExtra("precio", medicamento.precio)

            context.startActivity(intent)

        }

        tvNombre.text = "Nombre : " + medicamentos[position].nombre
        tvPrecio.text = "Precio: $ " + medicamentos[position].precio
        tvIndicaciones.text = "Indicaciones : " + medicamentos[position].indicaciones
        tvContraIndicaciones.text = "Contra-indicaciones : " + medicamentos[position].contraindicaciones
        return rowview
    }

        }
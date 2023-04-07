package com.example.pharmkare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView


class HistorialCompraActivity : AppCompatActivity() {


    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_compra)

        // Inicializa listView después de que la vista se haya inflado
        listView = findViewById(R.id.listView)

        // Obtiene la lista de detalles de compra del intent
        val detallesCompra = intent.getSerializableExtra("detallesCompra") as Array<DetalleCompra>

        // Crea un adapter para mostrar los detalles de compra en el ListView
        val adapter = ArrayAdapter<DetalleCompra>(
            this,
            android.R.layout.simple_list_item_1,
            detallesCompra
        )

        // Asigna el adapter al ListView
        listView.adapter = adapter
    }
}
package com.example.pharmkare


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*


class DetalleCompraActivity : AppCompatActivity() {

    private val detallesCompra = mutableListOf<DetalleCompra>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_compra)

        val nombreMedicamento = intent.getStringExtra("nombre")
        val precioMedicamento = intent.getStringExtra("precio")
        val precioMedicamento1 = intent.getStringExtra("precio")

        val nombreTextView = findViewById<TextView>(R.id.nombre1TextView)
        val precioTextView = findViewById<TextView>(R.id.precio1TextView)
        val precio1Textview = findViewById<TextView>(R.id.precio2TextView)

        nombreTextView.text = nombreMedicamento
        precioTextView.text = precioMedicamento
        precio1Textview.text= precioMedicamento



// Convertir el precio a un número flotante
        val precio = precioMedicamento!!.toFloat()

// Calcular el valor del IVA
        val iva = precio * 0.13

// Calcular el subtotal (precio sin IVA)
        val subtotal = precio - iva

// Mostrar el subtotal y el IVA en los TextView correspondientes
        findViewById<TextView>(R.id.SubtotalTextView).text = subtotal.toString()
        findViewById<TextView>(R.id.ivaTextView).text = iva.toString()

        val btnComprar = findViewById<Button>(R.id.btnCompar)
        val btnHistorial = findViewById<Button>(R.id.btnHistorial)




        btnComprar.setOnClickListener {

            // Crea un objeto DetalleCompra con el nombre del medicamento, el subtotal y la fecha actual
            val detalleCompra = DetalleCompra(nombreMedicamento.toString(), subtotal, Date())

            // Agrega el objeto a la lista de detalles de compra
            detallesCompra.add(detalleCompra)

            // Muestra un mensaje de confirmación
            Toast.makeText(
                this,
                "Compra realizada con éxito",
                Toast.LENGTH_SHORT
            ).show()

        }

        btnHistorial.setOnClickListener {
            // Inicia la actividad de historial y pasa la lista de detalles de compra como extra en el intent
            val intent = Intent(this, HistorialCompraActivity::class.java)
            intent.putExtra("detallesCompra", detallesCompra.toTypedArray())
            startActivity(intent)
        }

    }
}


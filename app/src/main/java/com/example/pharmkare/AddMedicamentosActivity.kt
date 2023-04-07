package com.example.pharmkare


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pharmkare.datos.Medicamentos
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddMedicamentosActivity : AppCompatActivity() {

    private var edtNombre: EditText? = null
    private var edtPrecio: EditText? = null
    private var edtIndicaciones: EditText? = null
    private var edtContraIndicaciones: EditText? = null
    private var key = ""
    private var nombre = ""
    private var precio = ""
    private var indicaciones = ""
    private var contraIndicaciones = ""
    private var accion = ""
    private lateinit var database:DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicamentos)
        inicializar()
    }

    private fun inicializar(){
        edtNombre = findViewById<EditText>(R.id.nombreEditText)
        edtPrecio = findViewById<EditText>(R.id.precioEditText)
        edtIndicaciones = findViewById<EditText>(R.id.indicacionesEditText)
        edtContraIndicaciones = findViewById<EditText>(R.id.contraIndicacionesEditText)

        val edtNombre = findViewById<EditText>(R.id.nombreEditText)
        val edtPrecio = findViewById<EditText>(R.id.precioEditText)
        val edtIndicaciones = findViewById<EditText>(R.id.indicacionesEditText)
        val edtContraIndicaciones = findViewById<EditText>(R.id.contraIndicacionesEditText)

        //Obtencion de datos

        val datos: Bundle? = intent.getExtras()

        if(datos !=null){
            key = datos.getString("key").toString()
        }

        if (datos != null){
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }

        if (datos != null){
            edtPrecio.setText(intent.getStringExtra("precio").toString())
        }

        if (datos != null){
            edtIndicaciones.setText(intent.getStringExtra("indicaciones").toString())
        }

        if (datos != null){
            edtContraIndicaciones.setText(intent.getStringExtra("contraindicaciones").toString())
        }


        if (datos != null){
            accion = datos.getString("accion").toString()
        }

    }

    fun guardar(v: View?){

        val nombre: String = edtNombre?.text.toString()
        val precio: String = edtPrecio?.text.toString()
        val indicaciones: String = edtIndicaciones?.text.toString()
        val contraIndicaciones: String = edtContraIndicaciones?.text.toString()

        database = FirebaseDatabase.getInstance().getReference("medicamentos")

        //Se forma objeto medicamento

        val medicamento = Medicamentos(nombre, precio, indicaciones, contraIndicaciones)

        if (accion == "a"){ //Agregar registro
            database.child(nombre).setValue(medicamento).addOnSuccessListener {
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }else { //Editar registro
            val key = database.child("nombre").push().key
            if (key == null){
                Toast.makeText(this,"Llave vacia",Toast.LENGTH_SHORT).show()
            }

            val medicamentosValues = medicamento.toMap()
            val childUpdates = hashMapOf<String,Any>(
                "$nombre" to medicamentosValues
            )

            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizo con exito",Toast.LENGTH_SHORT).show()
        }

        finish()

    }


    fun cancelar(v: View?){
        finish()
    }
}


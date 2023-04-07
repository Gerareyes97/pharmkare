package com.example.pharmkare


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import com.example.pharmkare.datos.Medicamentos
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MedicamentosActivity : AppCompatActivity() {

    var consultaOrdenada: Query = refMedicamentos.orderByChild("nombre")
    var medicamentos: MutableList<Medicamentos>? = null
    var listaMedicamentos: ListView? = null




    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamentos)
        inicializar()
    }

    private fun inicializar(){
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaMedicamentos = findViewById<ListView>(R.id.ListaMedicamentos)

        //Cuando el usuario haga clic en la lista (para editar registro)

        listaMedicamentos!!.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(),AddMedicamentosActivity::class.java)

                intent.putExtra("accion","e") //Editar
                intent.putExtra("key", medicamentos!![i].key)
                intent.putExtra("nombre",medicamentos!![i].nombre)
                intent.putExtra("precio",medicamentos!![i].precio)
                intent.putExtra("indicaciones",medicamentos!![i].indicaciones)
                intent.putExtra("contraindicaciones",medicamentos!![i].contraindicaciones)
                startActivity(intent)
            }
        })

        //Cuando el ususario hace un LongcLICL

        listaMedicamentos!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {

                val ad = AlertDialog.Builder(this@MedicamentosActivity)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    medicamentos!![position].nombre?.let {
                        refMedicamentos.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@MedicamentosActivity,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@MedicamentosActivity,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true

            }
        }

        fab_agregar.setOnClickListener({
            val i = Intent(getBaseContext(),AddMedicamentosActivity::class.java)
            i.putExtra("accion","a") //Agregar
            i.putExtra("key","")
            i.putExtra("nombre","")
            i.putExtra("precio","")
            i.putExtra("indicaciones","")
            i.putExtra("contraindicaciones","")
            startActivity(i)
        })

        medicamentos = ArrayList<Medicamentos>()

        consultaOrdenada.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                medicamentos!!.removeAll(medicamentos!!)
                for (dato in dataSnapshot.getChildren()){
                    val medicamento: Medicamentos? = dato.getValue(Medicamentos::class.java)
                    medicamento?.key(dato.key)
                    if (medicamento != null){
                        medicamentos!!.add(medicamento)

                    }
                }

                val adapter = AdaptadorMedicamento(
                    this@MedicamentosActivity,
                    medicamentos as ArrayList<Medicamentos>
                )

                listaMedicamentos!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }


    companion object{
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refMedicamentos: DatabaseReference = database.getReference("medicamentos")
    }
}
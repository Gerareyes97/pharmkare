package com.example.pharmkare.datos

class Medicamentos {

    fun key(key: String?) {
    }

    var nombre: String? = null
    var precio: String? = null
    var indicaciones: String? = null
    var contraindicaciones: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()


    constructor() {}
    constructor(nombre: String?, precio: String?, indicaciones: String?,contraIndicaciones: String?) {
        this.nombre = nombre
        this.precio = precio
        this.indicaciones = indicaciones
        this.contraindicaciones = contraIndicaciones

    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "precio" to precio,
            "indicaciones" to indicaciones,
            "contraindicaciones" to contraindicaciones,
            "key" to key,
            "per" to per
        )
    }
}
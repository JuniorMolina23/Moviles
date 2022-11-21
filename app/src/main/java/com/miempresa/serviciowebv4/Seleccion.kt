package com.miempresa.serviciowebv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_seleccion.*

class Seleccion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion)

        btnUsuarios.setOnClickListener(){
            val llamaractividad = Intent(applicationContext, listado_usuarios::class.java)
            startActivity(llamaractividad)
        }
        btnPeliculas.setOnClickListener(){
            val llamaractividad = Intent(applicationContext, listado_peliculas::class.java)
            startActivity(llamaractividad)
        }
    }
}
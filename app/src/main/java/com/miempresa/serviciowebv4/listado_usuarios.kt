package com.miempresa.serviciowebv4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_usuarios.*
import kotlinx.android.synthetic.main.activity_listado_usuarios.btnSalir
import kotlinx.android.synthetic.main.activity_listado_usuarios.lista
import org.json.JSONException

class listado_usuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_usuarios)
        btnSalir.setOnClickListener(){
            val llamaractividad = Intent(applicationContext, MainActivity::class.java)
            startActivity(llamaractividad)
            finish()
        }
        cargarUsuarios()
    }
    fun cargarUsuarios() {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Usuarios>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/usuarios"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val nombre =
                                response.getJSONObject(i).getString("usuario")
                            val clave =
                                response.getJSONObject(i).getString("clave")
                            val estado =
                                response.getJSONObject(i).getString("estado")
                            llenarLista.add(Usuarios(id.toInt(),nombre,clave,estado))
                        }
                        val adapter = AdaptadorUsuarios(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }
}
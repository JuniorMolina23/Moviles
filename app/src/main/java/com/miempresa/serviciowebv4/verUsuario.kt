package com.miempresa.serviciowebv4

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ver_pelicula.*
import kotlinx.android.synthetic.main.activity_ver_usuario.*
import kotlinx.android.synthetic.main.activity_ver_usuario.btnActualizar
import kotlinx.android.synthetic.main.activity_ver_usuario.btnEliminar
import kotlinx.android.synthetic.main.activity_ver_usuario.txtId
import kotlinx.android.synthetic.main.activity_ver_usuario.txtNombre

class verUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuario)

        val estados = arrayOf("A","X")
        cmbEstado.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, estados
            )
        )
        val bundle :Bundle ?=intent.extras
        if (bundle != null){
            txtId.setText(bundle.get("id").toString())
            txtNombre.setText(bundle.getString("usuario").toString())
            txtClave.setText(bundle.getString("clave").toString())
            when(bundle.getString("categoria").toString()){
                "A" -> cmbEstado.setSelection(0)
                "X" -> cmbEstado.setSelection(1)
            }
            txtId.setEnabled(false)
        }
        btnEliminar.setOnClickListener(){
            eliminarUsuario()
        }
        btnActualizar.setOnClickListener(){
            editarUsuario()
        }
    }
    fun eliminarUsuario() {
        AsyncTask.execute {
            val id = txtId.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios/" + id
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.DELETE, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro eliminado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al eliminar el registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ){}
            queue.add(postRequest)
        }
    }
    fun editarUsuario() {
        AsyncTask.execute {

            val id = txtId.text.toString()
            val nombre = txtNombre.text.toString()
            val clave = txtClave.text.toString()
            val estado = cmbEstado.selectedItem.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios/"+id
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.PUT, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro actualizado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al atualizar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["usuario"] = nombre
                    params["clave"] = clave
                    params["estado"] = estado
                    return params
                }
            }
            queue.add(postRequest)
        }
    }
}
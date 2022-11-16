package com.miempresa.serviciowebv4

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        btnLogear.setOnClickListener(){
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = "http://192.168.0.112:3000/usuarios?"
            url = url + "usuario=" + usuario + "&clave=" + clave

            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        val valor = response.getJSONObject(0)
                        Toast.makeText(
                            applicationContext,
                            "validacion de usuario: " + valor.getString("usuario")+
                                    " con clave: " + valor.getString("clave")+" correcta",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e:JSONException){
                        Toast.makeText(
                            applicationContext,
                            "Error en las credenciales proporcionadas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Compruebe que tiene acceso a internet: ",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            queue.add(stringRequest)

        }
        btnRegistrar.setOnClickListener(){
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            Verificar(usuario, clave)

        }
        btnSalir.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Salir")
            builder.setMessage("¿Esta seguro de salir?")

            builder.setPositiveButton(android.R.string.yes){ dialog, which ->
                finish()
            }
            builder.setNegativeButton(android.R.string.no){dialog, which ->

            }
            builder.show()
        }
    }

    private fun Registrar() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.112:3000/usuarios"
        val usuario = txtUsuario.text.toString()
        val clave = txtClave.text.toString()

        val stringReq : StringRequest =
            object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    var strResp = response.toString()
                    Log.d("API",strResp)
                },
                Response.ErrorListener{
                    Toast.makeText(
                        applicationContext,
                        "Compruebe que tiene acceso a internet: ",
                        Toast.LENGTH_SHORT
                    ).show()
                }){
                override fun getParams(): MutableMap<kotlin.String, kotlin.String>? {
                    val params: HashMap<kotlin.String, kotlin.String> = hashMapOf()
                    params.put("usuario",usuario)
                    params.put("clave",clave)
                    params.put("estado","A")
                    return params
                }
            }
        queue.add(stringReq)
    }

    private fun Verificar(usuario:String, clave: String){
        val queue = Volley.newRequestQueue(this)
        var url = "http://192.168.0.112:3000/usuarios?"
        url = url + "usuario=" + usuario + "&clave=" + clave

        val stringRequest = JsonArrayRequest(url,
            Response.Listener { response ->
                try {
                    val valor = response.getJSONObject(0)
                    Toast.makeText(
                        applicationContext,
                        "El usuario ya existe",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e:JSONException){
                    Registrar()
                    Toast.makeText(
                        applicationContext,
                        "El usuario ha sido registrado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, Response.ErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Compruebe que tiene acceso a internet: ",
                    Toast.LENGTH_SHORT
                ).show()
            })
        queue.add(stringRequest)
    }
}
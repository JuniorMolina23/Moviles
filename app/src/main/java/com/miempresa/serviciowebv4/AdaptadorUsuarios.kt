package com.miempresa.serviciowebv4

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class AdaptadorUsuarios(val ListaUsuarios:ArrayList<Usuarios>): RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder>() {
    override fun getItemCount(): Int {
        return ListaUsuarios.size;
    }
    class ViewHolder (itemView : View):RecyclerView.ViewHolder(itemView) {
        val fNombre = itemView.findViewById<TextView>(R.id.elemento_nombre);
        val fEstado = itemView.findViewById<TextView>(R.id.elemento_estado)

        //set the onclick listener for the singlt list item
    }
    override fun onBindViewHolder(holder: AdaptadorUsuarios.ViewHolder, position: Int) {
        holder?.fNombre?.text=ListaUsuarios[position].usuario
        holder?.fEstado?.text=ListaUsuarios[position].estado
        var id =ListaUsuarios[position].id
        var usuario =ListaUsuarios[position].usuario
        var clave =ListaUsuarios[position].clave
        var estado =ListaUsuarios[position].estado

        holder.itemView.setOnClickListener(){
            val llamarActividad = Intent(holder.itemView.context, verUsuario::class.java)
            llamarActividad.putExtra("id",id.toString())
            llamarActividad.putExtra("usuario",usuario.toString())
            llamarActividad.putExtra("clave",clave.toString())
            llamarActividad.putExtra("estado",estado.toString())
            holder.itemView.context.startActivity(llamarActividad)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.usuarioslista, parent, false);
        return ViewHolder(v);
    }



}
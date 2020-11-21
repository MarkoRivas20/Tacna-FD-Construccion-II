package com.example.tacnafdcliente.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CuponUsuario_Adaptador extends RecyclerView.Adapter<CuponUsuario_Adaptador.CuponUsuario_ViewHolder> implements View.OnClickListener{

    private List<CuponUsuario_Modelo> Cupones_Usuario;
    private Context Contexto;
    private View.OnClickListener Listener;

    @Override
    public void onClick(View v) {
        if (Listener != null)
        {
            Listener.onClick(v);
        }

    }

    public void setOnClickListener (View.OnClickListener listener){
        this.Listener = listener;
    }


    public static class CuponUsuario_ViewHolder extends RecyclerView.ViewHolder{

        public ImageView Imagen_Recycler_Cupon_Usuario;
        public TextView TxtEstablecimiento_Recycler_Cupon_Usuario;
        public TextView TxtTitulo_Recycler_Cupon_Usuario;

        public CuponUsuario_ViewHolder(View view) {
            super(view);

            Imagen_Recycler_Cupon_Usuario = (ImageView) view.findViewById(R.id.Imagen_Recycler_Cupon_Usuario);
            TxtEstablecimiento_Recycler_Cupon_Usuario = (TextView) view.findViewById(R.id.TxtEstablecimiento_Recycler_Cupon_Usuario);
            TxtTitulo_Recycler_Cupon_Usuario = (TextView) view.findViewById(R.id.TxtTitulo_Recycler_Cupon_Usuario);

        }

        public void bindData (CuponUsuario_Modelo Cupon_Usuario, Context Contexto) {

            Picasso.get().load(Cupon_Usuario.getUrl_Imagen_Cupon()).into(Imagen_Recycler_Cupon_Usuario);
            TxtEstablecimiento_Recycler_Cupon_Usuario.setText(Cupon_Usuario.getNombre_Establecimiento());
            TxtTitulo_Recycler_Cupon_Usuario.setText(Cupon_Usuario.getTitulo_Cupon());
        }
    }

    public CuponUsuario_Adaptador (List<CuponUsuario_Modelo> Cupones_Usuario, Context context) {
        this.Cupones_Usuario = Cupones_Usuario;
        Contexto = context;
    }

    @Override
    public CuponUsuario_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cupones_usuario, parent, false);
        v.setOnClickListener(this);
        return new CuponUsuario_Adaptador.CuponUsuario_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CuponUsuario_Adaptador.CuponUsuario_ViewHolder holder, int position) {
        holder.bindData(Cupones_Usuario.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Cupones_Usuario.size();
    }

}
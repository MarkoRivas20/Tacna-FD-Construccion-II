package com.example.tacnafdbusiness.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;

import java.util.List;

public class Resena_Adaptador extends RecyclerView.Adapter <Resena_Adaptador.ResenaViewHolder>{

    private List<Resena_Modelo> Resenas;
    private Context Contexto;

    public static class ResenaViewHolder extends RecyclerView.ViewHolder {

        public RatingBar Ratingbar_Resenas;
        public TextView TxtNombre_Recycler_Resena;
        public TextView TxtDescripcion_Recycler_Resena;
        public TextView TxtFecha_Recycler_Resena;
        public TextView TxtPuntuacion_Recycler_Resena;

        public ResenaViewHolder (View v) {
            super(v);

            Ratingbar_Resenas = (RatingBar) v.findViewById(R.id.ratingbar_resenas);
            TxtNombre_Recycler_Resena = (TextView) v.findViewById(R.id.TxtNombre_Recycler_Resena);
            TxtDescripcion_Recycler_Resena = (TextView) v.findViewById(R.id.TxtDescripcion_Recycler_Resena);
            TxtFecha_Recycler_Resena = (TextView) v.findViewById(R.id.TxtFecha_Recycler_Resena);
            TxtPuntuacion_Recycler_Resena = (TextView) v.findViewById(R.id.Txtpuntuacion_recycler_Resena);
        }

        public void bindData (Resena_Modelo Resena, Context Contexto) {

            Ratingbar_Resenas.setRating(Resena.getCalificacion().floatValue());
            TxtNombre_Recycler_Resena.setText(Resena.getNombre_Cliente());
            TxtDescripcion_Recycler_Resena.setText(Resena.getDescripcion());
            TxtFecha_Recycler_Resena.setText(Resena.getFecha());
            TxtPuntuacion_Recycler_Resena.setText(String.valueOf(Resena.getCalificacion()));
        }
    }

    public Resena_Adaptador (List<Resena_Modelo> Resenas, Context context) {
        this.Resenas = Resenas;
        Contexto = context;
    }

    @Override
    public ResenaViewHolder onCreateViewHolder (ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_resenas, parent, false);

        return new ResenaViewHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull ResenaViewHolder holder, int position) {

        holder.bindData(Resenas.get(position), Contexto);

    }

    @Override
    public int getItemCount() {
        return Resenas.size();
    }

}

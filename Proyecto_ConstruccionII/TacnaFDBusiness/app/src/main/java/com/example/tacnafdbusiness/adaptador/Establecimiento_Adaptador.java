package com.example.tacnafdbusiness.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Establecimiento_Adaptador extends RecyclerView.Adapter<Establecimiento_Adaptador.Establecimiento_ViewHolder> {

    private List<Establecimiento_Modelo> Items;
    private Context Contexto;


    public static class Establecimiento_ViewHolder extends RecyclerView.ViewHolder{

        public ImageView Imagen_Recycler_Establecimiento;
        public ImageView Imagen_Circle;
        public ImageView Imagen_Comentarios;
        public TextView Txtnombre_Recycler_Establecimiento;
        public TextView Txtpuntuacion_recycler_Establecimiento;
        public TextView Txttotalreseña_recycler_Establecimiento;

        public Establecimiento_ViewHolder(View view) {
            super(view);

            Imagen_Recycler_Establecimiento = (ImageView) view.findViewById(R.id.Imagen_Recycler_Establecimiento);
            Imagen_Circle = (ImageView) view.findViewById(R.id.Imagen_Circle);
            Imagen_Comentarios = (ImageView) view.findViewById(R.id.Imagen_Comentario);
            Txtnombre_Recycler_Establecimiento = (TextView) view.findViewById(R.id.Txtnombre_Recycler_Establecimiento);
            Txtpuntuacion_recycler_Establecimiento = (TextView) view.findViewById(R.id.Txtpuntuacion_recycler_Establecimiento);
            Txttotalreseña_recycler_Establecimiento = (TextView) view.findViewById(R.id.Txttotalreseña_recycler_Establecimiento);

        }

        public void bindData (Establecimiento_Modelo dataModel, Context context) {

            Picasso.with(context).load(dataModel.getUrl_Imagen_Logo()).into(Imagen_Recycler_Establecimiento);
            Imagen_Circle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_rating));
            Imagen_Comentarios.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_person));
            Txtnombre_Recycler_Establecimiento.setText(dataModel.getNombre());
            Txtpuntuacion_recycler_Establecimiento.setText(String.valueOf(dataModel.getPuntuacion()));
            Txttotalreseña_recycler_Establecimiento.setText(String.valueOf(dataModel.getTotalResenas()));
        }
    }

    public Establecimiento_Adaptador (List<Establecimiento_Modelo> items, Context context) {
        this.Items = items;
        Contexto = context;
    }

    @Override
    public Establecimiento_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_establecimientos, parent, false);

        return new Establecimiento_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Establecimiento_Adaptador.Establecimiento_ViewHolder holder, int position) {
        holder.bindData(Items.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }
}

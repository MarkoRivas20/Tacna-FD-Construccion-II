package com.example.tacnafddelivery.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Establecimiento_Adaptador extends RecyclerView.Adapter<Establecimiento_Adaptador.Establecimiento_ViewHolder> implements View.OnClickListener {

    private List<Establecimiento_Modelo> Establecimientos;
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


    public static class Establecimiento_ViewHolder extends RecyclerView.ViewHolder{


        public ImageView Imagen_Recycler_Establecimiento;
        public TextView Txtnombre_Recycler_Establecimiento;
        public TextView TxtDistrito_Recycler_Establecimiento;
        public TextView TxtDireccion_Recycler_Establecimiento;

        public Establecimiento_ViewHolder(View view) {
            super(view);

            Imagen_Recycler_Establecimiento = (ImageView) view.findViewById(R.id.Imagen_Recycler_Establecimiento);
            Txtnombre_Recycler_Establecimiento = (TextView) view.findViewById(R.id.Txtnombre_Recycler_Establecimiento);
            TxtDistrito_Recycler_Establecimiento = (TextView) view.findViewById(R.id.TxtDistrito_Recycler_Establecimiento);
            TxtDireccion_Recycler_Establecimiento = (TextView) view.findViewById(R.id.TxtDireccion_Recycler_Establecimiento);

        }

        public void bindData (Establecimiento_Modelo Establecimiento, Context Contexto) {

            Picasso.with(Contexto).load(Establecimiento.getUrl_Imagen_Logo()).into(Imagen_Recycler_Establecimiento);
            Txtnombre_Recycler_Establecimiento.setText(Establecimiento.getNombre());
            TxtDistrito_Recycler_Establecimiento.setText(String.valueOf(Establecimiento.getDistrito()));
            TxtDireccion_Recycler_Establecimiento.setText(String.valueOf(Establecimiento.getDireccion()));
        }
    }

    public Establecimiento_Adaptador (List<Establecimiento_Modelo> Establecimientos, Context context) {
        this.Establecimientos = Establecimientos;
        Contexto = context;
    }

    @Override
    public Establecimiento_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_establecimiento, parent, false);
        v.setOnClickListener(this);
        return new Establecimiento_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Establecimiento_Adaptador.Establecimiento_ViewHolder holder, int position) {
        holder.bindData(Establecimientos.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Establecimientos.size();
    }
}

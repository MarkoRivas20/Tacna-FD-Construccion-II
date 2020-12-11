package com.example.tacnafdcliente.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EstablecimientoInicio_Adaptador extends RecyclerView.Adapter<EstablecimientoInicio_Adaptador.EstablecimientoInicioViewHolder> implements View.OnClickListener{

    private List<Establecimiento_Modelo> Establecimiento;

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

    public class EstablecimientoInicioViewHolder extends RecyclerView.ViewHolder {

        public ImageView Imagen_Recycler_Establecimiento_Inicio;
        public TextView Txtpuntuacion_Recycler_Establecimiento_Inicio;
        public TextView Txtnombre_Recycler_Establecimiento_Inicio;

        public EstablecimientoInicioViewHolder (View v) {
            super(v);
            Imagen_Recycler_Establecimiento_Inicio = (ImageView) v.findViewById(R.id.Imagen_Recycler_Establecimiento_Inicio);
            Txtpuntuacion_Recycler_Establecimiento_Inicio = (TextView) v.findViewById(R.id.Txtpuntuacion_Recycler_Establecimiento_Inicio);
            Txtnombre_Recycler_Establecimiento_Inicio = (TextView) v.findViewById(R.id.Txtnombre_Recycler_Establecimiento_Inicio);
        }

        public void bindData (Establecimiento_Modelo Establecimiento, Context Contexto) {
            Picasso.get().load(Establecimiento.getUrl_Imagen_Logo()).into(Imagen_Recycler_Establecimiento_Inicio);
            Txtnombre_Recycler_Establecimiento_Inicio.setText(Establecimiento.getNombre());
            Txtpuntuacion_Recycler_Establecimiento_Inicio.setText(String.valueOf(Establecimiento.getPuntuacion()));
        }
    }


    public EstablecimientoInicio_Adaptador(List<Establecimiento_Modelo> Establecimiento, Context context) {
        this.Establecimiento = Establecimiento;
        Contexto = context;
    }

    @Override
    public EstablecimientoInicio_Adaptador.EstablecimientoInicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_establecimientos_inicio, parent, false);
        v.setOnClickListener(this);
        return new EstablecimientoInicio_Adaptador.EstablecimientoInicioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EstablecimientoInicio_Adaptador.EstablecimientoInicioViewHolder holder, int position) {
        holder.bindData(Establecimiento.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Establecimiento.size();
    }

}

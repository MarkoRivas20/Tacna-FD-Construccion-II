package com.example.tacnafdcliente.adaptador;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Cupon_Adaptador extends RecyclerView.Adapter<Cupon_Adaptador.CuponViewHolder>{

    private List<Cupon_Modelo> Cupones;

    private Context Contexto;

    public class CuponViewHolder extends RecyclerView.ViewHolder {

        public ImageView Imagen;
        public TextView TxtTitulo;

        public CuponViewHolder (View v) {
            super(v);
            Imagen = (ImageView) v.findViewById(R.id.Imagen_Recycler_Cupon);
            TxtTitulo = (TextView) v.findViewById(R.id.Txttitulo_Recycler_Cupon);
        }

        public void bindData (Cupon_Modelo Cupon, Context Contexto) {
            Picasso.get().load(Cupon.getUrl_Imagen()).into(Imagen);
            TxtTitulo.setText(Cupon.getTitulo());
        }
    }


    public Cupon_Adaptador(List<Cupon_Modelo> Cupones, Context context) {
        this.Cupones = Cupones;
        Contexto = context;
    }


    @Override
    public CuponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cupones, parent, false);
        return new Cupon_Adaptador.CuponViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Cupon_Adaptador.CuponViewHolder holder, int position) {
        holder.bindData(Cupones.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Cupones.size();
    }


}

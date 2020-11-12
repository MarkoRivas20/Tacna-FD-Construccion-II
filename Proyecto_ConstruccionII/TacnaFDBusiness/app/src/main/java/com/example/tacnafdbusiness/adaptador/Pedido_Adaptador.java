package com.example.tacnafdbusiness.adaptador;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;

import java.util.List;

public class Pedido_Adaptador extends RecyclerView.Adapter <Pedido_Adaptador.PedidoViewHolder>{

    private List<Pedido_Modelo> Pedidos;
    private Context Contexto;

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {

        public TextView TxtNombre_Recycler_Pedido;
        public TextView TxtDescripcion_Recycler_Pedido;
        public TextView TxtFecha_Recycler_Pedido;
        public TextView TxtPrecio_Recycler_Pedido;
        public ImageView Imagen_Recycler_Color;

        public PedidoViewHolder (View v) {
            super(v);

            TxtNombre_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtNombre_Recycler_Pedido);
            TxtDescripcion_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtDescripcion_Recycler_Pedido);
            TxtFecha_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtFecha_Recycler_Pedido);
            TxtPrecio_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtPrecio_Recycler_Pedido);
            Imagen_Recycler_Color = (ImageView) v.findViewById(R.id.Imagen_Recycler_Color);
        }

        public void bindData (Pedido_Modelo Pedido, Context Contexto) {

            TxtNombre_Recycler_Pedido.setText(Pedido.getNombre_Cliente());
            TxtDescripcion_Recycler_Pedido.setText(Pedido.getDescripcion());
            TxtFecha_Recycler_Pedido.setText(Pedido.getFecha());
            TxtPrecio_Recycler_Pedido.setText("S/ " + Pedido.getPrecio_Total());

            switch (Pedido.getEstado()) {
                case "Pendiente":
                    Imagen_Recycler_Color.setBackgroundColor(Color.parseColor("#F1C40F"));
                    break;
                case "En camino":
                    Imagen_Recycler_Color.setBackgroundColor(Color.parseColor("#00B0D9"));
                    break;
                case "Entregado":
                    Imagen_Recycler_Color.setBackgroundColor(Color.parseColor("#5FB238"));
                    break;
                case "Separado":
                    Imagen_Recycler_Color.setBackgroundColor(Color.parseColor("#8B4EEA"));
                    break;
                default:
                    Imagen_Recycler_Color.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
            }
        }
    }

    public Pedido_Adaptador (List<Pedido_Modelo> Pedidos, Context context) {
        this.Pedidos = Pedidos;
        Contexto = context;
    }

    @Override
    public Pedido_Adaptador.PedidoViewHolder onCreateViewHolder (ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pedidos, parent, false);

        return new Pedido_Adaptador.PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull Pedido_Adaptador.PedidoViewHolder holder, int position) {

        holder.bindData(Pedidos.get(position), Contexto);

    }

    @Override
    public int getItemCount() {
        return Pedidos.size();
    }

}

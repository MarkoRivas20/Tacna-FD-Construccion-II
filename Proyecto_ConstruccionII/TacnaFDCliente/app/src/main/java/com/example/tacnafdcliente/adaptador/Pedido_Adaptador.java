package com.example.tacnafdcliente.adaptador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;

import java.util.List;

public class Pedido_Adaptador extends RecyclerView.Adapter <Pedido_Adaptador.PedidoViewHolder> implements View.OnClickListener{

    private List<Pedido_Modelo> Pedidos;
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

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {

        public TextView TxtNombre_Recycler_Pedido;
        public TextView TxtDescripcion_Recycler_Pedido;
        public TextView TxtFecha_Recycler_Pedido;
        public TextView TxtPrecio_Recycler_Pedido;
        public TextView TxtDireccion_Recycler_Pedido;
        public TextView TxtMetodo_Pago_Recycler_Pedido;
        public ImageView ImgEstado;

        public PedidoViewHolder (View v) {
            super(v);

            TxtNombre_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtNombre_Recycler_Pedido);
            TxtDescripcion_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtDescripcion_Recycler_Pedido);
            TxtFecha_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtFecha_Recycler_Pedido);
            TxtPrecio_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtPrecio_Recycler_Pedido);
            TxtDireccion_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtDireccion_Recycler_Pedido);
            TxtMetodo_Pago_Recycler_Pedido = (TextView) v.findViewById(R.id.TxtMetodo_Pago_Recycler_Pedido);
            ImgEstado = (ImageView) v.findViewById(R.id.ImgEstado);
        }

        public void bindData (Pedido_Modelo Pedido, Context Contexto) {

            TxtNombre_Recycler_Pedido.setText(Pedido.getNombre_Establecimiento());
            TxtDescripcion_Recycler_Pedido.setText(Pedido.getDescripcion());
            TxtFecha_Recycler_Pedido.setText(Pedido.getFecha());
            TxtDireccion_Recycler_Pedido.setText("Direccion: " + Pedido.getDireccion_Destino());
            if(Pedido.getMetodo_Pago() == null)
            {
                TxtMetodo_Pago_Recycler_Pedido.setVisibility(View.GONE);
            }
            else
            {
                TxtMetodo_Pago_Recycler_Pedido.setVisibility(View.VISIBLE);
                TxtMetodo_Pago_Recycler_Pedido.setText("Metodo de pago: " + Pedido.getMetodo_Pago());
            }

            TxtPrecio_Recycler_Pedido.setText("S/. " + Pedido.getPrecio_Total());
            switch (Pedido.getEstado())
            {
                case "Pendiente":
                    ImgEstado.setBackground(new ColorDrawable(Color.parseColor("#FFC900")));
                    break;
                case "En Camino":
                    ImgEstado.setBackground(new ColorDrawable(Color.parseColor("#00BDFF")));
                    break;
                case "Entregado":
                    ImgEstado.setBackground(new ColorDrawable(Color.parseColor("#1BDF00")));
                    break;
                case "Separado":
                    ImgEstado.setBackground(new ColorDrawable(Color.parseColor("#5500FF")));
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pedido, parent, false);
        v.setOnClickListener(this);
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

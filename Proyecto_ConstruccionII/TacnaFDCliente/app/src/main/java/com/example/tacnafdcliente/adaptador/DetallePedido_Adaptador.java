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
import com.example.tacnafdcliente.modelo.DetallePedido_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetallePedido_Adaptador extends RecyclerView.Adapter<DetallePedido_Adaptador.DetallePedidoViewHolder>{

    private List<DetallePedido_Modelo> Items;
    private Context Contexto;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class DetallePedidoViewHolder extends RecyclerView.ViewHolder{
        // Campos respectivos de un item
        public ImageView Imagen_Item;
        public ImageView Imagen_Borrar;
        public TextView Txtnombre_Item;
        public TextView Txtcantidad;
        public TextView Txtprecio_Unitario;
        public TextView Txtprecio_Total;

        public DetallePedidoViewHolder (View v, final OnItemClickListener listener) {
            super(v);
            Imagen_Item = (ImageView) v.findViewById(R.id.Imagen_Recycler_Pedido);
            Imagen_Borrar = (ImageView) v.findViewById(R.id.imgborrar_Recycler_Pedido);
            Txtnombre_Item = (TextView)v.findViewById(R.id.txtnombre_Recycler_Pedido);
            Txtcantidad = (TextView)v.findViewById(R.id.txtcantidad_recycler_Pedido);
            Txtprecio_Unitario = (TextView)v.findViewById(R.id.txtpreciounitario_recycler_Pedido);
            Txtprecio_Total = (TextView)v.findViewById(R.id.txtpreciototal_Recycler_Pedido);

            Imagen_Borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bindData (DetallePedido_Modelo dataModel, Context context) {
            Picasso.get().load(dataModel.getUrl_Imagen()).into(Imagen_Item);
            Imagen_Borrar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete));
            Txtnombre_Item.setText(dataModel.getNombre_Item_Menu());
            Txtcantidad.setText("Cantidad: " + dataModel.getCantidad());
            Txtprecio_Unitario.setText("Precio Unitario: S/."+dataModel.getPrecio_Unitario());
            Txtprecio_Total.setText("Precio Total: S/."+dataModel.getPrecio_Total());
        }



    }
    public DetallePedido_Adaptador (List<DetallePedido_Modelo> items, Context context) {
        this.Items = items;
        Contexto = context;
    }


    @Override
    public DetallePedidoViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detalle_pedido, parent, false);
        DetallePedidoViewHolder detallePedidoViewHolder = new DetallePedidoViewHolder(v,mListener);
        return detallePedidoViewHolder;
    }

    @Override
    public void onBindViewHolder (@NonNull DetallePedido_Adaptador.DetallePedidoViewHolder holder, int position) {
        holder.bindData(Items.get(position),Contexto);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

}

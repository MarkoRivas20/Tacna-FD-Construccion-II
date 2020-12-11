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
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemMenuInicio_Adaptador extends RecyclerView.Adapter<ItemMenuInicio_Adaptador.ItemMenuInicioViewHolder> implements View.OnClickListener{

    private List<ItemMenu_Modelo> Items_Menu;

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

    public class ItemMenuInicioViewHolder extends RecyclerView.ViewHolder {

        public ImageView Imagen_Recycler_Item_Menu_Inicio;
        public TextView TxtNombre_Recycler_Item_Menu_Inicio;
        public TextView TxtPrecio_Recycler_Item_Menu_Inicio;

        public ItemMenuInicioViewHolder (View v) {
            super(v);
            Imagen_Recycler_Item_Menu_Inicio = (ImageView) v.findViewById(R.id.Imagen_Recycler_Item_Menu_Inicio);
            TxtNombre_Recycler_Item_Menu_Inicio = (TextView) v.findViewById(R.id.TxtNombre_Recycler_Item_Menu_Inicio);
            TxtPrecio_Recycler_Item_Menu_Inicio = (TextView) v.findViewById(R.id.TxtPrecio_Recycler_Item_Menu_Inicio);
        }

        public void bindData (ItemMenu_Modelo Item_Menu, Context Contexto) {
            Picasso.get().load(Item_Menu.getUrl_Imagen()).into(Imagen_Recycler_Item_Menu_Inicio);
            TxtNombre_Recycler_Item_Menu_Inicio.setText(Item_Menu.getNombre());
            TxtPrecio_Recycler_Item_Menu_Inicio.setText("S/. " + Item_Menu.getPrecio());
        }
    }


    public ItemMenuInicio_Adaptador(List<ItemMenu_Modelo> Items_Menu, Context context) {
        this.Items_Menu = Items_Menu;
        Contexto = context;
    }

    @Override
    public ItemMenuInicio_Adaptador.ItemMenuInicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_menu_inicio, parent, false);
        v.setOnClickListener(this);
        return new ItemMenuInicio_Adaptador.ItemMenuInicioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMenuInicio_Adaptador.ItemMenuInicioViewHolder holder, int position) {
        holder.bindData(Items_Menu.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Items_Menu.size();
    }

}
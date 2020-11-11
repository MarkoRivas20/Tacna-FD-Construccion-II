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
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemMenu_Adaptador extends RecyclerView.Adapter<ItemMenu_Adaptador.ItemMenuViewHolder>{

    private List<ItemMenu_Modelo> Items_Menu;

    private Context Contexto;

    public class ItemMenuViewHolder extends RecyclerView.ViewHolder {

        public ImageView Imagen_Recycler_Item_Menu;
        public TextView TxtNombre_Recycler_Item_Menu;
        public TextView TxtPrecio_Recycler_Item_Menu;

        public ItemMenuViewHolder (View v) {
            super(v);
            Imagen_Recycler_Item_Menu = (ImageView) v.findViewById(R.id.Imagen_Recycler_Item_Menu);
            TxtNombre_Recycler_Item_Menu = (TextView) v.findViewById(R.id.TxtNombre_Recycler_Item_Menu);
            TxtPrecio_Recycler_Item_Menu = (TextView) v.findViewById(R.id.TxtPrecio_Recycler_Item_Menu);
        }

        public void bindData (ItemMenu_Modelo Item_Menu, Context Contexto) {
            Picasso.get().load(Item_Menu.getUrl_Imagen()).into(Imagen_Recycler_Item_Menu);
            TxtNombre_Recycler_Item_Menu.setText(Item_Menu.getNombre());
            TxtPrecio_Recycler_Item_Menu.setText("S/. " + Item_Menu.getPrecio());
        }
    }


    public ItemMenu_Adaptador(List<ItemMenu_Modelo> Items_Menu, Context context) {
        this.Items_Menu = Items_Menu;
        Contexto = context;
    }

    @Override
    public ItemMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_menu, parent, false);
        return new ItemMenu_Adaptador.ItemMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMenu_Adaptador.ItemMenuViewHolder holder, int position) {
        holder.bindData(Items_Menu.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Items_Menu.size();
    }

}

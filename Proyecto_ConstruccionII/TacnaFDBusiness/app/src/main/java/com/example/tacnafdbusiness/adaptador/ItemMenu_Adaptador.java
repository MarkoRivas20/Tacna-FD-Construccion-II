package com.example.tacnafdbusiness.adaptador;

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

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemMenu_Adaptador extends RecyclerView.Adapter<ItemMenu_Adaptador.ItemMenuViewHolder>{

    private List<ItemMenu_Modelo> Items;

    private Context Contexto;

    private OnItemClickListener Listener;

    public class ItemMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public ImageView Imagen_Recycler_Item_Menu;
        public TextView TxtNombre_Recycler_Item_Menu;
        public TextView TxtPrecio_Recycler_Item_Menu;

        public ItemMenuViewHolder (View v) {
            super(v);
            Imagen_Recycler_Item_Menu = (ImageView) v.findViewById(R.id.Imagen_Recycler_Item_Menu);
            TxtNombre_Recycler_Item_Menu = (TextView) v.findViewById(R.id.TxtNombre_Recycler_Item_Menu);
            TxtPrecio_Recycler_Item_Menu = (TextView) v.findViewById(R.id.TxtPrecio_Recycler_Item_Menu);
            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);
        }

        public void bindData (ItemMenu_Modelo dataModel, Context context) {
            Picasso.with(context).load(dataModel.getUrl_Imagen()).into(Imagen_Recycler_Item_Menu);
            TxtNombre_Recycler_Item_Menu.setText(dataModel.getNombre());
            TxtPrecio_Recycler_Item_Menu.setText("S/. " + dataModel.getPrecio());
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (Listener != null)
            {
                int position=getAdapterPosition();

                if (position != RecyclerView.NO_POSITION)
                {

                    switch (item.getItemId())
                    {
                        case 1:

                            Listener.onUpdate(position);
                            return true;

                        case 2:

                            Listener.onDelete(position);
                            return true;

                        case 3:

                            Listener.onCancel(position);
                            return true;

                    }

                }
            }

            return false;
        }

        @Override
        public void onClick(View v) {

            if (Listener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    Listener.onItemClick(position);
                }
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("¿Que desea hacer?");
            MenuItem actualizar = menu.add(Menu.NONE,1,1,"Modificar");
            MenuItem eliminar = menu.add(Menu.NONE,2,2,"Eliminar");
            MenuItem cancelar = menu.add(Menu.NONE,3,3,"Cancelar");

            actualizar.setOnMenuItemClickListener(this);
            eliminar.setOnMenuItemClickListener(this);
            cancelar.setOnMenuItemClickListener(this);
        }
    }


    public ItemMenu_Adaptador(List<ItemMenu_Modelo> items, Context context) {
        this.Items = items;
        Contexto = context;
    }


    @Override
    public ItemMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_menu, parent, false);
        return new ItemMenu_Adaptador.ItemMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMenu_Adaptador.ItemMenuViewHolder holder, int position) {
        holder.bindData(Items.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onUpdate(int position);
        void onDelete(int position);
        void onCancel(int position);

    }

    public void setOnItemClickListener (ItemMenu_Adaptador.OnItemClickListener listener){

        this.Listener=listener;

    }
}

package com.example.tacnafdbusiness.adaptador;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Imagen_Adaptador extends RecyclerView.Adapter<Imagen_Adaptador.ImagenViewHolder> {

    private List<ImagenEstablecimiento_Modelo> Items;

    private Context Contexto;

    private OnItemClickListener Listener;

    public class ImagenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public ImageView Imagen;

        public ImagenViewHolder (View v) {
            super(v);
            Imagen = (ImageView) v.findViewById(R.id.Imagen_Recycler_CRUD);
            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);
        }

        public void bindData (ImagenEstablecimiento_Modelo dataModel, Context context) {
            Picasso.with(context).load(dataModel.getUrl_Imagen()).into(Imagen);
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

                            Listener.onDelete(position);
                            return true;

                        case 2:

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
            menu.setHeaderTitle("¿Desea Eliminarla?");
            MenuItem eliminar = menu.add(Menu.NONE,1,1,"Si");
            MenuItem noeliminar = menu.add(Menu.NONE,2,2,"No");

            eliminar.setOnMenuItemClickListener(this);
            noeliminar.setOnMenuItemClickListener(this);
        }
    }

    public Imagen_Adaptador (List<ImagenEstablecimiento_Modelo> items, Context context) {
        this.Items = items;
        Contexto = context;
    }


    @Override
    public ImagenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_imagenes, parent, false);
        return new ImagenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Imagen_Adaptador.ImagenViewHolder holder, int position) {
        holder.bindData(Items.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        void onDelete(int position);
        void onCancel(int position);

    }

    public void setOnItemClickListener (OnItemClickListener listener){

        this.Listener=listener;

    }
}

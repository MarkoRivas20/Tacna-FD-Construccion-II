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
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Cupon_Adaptador extends RecyclerView.Adapter<Cupon_Adaptador.CuponViewHolder>{

    private List<Cupon_Modelo> Items;

    private Context Contexto;

    private OnItemClickListener Listener;

    public class CuponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public ImageView Imagen;
        public TextView TxtTitulo;

        public CuponViewHolder (View v) {
            super(v);
            Imagen = (ImageView) v.findViewById(R.id.Imagen_Recycler_Cupon);
            TxtTitulo = (TextView) v.findViewById(R.id.Txttitulo_Recycler_Cupon);
            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);
        }

        public void bindData (Cupon_Modelo dataModel, Context context) {
            Picasso.with(context).load(dataModel.getUrl_Imagen()).into(Imagen);
            TxtTitulo.setText(dataModel.getTitulo());
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
            MenuItem modificar = menu.add(Menu.NONE,1,1,"Modificar");
            MenuItem cancelar = menu.add(Menu.NONE,2,2,"Cancelar");

            modificar.setOnMenuItemClickListener(this);
            cancelar.setOnMenuItemClickListener(this);
        }
    }


    public Cupon_Adaptador(List<Cupon_Modelo> items, Context context) {
        this.Items = items;
        Contexto = context;
    }


    @Override
    public CuponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cupones, parent, false);
        return new Cupon_Adaptador.CuponViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Cupon_Adaptador.CuponViewHolder holder, int position) {
        holder.bindData(Items.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onUpdate(int position);
        void onCancel(int position);

    }

    public void setOnItemClickListener (Cupon_Adaptador.OnItemClickListener listener){

        this.Listener=listener;

    }

}

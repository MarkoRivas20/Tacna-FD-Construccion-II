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
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Repartidor_Adaptador extends RecyclerView.Adapter<Repartidor_Adaptador.RepartidorViewHolder>{

    private List<Repartidor_Modelo> Items;

    private Context Contexto;

    private OnItemClickListener Listener;

    public class RepartidorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public ImageView Imagen;
        public TextView TxtNombre;

        public RepartidorViewHolder (View v) {
            super(v);
            Imagen = (ImageView) v.findViewById(R.id.Imagen_Recycler_Repartidores);
            TxtNombre = (TextView) v.findViewById(R.id.Txtnombre_Recycler_Repartidores);
            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);
        }

        public void bindData (Repartidor_Modelo dataModel, Context context) {
            Picasso.with(context).load(dataModel.getUrl_Foto()).into(Imagen);
            TxtNombre.setText(dataModel.getNombre() + " " + dataModel.getApellido());
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

                            Listener.onTakeOut(position);
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
            menu.setHeaderTitle("¿Desea Quitarlo?");
            MenuItem quitar = menu.add(Menu.NONE,1,1,"Si");
            MenuItem cancelar = menu.add(Menu.NONE,2,2,"No");

            quitar.setOnMenuItemClickListener(this);
            cancelar.setOnMenuItemClickListener(this);
        }
    }


    public Repartidor_Adaptador(List<Repartidor_Modelo> items, Context context) {
        this.Items = items;
        Contexto = context;
    }


    @Override
    public RepartidorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_repartidores, parent, false);
        return new Repartidor_Adaptador.RepartidorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Repartidor_Adaptador.RepartidorViewHolder holder, int position) {
        holder.bindData(Items.get(position), Contexto);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onTakeOut(int position);
        void onCancel(int position);

    }

    public void setOnItemClickListener (Repartidor_Adaptador.OnItemClickListener listener){

        this.Listener=listener;

    }

}

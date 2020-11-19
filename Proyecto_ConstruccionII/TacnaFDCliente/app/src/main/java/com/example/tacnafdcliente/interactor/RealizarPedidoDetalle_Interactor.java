package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.RealizarPedidoDetalle;
import com.example.tacnafdcliente.modelo.DetallePedido_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RealizarPedidoDetalle_Interactor implements RealizarPedidoDetalle.Interactor {

    private RealizarPedidoDetalle.onOperationListener mListener;

    private ArrayList<ItemMenu_Modelo> Items_Menu = new ArrayList<>();

    ArrayList<String> Nombres_Items_Menu = new ArrayList<String>();

    public RealizarPedidoDetalle_Interactor(RealizarPedidoDetalle.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetItemsMenuForEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento) {
        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Items_Menu.clear();
                Nombres_Items_Menu.clear();

                for(DataSnapshot postSnapShot : snapshot.getChildren())
                {
                    ItemMenu_Modelo Item_Menu = postSnapShot.getValue(ItemMenu_Modelo.class);
                    Items_Menu.add(Item_Menu);
                    Nombres_Items_Menu.add(Item_Menu.getNombre() + " - S/." + Item_Menu.getPrecio());
                }
                mListener.onSuccessGetItemsMenuForEstablishment(Items_Menu,Nombres_Items_Menu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetItemsMenuForEstablishment();
            }
        });
    }

    @Override
    public void performGetOrderDescription(List<DetallePedido_Modelo> Items_Detalle_Pedido) {

        String Descripcion_Pedido = "";

        if(Items_Detalle_Pedido.size() != 0)
        {
            for(int i = 0; i<Items_Detalle_Pedido.size(); i++)
            {
                Descripcion_Pedido = Descripcion_Pedido + Items_Detalle_Pedido.get(i).getCantidad() + "x " + Items_Detalle_Pedido.get(i).getNombre_Item_Menu() + ", ";
            }
            Descripcion_Pedido = Descripcion_Pedido.substring(0,Descripcion_Pedido.length()-2);

            mListener.onSuccessGetOrderDescription(Descripcion_Pedido);
        }
        else
        {
            mListener.onFailureGetOrderDescription();
        }

    }

    @Override
    public void performGetEstablishmentInfo(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(ID_Establecimiento);
        }
    }
}

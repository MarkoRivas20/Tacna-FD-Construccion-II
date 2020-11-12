package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarPedido;
import com.example.tacnafdbusiness.modelo.Cliente_Modelo;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarPedido_Interactor implements ListarPedido.Interactor {

    private ListarPedido.onOperationListener mListener;

    private ArrayList<Pedido_Modelo> Pedidos = new ArrayList<>();

    private ValueEventListener valueEventListener;

    public ListarPedido_Interactor(ListarPedido.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Obteniendo los Pedidos registradas en un establecimiento*/
    @Override
    public void performGetReviews(DatabaseReference Database_Reference, String ID_Establecimiento) {
        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Pedido = false;
                Pedidos.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Pedido = true;
                    Pedido_Modelo Pedido = postSnapshot.getValue(Pedido_Modelo.class);
                    Pedidos.add(Pedido);
                }
                mListener.onSuccessGetReviews(Pedidos, Existe_Pedido);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetReviews();
            }
        });
    }

    /*Buscando el nombre del cliente por el id_Usuario_Cliente*/
    @Override
    public void performSearchClientName(DatabaseReference Database_Reference, final ArrayList<Pedido_Modelo> Pedidos) {

        /*Recorriendo todas los Pedidos registradas al establecimiento*/
        for(int i = 0; i < Pedidos.size(); i++)
        {
            final int Posicion = i;

            final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(Pedidos.get(i).getID_Usuario_Cliente());

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Cliente_Modelo Cliente = postSnapshot.getValue(Cliente_Modelo.class);
                        Pedidos.get(Posicion).setNombre_Cliente(Cliente.getNombre() + " " + Cliente.getApellido());
                    }
                    mListener.onSuccessSearchClientName(Pedidos);
                    query.removeEventListener(valueEventListener); //Removiendo el evento de escucha
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchClientName();
                }
            };

            query.addListenerForSingleValueEvent(valueEventListener);

        }
    }
    /*Obteniendo el ID del establecimiento del SharedPreferences*/
    @Override
    public void performGetEstablishmentInfo(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(Id_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento);
        }
    }
}

package com.example.tacnafddelivery.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafddelivery.interfaces.ListarPedido;
import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarPedido_Interactor implements ListarPedido.Interactor {

    private ListarPedido.onOperationListener mListener;

    private ArrayList<Pedido_Modelo> Pedidos = new ArrayList<>();

    private ValueEventListener valueEventListener_Search_Client_Name;
    private ValueEventListener valueEventListener_Get_Establishment_Info;

    public ListarPedido_Interactor(ListarPedido.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetOrders(DatabaseReference Database_Reference, String ID_Establecimiento) {

        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Pedido = false;
                Pedidos.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Pedido_Modelo Pedido = postSnapshot.getValue(Pedido_Modelo.class);

                    if(Pedido.getEstado().equals("Pendiente"))
                    {
                        Existe_Pedido = true;
                        Pedidos.add(Pedido);
                    }

                }
                mListener.onSuccessGetOrders(Pedidos, Existe_Pedido);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetOrders();
            }
        });

    }

    @Override
    public void performSearchClientName(DatabaseReference Database_Reference, final ArrayList<Pedido_Modelo> Pedidos) {

        for(int i = 0; i < Pedidos.size(); i++)
        {
            final int Posicion = i;

            final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(Pedidos.get(i).getID_Usuario_Cliente());

            valueEventListener_Search_Client_Name = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Cliente_Modelo Cliente = postSnapshot.getValue(Cliente_Modelo.class);
                        Pedidos.get(Posicion).setNombre_Cliente(Cliente.getNombre() + " " + Cliente.getApellido());
                    }
                    mListener.onSuccessSearchClientName(Pedidos);
                    query.removeEventListener(valueEventListener_Search_Client_Name);//Removiendo el evento de escucha
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchClientName();
                }
            };

            query.addListenerForSingleValueEvent(valueEventListener_Search_Client_Name);

        }


    }

    @Override
    public void performGetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento) {

        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener_Get_Establishment_Info = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetEstablishmentInfo(Establecimiento);
                }
                query.removeEventListener(valueEventListener_Get_Establishment_Info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentInfo();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_Get_Establishment_Info);
    }

    @Override
    public void performGetIDEstablishment(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetIDEstablishment(ID_Establecimiento);
        }
    }

    @Override
    public void performSaveIDOrder(Context Contexto, String ID_Pedido) {
        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_pedido", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_pedido", ID_Pedido);
        editor.apply();
    }
}
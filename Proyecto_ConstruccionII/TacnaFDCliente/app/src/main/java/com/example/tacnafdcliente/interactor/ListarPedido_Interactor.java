package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.ListarPedido;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
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

    @Override
    public void performGetOrders(DatabaseReference Database_Reference, String ID_Usuario) {

        Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(ID_Usuario);

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
                mListener.onSuccessGetOrders(Pedidos, Existe_Pedido);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetOrders();
            }
        });

    }

    @Override
    public void performSearchEstablishmentName(DatabaseReference Database_Reference, final ArrayList<Pedido_Modelo> Pedidos) {

        for(int i = 0; i < Pedidos.size(); i++)
        {
            final int Posicion = i;

            final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(Pedidos.get(i).getID_Establecimiento());

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                        Pedidos.get(Posicion).setNombre_Establecimiento(Establecimiento.getNombre());
                    }
                    mListener.onSuccessSearchEstablishmentName(Pedidos);
                    query.removeEventListener(valueEventListener);//Removiendo el evento de escucha
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchEstablishmentName();
                }
            };

            query.addListenerForSingleValueEvent(valueEventListener);

        }


    }

    @Override
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Id_Usuario = sharedPref.getString("id_usuario","");

        if(Id_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(Id_Usuario);
        }
    }

    @Override
    public void performSaveIDOrderAndIDEstablishment(Context Contexto, String ID_Pedido, String ID_Establecimiento) {
        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_seguimiento_pedido", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_pedido", ID_Pedido);
        editor.putString("id_establecimiento", ID_Establecimiento);
        editor.apply();
    }
}

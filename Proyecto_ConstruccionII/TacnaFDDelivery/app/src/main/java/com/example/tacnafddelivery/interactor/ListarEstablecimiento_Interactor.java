package com.example.tacnafddelivery.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafddelivery.interfaces.ListarEstablecimiento;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.RepartidorEstablecimiento_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarEstablecimiento_Interactor implements ListarEstablecimiento.Interactor {

    private ListarEstablecimiento.onOperationListener mListener;
    private ValueEventListener valueEventListener;

    private ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimientos = new ArrayList<>();
    private ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();

    public ListarEstablecimiento_Interactor(ListarEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetEstablishments(DatabaseReference Database_Reference, String ID_Usuario) {

        Query query = Database_Reference.orderByChild("id_Usuario_Repartidor").equalTo(ID_Usuario);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Repartidores_Establecimientos.clear();

                for(DataSnapshot postSnapShot : snapshot.getChildren())
                {
                    RepartidorEstablecimiento_Modelo Repartidor_Establecimiento = postSnapShot.getValue(RepartidorEstablecimiento_Modelo.class);
                    Repartidores_Establecimientos.add(Repartidor_Establecimiento);
                }
                mListener.onSuccessGetEstablishments(Repartidores_Establecimientos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishments();
            }
        });

    }

    @Override
    public void performGetEstablishmentInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento) {

        for(int i = 0; i < Repartidores_Establecimiento.size(); i++)
        {
            final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(Repartidores_Establecimiento.get(i).getID_Establecimiento());

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    for(DataSnapshot postSnapShot : snapshot.getChildren())
                    {
                        Establecimiento_Modelo Establecimiento = postSnapShot.getValue(Establecimiento_Modelo.class);
                        Establecimientos.add(Establecimiento);
                    }
                    mListener.onSuccessGetEstablishmentInfo(Establecimientos);
                    query.removeEventListener(valueEventListener);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureGetEstablishmentInfo();
                }
            };

            query.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    @Override
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0){
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
    }

    @Override
    public void performSaveIDEstablishment(Context Contexto, String ID_Establecimiento) {
        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_establecimiento", ID_Establecimiento);
        editor.apply();
    }
}

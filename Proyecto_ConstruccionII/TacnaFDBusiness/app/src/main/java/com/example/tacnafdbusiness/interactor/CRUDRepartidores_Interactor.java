package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;


import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.CRUDRepartidores;
import com.example.tacnafdbusiness.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CRUDRepartidores_Interactor implements CRUDRepartidores.Interactor {

    private CRUDRepartidores.onOperationListener mListener;

    Repartidor_Modelo Repartidor = new Repartidor_Modelo();

    private ArrayList<Repartidor_Modelo> Repartidores = new ArrayList<>();
    private ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento = new ArrayList<>();

    private ValueEventListener valueEventListener;

    public CRUDRepartidores_Interactor(CRUDRepartidores.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Registrando un repartidor al establecimiento*/

    @Override
    public void performSaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo Repartidor_Establecimiento) {

        Database_Reference.child(Repartidor_Establecimiento.getID_Repartidor_Establecimiento()).setValue(Repartidor_Establecimiento).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    mListener.onSuccessSaveDeliveryMan();

                }
                else
                {
                    mListener.onFailureSaveDeliveryMan();
                }
            }
        });
    }

    /*Buscando un repartidor por su correo electronico*/

    @Override
    public void performSearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor) {

        Query query = Database_Reference.orderByChild("email").equalTo(Correo_Electronico_Repartidor);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean Existe_Repartidor = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Repartidor = true;
                    Repartidor = postSnapshot.getValue(Repartidor_Modelo.class);
                }

                mListener.onSuccessSearchDeliveryMan(Repartidor, Existe_Repartidor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureSearchDeliveryMan();
            }
        });
    }

    /*Obteneniendo la información de los repartidores registrados en el establecimiento*/

    @Override
    public void performSearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento) {
        Repartidores.clear();
        for(int i = 0; i < Repartidores_Establecimiento.size(); i++){
            Query query = Database_Reference.orderByChild("id_Usuario_Repartidor").equalTo(Repartidores_Establecimiento.get(i).getID_Usuario_Repartidor());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Repartidor_Modelo Repartidor = postSnapshot.getValue(Repartidor_Modelo.class);
                        Repartidores.add(Repartidor);

                    }
                    mListener.onSuccessSearchDeliveryManInfo(Repartidores);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchDeliveryManInfo();
                }
            });
        }


    }

    /*Eliminado al repartidor del establecimiento*/

    @Override
    public void performTakeOutDeliveryMan(final DatabaseReference Database_Reference, String ID_Repartidor_Establecimiento, final String ID_Establecimiento) {

        final Query query = Database_Reference.orderByChild("id_Usuario_Repartidor").equalTo(ID_Repartidor_Establecimiento);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    RepartidorEstablecimiento_Modelo Repartidor_Establecimiento = postSnapshot.getValue(RepartidorEstablecimiento_Modelo.class);

                    if(Repartidor_Establecimiento.getID_Establecimiento().equals(ID_Establecimiento))
                    {
                        Database_Reference.child(Repartidor_Establecimiento.getID_Repartidor_Establecimiento()).removeValue().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mListener.onFailureTakeOutDeliveryMan();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mListener.onSuccessTakeOutDeliveryMan();
                            }
                        });
                        query.removeEventListener(valueEventListener);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onSuccessTakeOutDeliveryMan();
            }
        };
        query.addValueEventListener(valueEventListener);


    }

    /*Buscando los repartidores que estan registrados en el establecimiento*/

    @Override
    public void performListDeliveryMen(DatabaseReference Database_Reference, String ID_Establecimiento) {


        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Boolean Existe_Repartidor_Establecimiento = false;
                Repartidores_Establecimiento.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Repartidor_Establecimiento = true;
                    RepartidorEstablecimiento_Modelo repartidorEstablecimiento_modelo = postSnapshot.getValue(RepartidorEstablecimiento_Modelo.class);
                    Repartidores_Establecimiento.add(repartidorEstablecimiento_modelo);

                }

                mListener.onSuccessListDeliveryMen(Repartidores_Establecimiento, Existe_Repartidor_Establecimiento);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureListDeliveryMen();
            }
        });
    }

    /*Obteniendo el ID del establecimiento del SharedPreferences*/

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

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

    Repartidor_Modelo repartidor_modelo = new Repartidor_Modelo();

    private ArrayList<Repartidor_Modelo> repartidor_modelos=new ArrayList<>();

    private ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos=new ArrayList<>();

    private ValueEventListener valueEventListener;

    public CRUDRepartidores_Interactor(CRUDRepartidores.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performSaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo repartidorEstablecimiento_modelo) {

        Database_Reference.child(repartidorEstablecimiento_modelo.getID_Repartidor_Establecimiento()).setValue(repartidorEstablecimiento_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    mListener.onSuccessSaveDeliveryMan();

                }
                else
                {
                    mListener.onFailureSaveDeliveryMan();
                }
            }
        });
    }

    @Override
    public void performSearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor) {

        Query query = Database_Reference.orderByChild("email").equalTo(Correo_Electronico_Repartidor);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean Existe_Repartidor = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Existe_Repartidor = true;
                    repartidor_modelo = postSnapshot.getValue(Repartidor_Modelo.class);
                }

                mListener.onSuccessSearchDeliveryMan(repartidor_modelo, Existe_Repartidor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureSearchDeliveryMan();
            }
        });
    }

    @Override
    public void performSearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos) {
        repartidor_modelos.clear();
        for(int i = 0; i < repartidorEstablecimiento_modelos.size(); i++){
            Query query = Database_Reference.orderByChild("id_Usuario_Repartidor").equalTo(repartidorEstablecimiento_modelos.get(i).getID_Usuario_Repartidor());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren()){

                        Repartidor_Modelo repartidor_modelo2 = postSnapshot.getValue(Repartidor_Modelo.class);
                        repartidor_modelos.add(repartidor_modelo2);

                    }
                    mListener.onSuccessSearchDeliveryManInfo(repartidor_modelos);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchDeliveryManInfo();
                }
            });
        }


    }

    @Override
    public void performTakeOutDeliveryMan(final DatabaseReference Database_Reference, String ID_Repartidor_Establecimiento, final String ID_Establecimiento) {

        final Query query = Database_Reference.orderByChild("id_Usuario_Repartidor").equalTo(ID_Repartidor_Establecimiento);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    RepartidorEstablecimiento_Modelo repartidorEstablecimiento_modelo = postSnapshot.getValue(RepartidorEstablecimiento_Modelo.class);

                    if(repartidorEstablecimiento_modelo.getID_Establecimiento().equals(ID_Establecimiento))
                    {
                        Database_Reference.child(repartidorEstablecimiento_modelo.getID_Repartidor_Establecimiento()).removeValue().addOnFailureListener(new OnFailureListener() {
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

    @Override
    public void performListDeliveryMen(DatabaseReference Database_Reference, String ID_Establecimiento) {


        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Boolean Existe_Repartidor_Establecimiento = false;
                repartidorEstablecimiento_modelos.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){

                    Existe_Repartidor_Establecimiento = true;
                    RepartidorEstablecimiento_Modelo repartidorEstablecimiento_modelo = postSnapshot.getValue(RepartidorEstablecimiento_Modelo.class);
                    repartidorEstablecimiento_modelos.add(repartidorEstablecimiento_modelo);

                }

                mListener.onSuccessListDeliveryMen(repartidorEstablecimiento_modelos, Existe_Repartidor_Establecimiento);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureListDeliveryMen();
            }
        });
    }

    @Override
    public void performGetEstablishmentInfo(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(Id_Establecimiento.length() != 0){
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento);
        }

    }
}

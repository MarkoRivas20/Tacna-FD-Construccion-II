package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.OpcionesEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ImagenEstablecimiento_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OpcionesEstablecimiento_Interactor implements OpcionesEstablecimiento.Interactor {

    private OpcionesEstablecimiento.onOperationListener mListener;

    int Contador_Imagenes = 0;

    String[] Imagenes_Urls;

    public OpcionesEstablecimiento_Interactor(OpcionesEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento) {

        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(Id_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for(DataSnapshot postsnapshot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postsnapshot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetEstablishmentData(Establecimiento);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentData();
            }
        });
    }

    @Override
    public void performGetImagesEstablishment(DatabaseReference Database_Reference, String Id_Establecimiento) {

        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(Id_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Contador_Imagenes = 0;

                for(DataSnapshot postsnapshot : snapshot.getChildren())
                {
                    Contador_Imagenes++;
                }

                Imagenes_Urls = new String[Contador_Imagenes];
                Contador_Imagenes = 0;

                for(DataSnapshot postsnapshot : snapshot.getChildren())
                {
                    ImagenEstablecimiento_Modelo Imagen_Establecimiento = postsnapshot.getValue(ImagenEstablecimiento_Modelo.class);

                    Imagenes_Urls[Contador_Imagenes] = Imagen_Establecimiento.getUrl_Imagen();

                    Contador_Imagenes++;
                }

                mListener.onSuccessGetImagesEstablishment(Imagenes_Urls);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetImagesEstablishment();
            }
        });

    }

    @Override
    public void performGetEstablishmentInfo(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(ID_Establecimiento);
        }
        else
        {
            mListener.onFailureGetEstablishmentInfo();
        }
    }
}

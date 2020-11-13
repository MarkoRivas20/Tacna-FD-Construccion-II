package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.OpcionesEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ImagenEstablecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OpcionesEstablecimiento_Interactor implements OpcionesEstablecimiento.Interactor {

    private OpcionesEstablecimiento.onOperationListener mListener;
    private ValueEventListener valueEventListener;

    int Contador_Imagenes = 0;

    String[] Imagenes_Urls;

    Boolean Existe_Resena = false;

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
    public void performGetUserReview(DatabaseReference Database_Reference, final String ID_Establecimiento, String ID_Usuario) {
        final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(ID_Usuario);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Existe_Resena = false;

                for(DataSnapshot postSnapShot : snapshot.getChildren()){

                    Resena_Modelo Resena = postSnapShot.getValue(Resena_Modelo.class);

                    if(Resena.getID_Establecimiento().equals(ID_Establecimiento)){

                        Existe_Resena = true;
                    }
                }
                query.removeEventListener(valueEventListener);
                mListener.onSuccessGetUserReview(Existe_Resena);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetUserReview();
            }
        };

        query.addValueEventListener(valueEventListener);

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

    @Override
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
    }
}

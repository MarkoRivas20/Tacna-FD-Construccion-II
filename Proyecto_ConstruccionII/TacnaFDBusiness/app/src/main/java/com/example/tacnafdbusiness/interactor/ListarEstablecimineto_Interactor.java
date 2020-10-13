package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarEstablecimineto_Interactor implements ListarEstablecimiento.Interactor {

    private ListarEstablecimiento.onOperationListener mListener;

    private ArrayList<Establecimiento_Modelo> establecimiento_modelos=new ArrayList<>();

    public ListarEstablecimineto_Interactor(ListarEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performSearchEstablishment(DatabaseReference reference, String ID_Usuario) {

        Query query=reference.orderByChild("id_Usuario_Propietario").startAt(ID_Usuario);



        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    booleano = true;
                    Establecimiento_Modelo establecimiento_modelo = postSnapshot.getValue(Establecimiento_Modelo.class);
                    establecimiento_modelos.add(establecimiento_modelo);
                }
                if(booleano){
                    mListener.onSuccess(establecimiento_modelos);
                }else{
                    mListener.onFailure();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailure();
            }
        });

    }

    @Override
    public void performGetSessionData(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String id_Usuario = sharedPref.getString("id_usuario","");

        if(id_Usuario.length() != 0){
            mListener.onSuccessGetSessionData(id_Usuario);
        }
        else{
            mListener.onFailure();
        }

    }
}

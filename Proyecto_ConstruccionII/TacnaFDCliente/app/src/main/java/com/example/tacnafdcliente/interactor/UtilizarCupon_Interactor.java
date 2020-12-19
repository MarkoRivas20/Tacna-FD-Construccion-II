package com.example.tacnafdcliente.interactor;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.UtilizarCupon;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UtilizarCupon_Interactor implements UtilizarCupon.Interactor {

    private UtilizarCupon.onOperationListener mListener;
    private ValueEventListener valueEventListener;
    private ValueEventListener valueEventListener_Get_Establishment;

    public UtilizarCupon_Interactor(UtilizarCupon.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetCouponInfo(DatabaseReference Database_Reference, String ID_Cupon) {

        final Query query = Database_Reference.orderByChild("id_Cupon").equalTo(ID_Cupon);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot postSnapShot : snapshot.getChildren())
                {
                    Cupon_Modelo Cupon = postSnapShot.getValue(Cupon_Modelo.class);
                    mListener.onSuccessGetCouponInfo(Cupon);
                }
                query.removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetCouponInfo();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void performGetEstablishmentByID(DatabaseReference Database_Reference, String ID_Establecimiento) {

        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener_Get_Establishment = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapShot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postSnapShot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetEstablishmentByID(Establecimiento);
                }
                query.removeEventListener(valueEventListener_Get_Establishment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentByID();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_Get_Establishment);
    }

    @Override
    public void performSaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_establecimiento", ID_Establecimiento);
        editor.putString("nombre_establecimiento", Nombre_Establecimiento);
        editor.apply();
    }

    @Override
    public void performSaveCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento) {
        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_cupon", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_cupon", ID_Cupon);
        editor.putString("id_cupon_usuario", ID_Cupon_Usuario);
        editor.putInt("descuento", Descuento);
        editor.apply();
    }
}

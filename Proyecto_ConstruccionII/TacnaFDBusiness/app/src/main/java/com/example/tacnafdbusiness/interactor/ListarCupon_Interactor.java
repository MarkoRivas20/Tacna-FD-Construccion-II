package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarCupon_Interactor implements ListarCupon.Interactor {

    private ListarCupon.onOperationListener mListener;

    private ArrayList<Cupon_Modelo> Cupones = new ArrayList<>();

    public ListarCupon_Interactor(ListarCupon.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Obteniendo los cupones registrados en el establecimiento*/
    @Override
    public void performListCoupon(DatabaseReference Database_Reference, String ID_Establecimiento) {

        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Cupon = false;
                Cupones.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Cupon = true;
                    Cupon_Modelo Cupon = postSnapshot.getValue(Cupon_Modelo.class);
                    Cupones.add(Cupon);
                }

                mListener.onSuccessListCoupon(Cupones, Existe_Cupon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureListCoupon();
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

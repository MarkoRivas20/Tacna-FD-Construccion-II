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

    private ArrayList<Cupon_Modelo> cupon_modelos = new ArrayList<>();

    public ListarCupon_Interactor(ListarCupon.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performListCoupon(DatabaseReference reference, String Id_Establecimiento) {

        Query query = reference.orderByChild("id_Establecimiento").equalTo(Id_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Cupon = false;
                cupon_modelos.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Existe_Cupon = true;
                    Cupon_Modelo cupon_modelo = postSnapshot.getValue(Cupon_Modelo.class);
                    cupon_modelos.add(cupon_modelo);
                }

                mListener.onSuccessListCoupon(cupon_modelos, Existe_Cupon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureListCoupon();
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

package com.example.tacnafdcliente.interactor;


import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.UtilizarCupon;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UtilizarCupon_Interactor implements UtilizarCupon.Interactor {

    private UtilizarCupon.onOperationListener mListener;
    private ValueEventListener valueEventListener;

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
}

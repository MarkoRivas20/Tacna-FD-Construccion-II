package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface UtilizarCupon {

    interface View{
        void onGetCouponInfoSuccessful(Cupon_Modelo Cupon);
        void onGetCouponInfoFailure();
    }

    interface Presenter{
        void GetCouponInfo(DatabaseReference Database_Reference, String ID_Cupon);
    }

    interface Interactor{
        void performGetCouponInfo(DatabaseReference Database_Reference, String ID_Cupon);
    }

    interface onOperationListener{
        void onSuccessGetCouponInfo(Cupon_Modelo Cupon);
        void onFailureGetCouponInfo();
    }
}

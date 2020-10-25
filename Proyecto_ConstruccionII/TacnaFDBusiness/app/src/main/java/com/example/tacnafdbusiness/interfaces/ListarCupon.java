package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarCupon {

    interface View{
        void onListCouponSuccessful(ArrayList<Cupon_Modelo> cupon_modelos, Boolean Existe_Cupon);
        void onListCouponFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
    }

    interface Presenter{
        void ListCoupon(DatabaseReference reference, String Id_Establecimiento);
        void GetEstablishmentInfo(Context context);
    }

    interface Interactor{
        void performListCoupon(DatabaseReference reference, String Id_Establecimiento);
        void performGetEstablishmentInfo(Context context);
    }

    interface onOperationListener{
        void onSuccessListCoupon(ArrayList<Cupon_Modelo> cupon_modelos, Boolean Existe_Cupon);
        void onFailureListCoupon();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
    }
}

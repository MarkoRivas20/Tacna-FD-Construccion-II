package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarCupon {

    interface View{
        void onListCouponSuccessful(ArrayList<Cupon_Modelo> Cupones, Boolean Existe_Cupon);
        void onListCouponFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void ListCoupon(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performListCoupon(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessListCoupon(ArrayList<Cupon_Modelo> Cupones, Boolean Existe_Cupon);
        void onFailureListCoupon();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

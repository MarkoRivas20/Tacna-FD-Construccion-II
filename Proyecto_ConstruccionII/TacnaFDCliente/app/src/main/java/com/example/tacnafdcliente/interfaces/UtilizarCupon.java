package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface UtilizarCupon {

    interface View{
        void onGetCouponInfoSuccessful(Cupon_Modelo Cupon);
        void onGetCouponInfoFailure();
        void onGetEstablishmentByIDSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentByIDFailure();
    }

    interface Presenter{
        void GetCouponInfo(DatabaseReference Database_Reference, String ID_Cupon);
        void GetEstablishmentByID(DatabaseReference Database_Reference, String ID_Establecimiento);
        void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento);
        void SaveCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento);
    }

    interface Interactor{
        void performGetCouponInfo(DatabaseReference Database_Reference, String ID_Cupon);
        void performGetEstablishmentByID(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performSaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento);
        void performSaveCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento);
    }

    interface onOperationListener{
        void onSuccessGetCouponInfo(Cupon_Modelo Cupon);
        void onFailureGetCouponInfo();
        void onSuccessGetEstablishmentByID(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentByID();
    }
}

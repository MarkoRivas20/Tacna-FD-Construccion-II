package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public interface ListarMiCupon {

    interface View{
        void onGetNumberOfCouponsSuccessful(int Numero_Cupones);
        void onGetCouponUserSuccessful(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onGetCouponUserFailure();
        void onSearchEstablishmentNameSuccessful(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onSearchEstablishmentNameFailure();
        void onSearchCouponInfoSuccessful(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onSearchCouponInfoFailure();
        void onGetSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void GetNumberOfCoupons(Context Contexto);
        void GetCouponUser(DatabaseReference Database_Reference, String ID_Usuario);
        void SearchEstablishmentName(DatabaseReference Database_Reference, ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void SearchCouponInfo(DatabaseReference Database_Reference, ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void GetSessionData(Context Contexto);
    }

    interface Interactor{
        void performGetNumberOfCoupons(Context Contexto);
        void performGetCouponUser(DatabaseReference Database_Reference, String ID_Usuario);
        void performSearchEstablishmentName(DatabaseReference Database_Reference, ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void performSearchCouponInfo(DatabaseReference Database_Reference, ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void performGetSessionData(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetNumberOfCoupons(int Numero_Cupones);
        void onSuccessGetCouponUser(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onFailureGetCouponUser();
        void onSuccessSearchEstablishmentName(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onFailureSearchEstablishmentName();
        void onSuccessSearchCouponInfo(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onFailureSearchCouponInfo();
        void onSuccessGetSessionData(String ID_Usuario);
    }

}

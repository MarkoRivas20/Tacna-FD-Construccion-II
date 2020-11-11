package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

public interface OpcionesEstablecimiento {

    interface View{
        void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentDataFailure();
        void onGetImagesEstablishmentSuccessful(String[] Imagene_Urls);
        void onGetImagesEstablishmentFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
        void onGetEstablishmentInfoFailure();
    }

    interface Presenter{
        void GetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento);
        void GetImagesEstablishment(DatabaseReference Database_Reference, String Id_Establecimiento);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performGetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento);
        void performGetImagesEstablishment(DatabaseReference Database_Reference, String Id_Establecimiento);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentData();
        void onSuccessGetImagesEstablishment(String[] Imagene_Urls);
        void onFailureGetImagesEstablishment();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
        void onFailureGetEstablishmentInfo();
    }
}

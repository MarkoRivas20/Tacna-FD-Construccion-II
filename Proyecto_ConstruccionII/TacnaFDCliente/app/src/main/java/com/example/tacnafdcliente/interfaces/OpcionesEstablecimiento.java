package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

public interface OpcionesEstablecimiento {

    interface View{
        void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentDataFailure();
        void onGetImagesEstablishmentSuccessful(String[] Imagene_Urls);
        void onGetImagesEstablishmentFailure();
        void onGetUserReviewSuccessful(Boolean Existe_Resena);
        void onGetUserReviewFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
        void onGetEstablishmentInfoFailure();
        void onGetSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void GetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento);
        void GetImagesEstablishment(DatabaseReference Database_Reference, String Id_Establecimiento);
        void GetUserReview(DatabaseReference Database_Reference, String ID_Establecimiento, String ID_Usuario);
        void GetEstablishmentInfo(Context Contexto);
        void GetSessionData(Context Contexto);
    }

    interface Interactor{
        void performGetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento);
        void performGetImagesEstablishment(DatabaseReference Database_Reference, String Id_Establecimiento);
        void performGetUserReview(DatabaseReference Database_Reference, String ID_Establecimiento, String ID_Usuario);
        void performGetEstablishmentInfo(Context Contexto);
        void performGetSessionData(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentData();
        void onSuccessGetImagesEstablishment(String[] Imagene_Urls);
        void onFailureGetImagesEstablishment();
        void onSuccessGetUserReview(Boolean Existe_Resena);
        void onFailureGetUserReview();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
        void onFailureGetEstablishmentInfo();
        void onSuccessGetSessionData(String ID_Usuario);
    }
}

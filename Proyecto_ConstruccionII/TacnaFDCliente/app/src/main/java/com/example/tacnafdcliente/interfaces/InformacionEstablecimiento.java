package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.DatabaseReference;

public interface InformacionEstablecimiento {

    interface View{
        void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentDataFailure();
        void onGetLatitudeLongitudeSuccessful(Double Latitud, Double Longitud);
        void onGetLatitudeLongitudeFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
        void onGetEstablishmentInfoFailure();
    }

    interface Presenter{
        void GetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento);
        void GetLatitudeLongitude(Context Contexto, FusedLocationProviderClient Fused_Location_Provider_Client);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performGetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento);
        void performGetLatitudeLongitude(Context Contexto, FusedLocationProviderClient Fused_Location_Provider_Client);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentData();
        void onSuccessGetLatitudeLongitude(Double Latitud, Double Longitud);
        void onFailureGetLatitudeLongitude();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
        void onFailureGetEstablishmentInfo();
    }
}

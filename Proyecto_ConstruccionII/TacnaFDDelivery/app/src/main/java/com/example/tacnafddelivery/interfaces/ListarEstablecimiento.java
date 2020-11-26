package com.example.tacnafddelivery.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public interface ListarEstablecimiento {

    interface View{
        void onGetEstablishmentsSuccessful(ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento);
        void onGetEstablishmentsFailure();
        void onGetEstablishmentInfoSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos);
        void onGetEstablishmentInfoFailure();
        void onGetSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void GetEstablishments(DatabaseReference Database_Reference, String ID_Usuario);
        void GetEstablishmentInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento);
        void GetSessionData(Context Contexto);
        void SaveIDEstablishment(Context Contexto, String ID_Establecimiento);
    }

    interface Interactor{
        void performGetEstablishments(DatabaseReference Database_Reference, String ID_Usuario);
        void performGetEstablishmentInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento);
        void performGetSessionData(Context Contexto);
        void performSaveIDEstablishment(Context Contexto, String ID_Establecimiento);
    }

    interface onOperationListener{
        void onSuccessGetEstablishments(ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento);
        void onFailureGetEstablishments();
        void onSuccessGetEstablishmentInfo(ArrayList<Establecimiento_Modelo> Establecimientos);
        void onFailureGetEstablishmentInfo();
        void onSuccessGetSessionData(String ID_Usuario);
    }

}

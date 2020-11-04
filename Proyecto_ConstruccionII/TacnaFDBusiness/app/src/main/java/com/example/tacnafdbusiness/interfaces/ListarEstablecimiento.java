package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarEstablecimiento {

    interface View{
        void onSearchEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onSearchEstablishmentFailure();
        void onSessionDataSuccessful(String ID_Usuario);
        void onFilterSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Buscar_Establecimiento);
    }

    interface Presenter{
        void SearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario);
        void GetSessionData(Context Contexto);
        void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void FilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento);
    }

    interface Interactor{
        void performSearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario);
        void performGetSessionData(Context Contexto);
        void performSaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void performFilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento);
    }

    interface onOperationListener{
        void onSuccess(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onFailure();
        void onSuccessGetSessionData(String ID_Usuario);
        void onSuccessFilter(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Buscar_Establecimiento);
    }
}

package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarEstablecimiento {

    interface View{
        void onSearchEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> establecimiento, Boolean Existe_Establecimiento);
        void onSearchEstablishmentFailure();
        void onSessionDataSuccessful(String ID_Usuario);
        void onFilterSuccessful(ArrayList<Establecimiento_Modelo> establecimientos, Boolean buscar_establecimiento);
    }

    interface Presenter{
        void SearchEstablishment(DatabaseReference reference, String ID_Usuario);
        void GetSessionData(Context context);
        void SaveEstablishmentInfo(Context context, String Id_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void FilterEstablishment(ArrayList<Establecimiento_Modelo> establecimientos, String Nombre_Establecimiento);
    }

    interface Interactor{
        void performSearchEstablishment(DatabaseReference reference, String ID_Usuario);
        void performGetSessionData(Context context);
        void performSaveEstablishmentInfo(Context context, String Id_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void performFilterEstablishment(ArrayList<Establecimiento_Modelo> establecimientos, String Nombre_Establecimiento);
    }

    interface onOperationListener{
        void onSuccess(ArrayList<Establecimiento_Modelo> establecimiento, Boolean Existe_Establecimiento);
        void onFailure();
        void onSuccessGetSessionData(String ID_Usuario);
        void onSuccessFilter(ArrayList<Establecimiento_Modelo> establecimientos, Boolean buscar_establecimiento);
    }
}

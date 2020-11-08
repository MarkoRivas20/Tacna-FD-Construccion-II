package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarEstablecimiento {

    interface View{
        void onGetAllEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onGetAllEstablishmentFailure();
        void onFilterSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Buscar_Establecimiento);
    }

    interface Presenter{
        void GetAllEstablishment(DatabaseReference Database_Reference);
        void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void FilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento, String Categoria_Establecimiento, String Distrito_Establecimiento);
    }

    interface Interactor{
        void performGetAllEstablishment(DatabaseReference Database_Reference);
        void performSaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void performFilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento, String Categoria_Establecimiento, String Distrito_Establecimiento);
    }

    interface onOperationListener{
        void onSuccessGetAllEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onFailureGetAllEstablishment();
        void onSuccessFilter(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Buscar_Establecimiento);
    }
}

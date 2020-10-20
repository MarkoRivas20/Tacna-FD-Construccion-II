package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public interface OpcionesEstablecimiento {

    interface View{
        void onGetEstablishmentInfoSuccessful(String Nombre_Establecimiento, String Url_Logo);
        void onGetEstablishmentInfoFailure();
    }

    interface Presenter{

        void GetEstablishmentInfo(Context context);
    }

    interface Interactor{
        void performGetEstablishmentInfo(Context context);
    }

    interface onOperationListener{
        void onSuccess(String Nombre_Establecimiento, String Url_Logo);
        void onFailure();
    }
}

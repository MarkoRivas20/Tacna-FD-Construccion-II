package com.example.tacnafdbusiness.interfaces;

import android.content.Context;


public interface OpcionesEstablecimiento {

    interface View{
        void onGetEstablishmentInfoSuccessful(String Nombre_Establecimiento, String Url_Logo);
        void onGetEstablishmentInfoFailure();
    }

    interface Presenter{

        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccess(String Nombre_Establecimiento, String Url_Logo);
        void onFailure();
    }
}

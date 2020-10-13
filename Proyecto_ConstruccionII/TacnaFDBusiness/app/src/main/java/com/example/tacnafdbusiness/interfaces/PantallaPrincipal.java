package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

public interface PantallaPrincipal {

    interface View{
        void onSessionDataSuccessful(String correo_electronico, String nombre_usuario);
        void onSessionDataFailure();
        void onCloseSessionSuccessful();
    }

    interface Presenter{
        void GetSessionData(Context context);
        void CloseSession(Context context);
    }

    interface Interactor{
        void performGetSessionData(Context context);
        void performCloseSession(Context context);
    }

    interface onOperationListener{
        void onSuccess(String correo_electronico, String nombre_usuario);
        void onFailure();
        void onSuccessCloseSession();
    }
}

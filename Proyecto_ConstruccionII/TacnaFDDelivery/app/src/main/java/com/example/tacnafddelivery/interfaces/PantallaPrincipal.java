package com.example.tacnafddelivery.interfaces;

import android.content.Context;

public interface PantallaPrincipal {

    interface View{
        void onSessionDataSuccessful(String correo_electronico, String nombre_usuario, String url_foto);
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
        void onSuccess(String correo_electronico, String nombre_usuario, String url_foto);
        void onFailure();
        void onSuccessCloseSession();
    }

}

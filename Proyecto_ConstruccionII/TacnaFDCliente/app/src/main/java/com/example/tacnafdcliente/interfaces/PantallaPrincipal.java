package com.example.tacnafdcliente.interfaces;

import android.content.Context;

public interface PantallaPrincipal {

    interface View{
        void onSessionDataSuccessful(String Correo_Electronico, String Nombre_Usuario);
        void onSessionDataFailure();
        void onCloseSessionSuccessful();
    }

    interface Presenter{
        void GetSessionData(Context Contexto);
        void CloseSession(Context Contexto);
    }

    interface Interactor{
        void performGetSessionData(Context Contexto);
        void performCloseSession(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessSessionData(String Correo_Electronico, String Nombre_Usuario);
        void onFailureSessionData();
        void onSuccessCloseSession();
    }
}

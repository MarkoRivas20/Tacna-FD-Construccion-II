package com.example.tacnafddelivery.interfaces;

import android.content.Context;

public interface PantallaPrincipal {

    interface View{
        void onSessionDataSuccessful(String correo_electronico, String nombre_usuario, String url_foto);
        void onSessionDataFailure();
        void onCloseSessionSuccessful();
        void onCheckTrackingOrderSharedPreferenceSuccessful(String Seguimiento);
    }

    interface Presenter{
        void GetSessionData(Context context);
        void CloseSession(Context context);
        void CheckTrackingOrderSharedPreference(Context Contexto);
    }

    interface Interactor{
        void performGetSessionData(Context context);
        void performCloseSession(Context context);
        void performCheckTrackingOrderSharedPreference(Context Contexto);
    }

    interface onOperationListener{
        void onSuccess(String correo_electronico, String nombre_usuario, String url_foto);
        void onFailure();
        void onSuccessCloseSession();
        void onSuccessCheckTrackingOrderSharedPreference(String Seguimiento);
    }

}

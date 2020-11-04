package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public interface ModificarUsuario {

    interface View{
        void onUpdateUserSuccessful();
        void onUpdateUserFailure();
        void onShowUserDataSuccessful(Usuario_Modelo Usuario);
        void onShowUserDataFailure();
        void onGetSessionDataSuccessful(String Correo_Electronico);
    }

    interface Presenter{
        void UpdateUser(DatabaseReference Database_Reference, Usuario_Modelo Usuario);
        void ShowUserData(DatabaseReference Database_Reference, String Correo_Electronico);
        void GetSessionData(Context Contexto);
    }

    interface Interactor{
        void performUpdateUser(DatabaseReference Database_Reference, Usuario_Modelo Usuario);
        void performShowUserData(DatabaseReference Database_Reference, String Correo_Electronico);
        void performGetSessionData(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessUpdateUser();
        void onFailureUpdateUser();
        void onSuccessShowUserData(Usuario_Modelo Usuario);
        void onFailureShowUserData();
        void onSuccessGetSessionData(String Correo_Electronico);
    }

}

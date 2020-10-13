package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public interface ModificarUsuario {

    interface View{
        void onUpdateUserSuccessful();
        void onUpdateUserFailure();
        void onShowUserDataSuccessful(Usuario_Modelo usuario_modelo);
        void onShowUserDataFailure();
        void onSessionDataSuccessful(String correo_electronico);
    }

    interface Presenter{
        void UpdateUser(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void ShowUserData(DatabaseReference reference, String correo_electronico);
        void GetSessionData(Context context);
    }

    interface Interactor{
        void performUpdateUser(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void performShowUserData(DatabaseReference reference, String correo_electronico);
        void performGetSessionData(Context context);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onSuccessShowUserData(Usuario_Modelo usuario_modelo);
        void onFailureShowUserData();
        void onSuccess(String correo_electronico);
    }
}

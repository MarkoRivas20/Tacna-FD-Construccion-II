package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public interface ModificarUsuario {

    interface View{
        void onUpdateUserDataSuccessful();
        void onUpdateUserDataFailure();
        void onShowUserDataSuccessful(Usuario_Modelo usuario_modelo);
        void onShowUserDataFailure();
        void onSessionDataSuccessful(String correo_electronico);
        void onSaveSessionSuccessful();
    }

    interface Presenter{
        void UpdateUserData(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void ShowUserData(DatabaseReference reference, String correo_electronico);
        void SaveSession(Context context, String nombre_usuario);
        void GetSessionData(Context context);
    }

    interface Interactor{
        void performUpdateUserData(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void performShowUserData(DatabaseReference reference, String correo_electronico);
        void performSaveSession(Context context, String nombre_usuario);
        void performGetSessionData(Context context);
    }

    interface onOperationListener{
        void onSuccessUpdateUserData();
        void onFailureUpdateUserData();
        void onSuccessShowUserData(Usuario_Modelo usuario_modelo);
        void onFailureShowUserData();
        void onSuccessGetSessionData(String correo_electronico);
        void onSuccessSaveSession();
    }

}
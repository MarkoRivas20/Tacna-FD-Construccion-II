package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

public interface Login {

    interface View{
        void onLogInSuccessful(String nombre_usuario, String id_usuario);
        void onLogInFailure();
        void onSuccessfulCheck();
    }

    interface Presenter{
        void LogIn(DatabaseReference reference, String correo_electronico, String contrasena);
        void SaveSession(Context context, String correo_electronico, String nombre_usuario, String id_usuario);
        void CheckSession(Context context);
    }

    interface Interactor{
        void performLogIn(DatabaseReference reference, String correo_electronico, String contrasena);
        void performSaveSession(Context context, String correo_electronico, String nombre_usuario, String id_usuario);
        void performCheckSession(Context context);
    }

    interface onOperationListener{
        void onSuccessCheck();
        void onSuccess(String nombre_usuario, String id_usuario);
        void onFailure();
    }

}

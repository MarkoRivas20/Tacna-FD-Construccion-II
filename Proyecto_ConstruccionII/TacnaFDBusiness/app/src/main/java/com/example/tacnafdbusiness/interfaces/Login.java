package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

public interface Login {

    interface View{
        void onLogInSuccessful(String Nombre_Usuario, String ID_Usuario);
        void onLogInFailure();
        void onSuccessfulCheck();
    }

    interface Presenter{
        void LogIn(DatabaseReference Database_Reference, String Correo_Electronico, String Contrasena);
        void SaveSession(Context Contexto, String Correo_Electronico, String Nombre_Usuario, String ID_Usuario);
        void CheckSession(Context Contexto);
    }

    interface Interactor{
        void performLogIn(DatabaseReference Database_Reference, String Correo_Electronico, String Contrasena);
        void performSaveSession(Context Contexto, String Correo_Electronico, String Nombre_Usuario, String ID_Usuario);
        void performCheckSession(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessCheck();
        void onSuccess(String Nombre_Usuario, String ID_Usuario);
        void onFailure();
    }
}

package com.example.tacnafdbusiness.interfaces;

import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public interface Login {

    interface View{
        void onLogInSuccessful();
        void onLogInFailure();
    }

    interface Presenter{
        void LogIn(DatabaseReference reference, String correo_electronico, String contrasena);
    }

    interface Interactor{
        void performLogIn(DatabaseReference reference, String correo_electronico, String contrasena);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
    }
}

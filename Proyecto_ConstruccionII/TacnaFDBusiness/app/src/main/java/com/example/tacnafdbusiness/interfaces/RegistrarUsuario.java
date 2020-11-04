package com.example.tacnafdbusiness.interfaces;

import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;


public interface RegistrarUsuario {

    interface View{
        void onCreateUserSuccessful();
        void onCreateUserFailure();
        void onCreateUserFailureUsedMail();
    }

    interface Presenter{
        void CreateNewUser(DatabaseReference Database_Reference, Usuario_Modelo Usuario);
    }

    interface Interactor{
        void performCreateUser(DatabaseReference Database_Reference, Usuario_Modelo Usuario);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onUsedMail();
    }
}

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
        void CreateNewUser(DatabaseReference reference, Usuario_Modelo usuario_modelo);
    }

    interface Interactor{
        void performCreateUser(DatabaseReference reference, Usuario_Modelo usuario_modelo);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onUsedMail();
    }
}

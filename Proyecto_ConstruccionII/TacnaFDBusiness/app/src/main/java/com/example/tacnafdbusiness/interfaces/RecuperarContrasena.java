package com.example.tacnafdbusiness.interfaces;


import com.google.firebase.database.DatabaseReference;

public interface RecuperarContrasena {

    interface View{
        void onRestorePasswordSuccessful();
        void onRestorePasswordFailure();
    }

    interface Presenter{
        void RestorePassword(DatabaseReference Database_Reference, String Correo_Electronico, String Nueva_Contrasena);
    }

    interface Interactor{
        void performRestorePassword(DatabaseReference Database_Reference, String Correo_Electronico, String Nueva_Contrasena);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
    }

}

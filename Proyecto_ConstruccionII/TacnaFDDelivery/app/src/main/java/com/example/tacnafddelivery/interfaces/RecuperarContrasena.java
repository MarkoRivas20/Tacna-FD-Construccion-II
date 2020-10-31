package com.example.tacnafddelivery.interfaces;

import com.google.firebase.database.DatabaseReference;

public interface RecuperarContrasena {

    interface View{
        void onRestorePasswordSuccessful();
        void onRestorePasswordFailure();
    }

    interface Presenter{
        void RestorePassword(DatabaseReference reference, String correo_electronico, String nueva_contrasena);
    }

    interface Interactor{
        void performRestorePassword(DatabaseReference reference, String correo_electronico, String nueva_contrasena);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
    }
}

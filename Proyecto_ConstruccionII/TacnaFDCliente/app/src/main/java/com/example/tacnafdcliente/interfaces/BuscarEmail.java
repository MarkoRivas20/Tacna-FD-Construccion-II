package com.example.tacnafdcliente.interfaces;

import com.google.firebase.database.DatabaseReference;

public interface BuscarEmail {

    interface View{
        void onSearchEmailSuccessful();
        void onSearchEmailFailure();
        void onNotFoundEmailFailure();
    }

    interface Presenter{
        void SearchEmail(DatabaseReference reference, String correo_electronico, int codigo);
    }

    interface Interactor{
        void performSearchEmail(DatabaseReference reference, String correo_electronico, int codigo);
        void performSendEmail(String correo_electronico, int codigo);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onNotFoundEMail();
    }
}

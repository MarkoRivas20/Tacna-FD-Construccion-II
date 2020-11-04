package com.example.tacnafdbusiness.interfaces;

import com.google.firebase.database.DatabaseReference;

public interface BuscarEmail {

    interface View{
        void onSearchEmailSuccessful();
        void onSearchEmailFailure();
        void onNotFoundEmailFailure();
    }

    interface Presenter{
        void SearchEmail(DatabaseReference Database_Reference, String Correo_Electronico, int Codigo);
    }

    interface Interactor{
        void performSearchEmail(DatabaseReference Database_Reference, String Correo_Electronico, int Codigo);
        void performSendEmail(String Correo_Electronico, int Codigo);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onNotFoundEMail();
    }
}

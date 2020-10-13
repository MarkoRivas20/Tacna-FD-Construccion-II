package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarEstablecimiento {

    interface View{
        void onSearchEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> establecimiento);
        void onSearchEstablishmentFailure();
        void onSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void SearchEstablishment(DatabaseReference reference, String ID_Usuario);
        void GetSessionData(Context context);
    }

    interface Interactor{
        void performSearchEstablishment(DatabaseReference reference, String ID_Usuario);
        void performGetSessionData(Context context);
    }

    interface onOperationListener{
        void onSuccess(ArrayList<Establecimiento_Modelo> establecimiento);
        void onFailure();
        void onSuccessGetSessionData(String ID_Usuario);
    }
}

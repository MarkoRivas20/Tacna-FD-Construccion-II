package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface RealizarPedidoDatos {

    interface View{
        void onSaveOrderDataSharedPreferenceSuccessful();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento, String Nombre_Establecimiento);
        void onGetSessionDataSuccessful(String ID_Usuario, String Nombre_Usuario);
    }

    interface Presenter{
        void SaveOrderDataSharedPreference(Context Contexto, String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino);
        void GetEstablishmentInfo(Context Contexto);
        void GetSessionData(Context Contexto);

    }

    interface Interactor{
        void performSaveOrderDataSharedPreference(Context Contexto, String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino);
        void performGetEstablishmentInfo(Context Contexto);
        void performGetSessionData(Context Contexto);

    }

    interface onOperationListener{
        void onSuccessSaveOrderDataSharedPreference();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento, String Nombre_Establecimiento);
        void onSuccessGetSessionData(String ID_Usuario, String Nombre_Usuario);
    }
}

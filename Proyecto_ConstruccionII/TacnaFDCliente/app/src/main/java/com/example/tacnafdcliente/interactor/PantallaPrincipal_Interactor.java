package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tacnafdcliente.interfaces.PantallaPrincipal;

public class PantallaPrincipal_Interactor implements PantallaPrincipal.Interactor {

    private PantallaPrincipal.onOperationListener mListener;

    public PantallaPrincipal_Interactor(PantallaPrincipal.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetSessionData(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Correo_Electronico = sharedPref.getString("correo_electronico","");
        String Nombre_Usuario = sharedPref.getString("nombre_usuario","");

        if(Correo_Electronico.length() != 0)
        {
            mListener.onSuccessSessionData(Correo_Electronico, Nombre_Usuario);
        }
        else{
            mListener.onFailureSessionData();
        }
    }

    @Override
    public void performCloseSession(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("correo_electronico", "");
        editor.putString("nombre_usuario", "");
        editor.putString("id_usuario", "");
        editor.apply();

        mListener.onSuccessCloseSession();
    }
}
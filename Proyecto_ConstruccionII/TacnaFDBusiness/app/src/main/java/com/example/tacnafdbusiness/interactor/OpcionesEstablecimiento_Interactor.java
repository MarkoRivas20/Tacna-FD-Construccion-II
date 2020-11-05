package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tacnafdbusiness.interfaces.OpcionesEstablecimiento;


public class OpcionesEstablecimiento_Interactor implements OpcionesEstablecimiento.Interactor {

    private OpcionesEstablecimiento.onOperationListener mListener;

    public OpcionesEstablecimiento_Interactor(OpcionesEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Obteniendo el Nombre_Establecimiento, Url_Logo del SharedPreferences*/
    @Override
    public void performGetEstablishmentInfo(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Nombre_Establecimiento = sharedPref.getString("nombre_establecimiento","");
        String Url_Logo = sharedPref.getString("url_logo","");

        if(Nombre_Establecimiento.length() != 0)
        {
            mListener.onSuccess(Nombre_Establecimiento,Url_Logo);
        }
        else
        {
            mListener.onFailure();
        }

    }
}

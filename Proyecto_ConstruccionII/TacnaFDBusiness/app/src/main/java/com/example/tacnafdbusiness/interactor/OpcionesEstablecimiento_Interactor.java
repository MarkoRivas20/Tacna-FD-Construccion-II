package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.interfaces.OpcionesEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;

import java.util.ArrayList;

public class OpcionesEstablecimiento_Interactor implements OpcionesEstablecimiento.Interactor {

    private OpcionesEstablecimiento.onOperationListener mListener;

    public OpcionesEstablecimiento_Interactor(OpcionesEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetEstablishmentInfo(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Nombre_Establecimiento = sharedPref.getString("nombre_establecimiento","");
        String Url_Logo = sharedPref.getString("url_logo","");

        if(Nombre_Establecimiento.length() != 0){
            mListener.onSuccess(Nombre_Establecimiento,Url_Logo);
        }
        else{
            mListener.onFailure();
        }

    }
}

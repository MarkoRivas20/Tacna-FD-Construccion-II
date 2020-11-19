package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tacnafdcliente.interfaces.RealizarPedidoDatos;

import java.util.ArrayList;

public class RealizarPedidoDatos_Interactor implements RealizarPedidoDatos.Interactor {

    private RealizarPedidoDatos.onOperationListener mListener;

    public RealizarPedidoDatos_Interactor(RealizarPedidoDatos.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performSaveOrderDataSharedPreference(Context Contexto, String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_pedido", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_usuario", ID_Usuario);
        editor.putString("id_establecimiento", ID_Establecimiento);
        editor.putString("direccion_destino", Direccion_Destino);
        editor.putString("punto_geografico_destino", Punto_Geografico_Destino);
        editor.apply();

        mListener.onSuccessSaveOrderDataSharedPreference();

    }

    @Override
    public void performGetEstablishmentInfo(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");
        String Nombre_Establecimiento = sharedPref.getString("nombre_establecimiento","");

        if(Nombre_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(ID_Establecimiento, Nombre_Establecimiento);
        }
    }

    @Override
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");
        String Nombre_Usuario = sharedPref.getString("nombre_usuario","");

        if(Nombre_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario,Nombre_Usuario);
        }
    }
}

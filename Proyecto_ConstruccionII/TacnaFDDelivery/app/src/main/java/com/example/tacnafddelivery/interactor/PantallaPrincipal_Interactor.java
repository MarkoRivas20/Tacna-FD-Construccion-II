package com.example.tacnafddelivery.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tacnafddelivery.interfaces.PantallaPrincipal;

public class PantallaPrincipal_Interactor implements PantallaPrincipal.Interactor {

    private PantallaPrincipal.onOperationListener mListener;

    public PantallaPrincipal_Interactor(PantallaPrincipal.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetSessionData(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Correo_Electronico = sharedPref.getString("correo_electronico","");
        String Nombre_Usuario = sharedPref.getString("nombre_usuario","");
        String Url_Foto = sharedPref.getString("url_foto","");

        if(Correo_Electronico.length() != 0){
            mListener.onSuccess(Correo_Electronico, Nombre_Usuario, Url_Foto);
        }
        else{
            mListener.onFailure();
        }
    }

    @Override
    public void performCloseSession(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("correo_electronico", "");
        editor.putString("nombre_usuario", "");
        editor.putString("id_usuario", "");
        editor.putString("url_foto", "");
        editor.apply();

        mListener.onSuccessCloseSession();
    }

    @Override
    public void performCheckTrackingOrderSharedPreference(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("seguimiento_pedido", Context.MODE_PRIVATE);
        String Seguimiento = sharedPref.getString("seguimiento_pedido","");

        mListener.onSuccessCheckTrackingOrderSharedPreference(Seguimiento);

    }
}

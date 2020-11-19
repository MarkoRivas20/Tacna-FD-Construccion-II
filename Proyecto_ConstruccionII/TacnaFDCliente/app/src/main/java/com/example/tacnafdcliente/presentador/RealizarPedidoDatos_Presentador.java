package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.RealizarPedidoDatos_Interactor;
import com.example.tacnafdcliente.interfaces.RealizarPedidoDatos;

public class RealizarPedidoDatos_Presentador implements RealizarPedidoDatos.Presenter, RealizarPedidoDatos.onOperationListener {

    private RealizarPedidoDatos.View mView;
    private RealizarPedidoDatos_Interactor mInteractor;

    public RealizarPedidoDatos_Presentador(RealizarPedidoDatos.View mView) {
        this.mView = mView;
        mInteractor=new RealizarPedidoDatos_Interactor(this);
    }

    @Override
    public void SaveOrderDataSharedPreference(Context Contexto, String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino) {
        mInteractor.performSaveOrderDataSharedPreference(Contexto, ID_Usuario, ID_Establecimiento, Direccion_Destino, Punto_Geografico_Destino);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void onSuccessSaveOrderDataSharedPreference() {
        mView.onSaveOrderDataSharedPreferenceSuccessful();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento, String Nombre_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento, Nombre_Establecimiento);
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario, String Nombre_Usuario) {
        mView.onGetSessionDataSuccessful(ID_Usuario, Nombre_Usuario);
    }
}

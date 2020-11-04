package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.PantallaPrincipal_Interactor;
import com.example.tacnafdbusiness.interfaces.PantallaPrincipal;

public class PantallaPrincipal_Presentador implements PantallaPrincipal.onOperationListener, PantallaPrincipal.Presenter {

    private PantallaPrincipal.View mView;
    private PantallaPrincipal_Interactor mInteractor;

    public PantallaPrincipal_Presentador(PantallaPrincipal.View mView) {
        this.mView = mView;
        mInteractor=new PantallaPrincipal_Interactor(this);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void CloseSession(Context Contexto) {
        mInteractor.performCloseSession(Contexto);
    }

    @Override
    public void onSuccess(String Correo_Electronico, String Nombre_Usuario) {
        mView.onSessionDataSuccessful(Correo_Electronico, Nombre_Usuario);
    }

    @Override
    public void onFailure() {
        mView.onSessionDataFailure();
    }

    @Override
    public void onSuccessCloseSession() {
        mView.onCloseSessionSuccessful();
    }
}

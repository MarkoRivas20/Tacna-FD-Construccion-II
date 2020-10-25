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
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
    }

    @Override
    public void CloseSession(Context context) {
        mInteractor.performCloseSession(context);
    }

    @Override
    public void onSuccess(String correo_electronico, String nombre_usuario) {
        mView.onSessionDataSuccessful(correo_electronico, nombre_usuario);
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

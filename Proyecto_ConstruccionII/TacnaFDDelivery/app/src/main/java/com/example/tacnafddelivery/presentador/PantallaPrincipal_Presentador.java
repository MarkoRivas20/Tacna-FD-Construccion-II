package com.example.tacnafddelivery.presentador;

import android.content.Context;

import com.example.tacnafddelivery.interactor.PantallaPrincipal_Interactor;
import com.example.tacnafddelivery.interfaces.PantallaPrincipal;

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
    public void onSuccess(String correo_electronico, String nombre_usuario, String url_foto) {
        mView.onSessionDataSuccessful(correo_electronico, nombre_usuario, url_foto);
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

package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.OpcionesEstablecimiento_Interactor;
import com.example.tacnafdbusiness.interfaces.OpcionesEstablecimiento;

public class OpcionesEstablecimiento_Presentador implements OpcionesEstablecimiento.Presenter, OpcionesEstablecimiento.onOperationListener {

    private OpcionesEstablecimiento.View mView;
    private OpcionesEstablecimiento_Interactor mInteractor;

    public OpcionesEstablecimiento_Presentador(OpcionesEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new OpcionesEstablecimiento_Interactor(this);
    }

    @Override
    public void GetEstablishmentInfo(Context context) {
        mInteractor.performGetEstablishmentInfo(context);
    }

    @Override
    public void onSuccess(String Nombre_Establecimiento, String Url_Logo) {
        mView.onGetEstablishmentInfoSuccessful(Nombre_Establecimiento, Url_Logo);
    }

    @Override
    public void onFailure() {
        mView.onGetEstablishmentInfoFailure();
    }
}

package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.OpcionesEstablecimiento_Interactor;
import com.example.tacnafdcliente.interfaces.OpcionesEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

public class OpcionesEstablecimiento_Presentador implements OpcionesEstablecimiento.Presenter, OpcionesEstablecimiento.onOperationListener {

    private OpcionesEstablecimiento.View mView;
    private OpcionesEstablecimiento_Interactor mInteractor;

    public OpcionesEstablecimiento_Presentador(OpcionesEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new OpcionesEstablecimiento_Interactor(this);
    }

    @Override
    public void GetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento) {
        mInteractor.performGetEstablishmentData(Database_Reference, Id_Establecimiento);
    }

    @Override
    public void GetImagesEstablishment(DatabaseReference Database_Reference, String Id_Establecimiento) {
        mInteractor.performGetImagesEstablishment(Database_Reference, Id_Establecimiento);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento) {
        mView.onGetEstablishmentDataSuccessful(Establecimiento);
    }

    @Override
    public void onFailureGetEstablishmentData() {
        mView.onGetEstablishmentDataFailure();
    }

    @Override
    public void onSuccessGetImagesEstablishment(String[] Imagene_Urls) {
        mView.onGetImagesEstablishmentSuccessful(Imagene_Urls);
    }

    @Override
    public void onFailureGetImagesEstablishment() {
        mView.onGetImagesEstablishmentFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String Id_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(Id_Establecimiento);
    }

    @Override
    public void onFailureGetEstablishmentInfo() {
        mView.onGetEstablishmentInfoFailure();
    }
}

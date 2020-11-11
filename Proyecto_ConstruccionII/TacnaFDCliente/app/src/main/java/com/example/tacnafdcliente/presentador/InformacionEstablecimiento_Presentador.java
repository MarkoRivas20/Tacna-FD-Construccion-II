package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.InformacionEstablecimiento_Interactor;
import com.example.tacnafdcliente.interfaces.InformacionEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.DatabaseReference;

public class InformacionEstablecimiento_Presentador implements InformacionEstablecimiento.Presenter, InformacionEstablecimiento.onOperationListener {

    private InformacionEstablecimiento.View mView;
    private InformacionEstablecimiento_Interactor mInteractor;

    public InformacionEstablecimiento_Presentador(InformacionEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new InformacionEstablecimiento_Interactor(this);
    }

    @Override
    public void GetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento) {
        mInteractor.performGetEstablishmentData(Database_Reference, Id_Establecimiento);
    }

    @Override
    public void GetLatitudeLongitude(Context Contexto, FusedLocationProviderClient Fused_Location_Provider_Client) {
        mInteractor.performGetLatitudeLongitude(Contexto, Fused_Location_Provider_Client);
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
    public void onSuccessGetLatitudeLongitude(Double Latitud, Double Longitud) {
        mView.onGetLatitudeLongitudeSuccessful(Latitud, Longitud);
    }

    @Override
    public void onFailureGetLatitudeLongitude() {
        mView.onGetLatitudeLongitudeFailure();
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

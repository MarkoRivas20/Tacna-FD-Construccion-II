package com.example.tacnafddelivery.presentador;

import android.content.Context;

import com.example.tacnafddelivery.interactor.ListarEstablecimiento_Interactor;
import com.example.tacnafddelivery.interfaces.ListarEstablecimiento;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.RepartidorEstablecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarEstablecimiento_Presentador  implements ListarEstablecimiento.Presenter, ListarEstablecimiento.onOperationListener {

    private ListarEstablecimiento.View mView;
    private ListarEstablecimiento_Interactor mInteractor;

    public ListarEstablecimiento_Presentador(ListarEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new ListarEstablecimiento_Interactor(this);
    }

    @Override
    public void GetEstablishments(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performGetEstablishments(Database_Reference, ID_Usuario);
    }

    @Override
    public void GetEstablishmentInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento) {
        mInteractor.performGetEstablishmentInfo(Database_Reference, Repartidores_Establecimiento);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void SaveIDEstablishment(Context Contexto, String ID_Establecimiento) {
        mInteractor.performSaveIDEstablishment(Contexto, ID_Establecimiento);
    }

    @Override
    public void onSuccessGetEstablishments(ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento) {
        mView.onGetEstablishmentsSuccessful(Repartidores_Establecimiento);
    }

    @Override
    public void onFailureGetEstablishments() {
        mView.onGetEstablishmentsFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(ArrayList<Establecimiento_Modelo> Establecimientos) {
        mView.onGetEstablishmentInfoSuccessful(Establecimientos);
    }

    @Override
    public void onFailureGetEstablishmentInfo() {
        mView.onGetEstablishmentInfoFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onGetSessionDataSuccessful(ID_Usuario);
    }
}

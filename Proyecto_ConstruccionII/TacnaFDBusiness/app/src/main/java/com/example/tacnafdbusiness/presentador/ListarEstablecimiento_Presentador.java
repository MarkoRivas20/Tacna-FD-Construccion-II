package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ListarEstablecimineto_Interactor;
import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarEstablecimiento_Presentador implements ListarEstablecimiento.Presenter, ListarEstablecimiento.onOperationListener {

    private ListarEstablecimiento.View mView;
    private ListarEstablecimineto_Interactor mInteractor;

    public ListarEstablecimiento_Presentador(ListarEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new ListarEstablecimineto_Interactor(this);
    }

    @Override
    public void SearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performSearchEstablishment(Database_Reference, ID_Usuario);
    }

    @Override
    public void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {
        mInteractor.performSaveEstablishmentInfo(Contexto, ID_Establecimiento, Nombre_Establecimiento, Url_Logo, Url_Documento);
    }

    @Override
    public void FilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento) {
        mInteractor.performFilterEstablishment(Establecimientos, Nombre_Establecimiento);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void onSuccess(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento) {
        mView.onSearchEstablishmentSuccessful(Establecimientos, Existe_Establecimiento);
    }

    @Override
    public void onFailure() {
        mView.onSearchEstablishmentFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onSessionDataSuccessful(ID_Usuario);
    }

    @Override
    public void onSuccessFilter(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Buscar_Establecimiento) {
        mView.onFilterSuccessful(Establecimientos, Buscar_Establecimiento);
    }
}

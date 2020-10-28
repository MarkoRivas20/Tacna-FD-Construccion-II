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
    public void SearchEstablishment(DatabaseReference reference, String ID_Usuario) {
        mInteractor.performSearchEstablishment(reference, ID_Usuario);
    }

    @Override
    public void SaveEstablishmentInfo(Context context, String Id_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {
        mInteractor.performSaveEstablishmentInfo(context, Id_Establecimiento, Nombre_Establecimiento, Url_Logo, Url_Documento);
    }

    @Override
    public void FilterEstablishment(ArrayList<Establecimiento_Modelo> establecimientos, String Nombre_Establecimiento) {
        mInteractor.performFilterEstablishment(establecimientos, Nombre_Establecimiento);
    }

    @Override
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
    }

    @Override
    public void onSuccess(ArrayList<Establecimiento_Modelo> establecimiento, Boolean Existe_Establecimiento) {
        mView.onSearchEstablishmentSuccessful(establecimiento, Existe_Establecimiento);
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
    public void onSuccessFilter(ArrayList<Establecimiento_Modelo> establecimientos, Boolean buscar_establecimiento) {
        mView.onFilterSuccessful(establecimientos, buscar_establecimiento);
    }
}

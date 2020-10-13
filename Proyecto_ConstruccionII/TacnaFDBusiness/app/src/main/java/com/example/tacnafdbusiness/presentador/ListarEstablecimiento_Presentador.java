package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ListarEstablecimineto_Interactor;
import com.example.tacnafdbusiness.interactor.Login_Interactor;
import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarEstablecimiento_Presentador implements ListarEstablecimiento.Presenter, ListarEstablecimiento.onOperationListener {

    private ListarEstablecimiento.View mView;
    private ListarEstablecimiento.Interactor mInteractor;

    public ListarEstablecimiento_Presentador(ListarEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new ListarEstablecimineto_Interactor(this);
    }

    @Override
    public void SearchEstablishment(DatabaseReference reference, String ID_Usuario) {
        mInteractor.performSearchEstablishment(reference, ID_Usuario);
    }

    @Override
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
    }

    @Override
    public void onSuccess(ArrayList<Establecimiento_Modelo> establecimiento) {
        mView.onSearchEstablishmentSuccessful(establecimiento);
    }

    @Override
    public void onFailure() {
        mView.onSearchEstablishmentFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onSessionDataSuccessful(ID_Usuario);
    }
}

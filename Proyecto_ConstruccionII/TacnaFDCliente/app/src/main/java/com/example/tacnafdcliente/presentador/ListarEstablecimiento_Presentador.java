package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.ListarEstablecimiento_Interactor;
import com.example.tacnafdcliente.interfaces.ListarEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarEstablecimiento_Presentador implements ListarEstablecimiento.Presenter, ListarEstablecimiento.onOperationListener{

    private ListarEstablecimiento.View mView;
    private ListarEstablecimiento_Interactor mInteractor;

    public ListarEstablecimiento_Presentador(ListarEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new ListarEstablecimiento_Interactor(this);
    }

    @Override
    public void GetAllEstablishment(DatabaseReference Database_Reference) {
        mInteractor.performGetAllEstablishment(Database_Reference);
    }

    @Override
    public void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento) {
        mInteractor.performSaveEstablishmentInfo(Contexto, ID_Establecimiento);
    }

    @Override
    public void FilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento, String Categoria_Establecimiento, String Distrito_Establecimiento) {
        mInteractor.performFilterEstablishment(Establecimientos, Nombre_Establecimiento, Categoria_Establecimiento, Distrito_Establecimiento);
    }

    @Override
    public void onSuccessGetAllEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento) {
        mView.onGetAllEstablishmentSuccessful(Establecimientos, Existe_Establecimiento);
    }

    @Override
    public void onFailureGetAllEstablishment() {
        mView.onGetAllEstablishmentFailure();
    }

    @Override
    public void onSuccessFilter(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Buscar_Establecimiento) {
        mView.onFilterSuccessful(Establecimientos, Buscar_Establecimiento);
    }
}

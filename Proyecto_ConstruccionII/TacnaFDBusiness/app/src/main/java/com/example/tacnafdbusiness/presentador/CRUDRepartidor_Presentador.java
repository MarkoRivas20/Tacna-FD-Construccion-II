package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.CRUDRepartidores_Interactor;
import com.example.tacnafdbusiness.interfaces.CRUDRepartidores;
import com.example.tacnafdbusiness.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CRUDRepartidor_Presentador implements CRUDRepartidores.Presenter, CRUDRepartidores.onOperationListener {

    private CRUDRepartidores.View mView;
    private CRUDRepartidores_Interactor mInteractor;

    public CRUDRepartidor_Presentador(CRUDRepartidores.View mView) {
        this.mView = mView;
        mInteractor=new CRUDRepartidores_Interactor(this);
    }

    @Override
    public void SaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo Repartidor_Establecimiento) {
        mInteractor.performSaveDeliveryMan(Database_Reference, Repartidor_Establecimiento);
    }

    @Override
    public void SearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor) {
        mInteractor.performSearchDeliveryMan(Database_Reference, Correo_Electronico_Repartidor);
    }

    @Override
    public void SearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento) {
        mInteractor.performSearchDeliveryManInfo(Database_Reference, Repartidores_Establecimiento);
    }

    @Override
    public void TakeOutDeliveryMan(DatabaseReference Database_Reference, String ID_Repartidor_Establecimiento, String ID_Establecimiento) {
        mInteractor.performTakeOutDeliveryMan(Database_Reference, ID_Repartidor_Establecimiento, ID_Establecimiento);
    }

    @Override
    public void ListDeliveryMen(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performListDeliveryMen(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessSaveDeliveryMan() {
        mView.onSaveDeliveryManSuccessful();
    }

    @Override
    public void onFailureSaveDeliveryMan() {
        mView.onSaveDeliveryManFailure();
    }

    @Override
    public void onSuccessSearchDeliveryMan(Repartidor_Modelo Repartidor, Boolean Existe_Repartidor) {
        mView.onSearchDeliveryManSuccessful(Repartidor, Existe_Repartidor);
    }

    @Override
    public void onFailureSearchDeliveryMan() {
        mView.onSearchDeliveryManFailure();
    }

    @Override
    public void onSuccessTakeOutDeliveryMan() {
        mView.onTakeOutDeliveryManSuccessful();
    }

    @Override
    public void onFailureTakeOutDeliveryMan() {
        mView.onTakeOutDeliveryManFailure();
    }

    @Override
    public void onSuccessListDeliveryMen(ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento, Boolean Existe_Repartidor_Establecimiento) {
        mView.onListDeliveryMenSuccessful(Repartidores_Establecimiento, Existe_Repartidor_Establecimiento);
    }

    @Override
    public void onFailureListDeliveryMen() {
        mView.onListDeliveryMenFailure();
    }

    @Override
    public void onSuccessSearchDeliveryManInfo(ArrayList<Repartidor_Modelo> Repartidor) {
        mView.onSearchDeliveryManInfoSuccessful(Repartidor);
    }

    @Override
    public void onFailureSearchDeliveryManInfo() {
        mView.onSearchDeliveryManInfoFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

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
    public void SaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo repartidorEstablecimiento_modelo) {
        mInteractor.performSaveDeliveryMan(Database_Reference, repartidorEstablecimiento_modelo);
    }

    @Override
    public void SearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor) {
        mInteractor.performSearchDeliveryMan(Database_Reference, Correo_Electronico_Repartidor);
    }

    @Override
    public void SearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos) {
        mInteractor.performSearchDeliveryManInfo(Database_Reference, repartidorEstablecimiento_modelos);
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
    public void GetEstablishmentInfo(Context context) {
        mInteractor.performGetEstablishmentInfo(context);
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
    public void onSuccessSearchDeliveryMan(Repartidor_Modelo repartidor_modelos, Boolean Existe_Repartidor) {
        mView.onSearchDeliveryManSuccessful(repartidor_modelos, Existe_Repartidor);
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
    public void onSuccessListDeliveryMen(ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos, Boolean Existe_Repartidor_Establecimiento) {
        mView.onListDeliveryMenSuccessful(repartidorEstablecimiento_modelos, Existe_Repartidor_Establecimiento);
    }

    @Override
    public void onFailureListDeliveryMen() {
        mView.onListDeliveryMenFailure();
    }

    @Override
    public void onSuccessSearchDeliveryManInfo(ArrayList<Repartidor_Modelo> repartidor_modelos) {
        mView.onSearchDeliveryManInfoSuccessful(repartidor_modelos);
    }

    @Override
    public void onFailureSearchDeliveryManInfo() {
        mView.onSearchDeliveryManInfoFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String Id_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(Id_Establecimiento);
    }
}

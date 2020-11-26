package com.example.tacnafddelivery.presentador;

import android.content.Context;

import com.example.tacnafddelivery.interactor.ListarPedido_Interactor;
import com.example.tacnafddelivery.interfaces.ListarPedido;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarPedido_Presentador implements ListarPedido.Presenter, ListarPedido.onOperationListener {

    private ListarPedido.View mView;
    private ListarPedido_Interactor mInteractor;

    public ListarPedido_Presentador(ListarPedido.View mView) {
        this.mView = mView;
        mInteractor = new ListarPedido_Interactor(this);
    }

    @Override
    public void GetOrders(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetOrders(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void SearchClientName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos) {
        mInteractor.performSearchClientName(Database_Reference, Pedidos);
    }

    @Override
    public void GetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetEstablishmentInfo(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void GetIDEstablishment(Context Contexto) {
        mInteractor.performGetIDEstablishment(Contexto);
    }

    @Override
    public void SaveIDOrder(Context Contexto, String ID_Pedido) {
        mInteractor.performSaveIDOrder(Contexto, ID_Pedido);
    }

    @Override
    public void onSuccessGetOrders(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Resena) {
        mView.onGetOrdersSuccessful(Pedidos, Existe_Resena);
    }

    @Override
    public void onFailureGetOrders() {
        mView.onGetOrdersFailure();
    }

    @Override
    public void onSuccessSearchClientName(ArrayList<Pedido_Modelo> Pedidos) {
        mView.onSearchClientNameSuccessful(Pedidos);
    }

    @Override
    public void onFailureSearchClientName() {
        mView.onSearchClientNameFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(Establecimiento_Modelo Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(Establecimiento);
    }

    @Override
    public void onFailureGetEstablishmentInfo() {
        mView.onGetEstablishmentInfoFailure();
    }

    @Override
    public void onSuccessGetIDEstablishment(String ID_Establecimiento) {
        mView.onGetIDEstablishmentSuccessful(ID_Establecimiento);
    }
}

package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.ListarPedido_Interactor;
import com.example.tacnafdcliente.interfaces.ListarPedido;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarPedido_Presentador implements ListarPedido.Presenter, ListarPedido.onOperationListener {

    private ListarPedido.View mView;
    private ListarPedido_Interactor mInteractor;

    public ListarPedido_Presentador(ListarPedido.View mView) {
        this.mView = mView;
        mInteractor=new ListarPedido_Interactor(this);
    }

    @Override
    public void GetOrders(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performGetOrders(Database_Reference, ID_Usuario);
    }

    @Override
    public void SearchEstablishmentName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos) {
        mInteractor.performSearchEstablishmentName(Database_Reference, Pedidos);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
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
    public void onSuccessSearchEstablishmentName(ArrayList<Pedido_Modelo> Pedidos) {
        mView.onSearchEstablishmentNameSuccessful(Pedidos);
    }

    @Override
    public void onFailureSearchEstablishmentName() {
        mView.onSearchEstablishmentNameFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onGetSessionDataSuccessful(ID_Usuario);
    }
}
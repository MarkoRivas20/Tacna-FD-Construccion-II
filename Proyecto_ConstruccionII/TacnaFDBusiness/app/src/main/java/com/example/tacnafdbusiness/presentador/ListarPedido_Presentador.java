package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ListarPedido_Interactor;
import com.example.tacnafdbusiness.interactor.ListarResena_Interactor;
import com.example.tacnafdbusiness.interfaces.ListarPedido;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
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
    public void GetOrders(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetOrders(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void SearchClientName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos) {
        mInteractor.performSearchClientName(Database_Reference, Pedidos);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
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
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

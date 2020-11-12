package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ListarPedido_Interactor;
import com.example.tacnafdbusiness.interfaces.ListarPedido;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarPedido_Presentador implements ListarPedido.Presenter, ListarPedido.onOperationListener {

    private ListarPedido.View mView;
    private ListarPedido.Interactor mInteractor;

    public ListarPedido_Presentador(ListarPedido.View mView) {
        this.mView = mView;
        mInteractor=new ListarPedido_Interactor(this);
    }

    @Override
    public void GetReviews(DatabaseReference Database_Reference, String Id_Establecimiento) {
        mInteractor.performGetReviews(Database_Reference, Id_Establecimiento);
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
    public void onSuccessGetReviews(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Pedido) {
        mView.onGetReviewsSuccessful(Pedidos, Existe_Pedido);
    }

    @Override
    public void onFailureGetReviews() {
        mView.onGetReviewsFailure();
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

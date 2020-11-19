package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.RealizarPedidoDetalle_Interactor;
import com.example.tacnafdcliente.interfaces.RealizarPedidoDetalle;
import com.example.tacnafdcliente.modelo.DetallePedido_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RealizarPedidoDetalle_Presentador implements RealizarPedidoDetalle.Presenter, RealizarPedidoDetalle.onOperationListener {

    private RealizarPedidoDetalle.View mView;
    private RealizarPedidoDetalle_Interactor mInteractor;

    public RealizarPedidoDetalle_Presentador(RealizarPedidoDetalle.View mView) {
        this.mView = mView;
        mInteractor=new RealizarPedidoDetalle_Interactor(this);
    }

    @Override
    public void GetItemsMenuForEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetItemsMenuForEstablishment(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void GetOrderDescription(List<DetallePedido_Modelo> Items_Detalle_Pedido) {
        mInteractor.performGetOrderDescription(Items_Detalle_Pedido);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessGetItemsMenuForEstablishment(ArrayList<ItemMenu_Modelo> Items_Menu, ArrayList<String> Nombre_Items_Menu) {
        mView.onGetItemsMenuForEstablishmentSuccessful(Items_Menu, Nombre_Items_Menu);
    }

    @Override
    public void onFailureGetItemsMenuForEstablishment() {
        mView.onGetItemsMenuForEstablishmentFailure();
    }

    @Override
    public void onSuccessGetOrderDescription(String Descripcion_Pedido) {
        mView.onGetOrderDescriptionSuccessful(Descripcion_Pedido);
    }

    @Override
    public void onFailureGetOrderDescription() {
        mView.onGetOrderDescriptionFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

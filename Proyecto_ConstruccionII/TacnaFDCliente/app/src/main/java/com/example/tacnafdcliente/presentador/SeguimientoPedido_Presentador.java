package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.SeguimientoPedido_Interactor;
import com.example.tacnafdcliente.interfaces.SeguimientoPedido;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.SeguimientoPedido_Modelo;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONObject;

import java.util.List;

public class SeguimientoPedido_Presentador implements SeguimientoPedido.Presenter, SeguimientoPedido.onOperationListener {

    private SeguimientoPedido.View mView;
    private SeguimientoPedido_Interactor mInteractor;

    public SeguimientoPedido_Presentador(SeguimientoPedido.View mView) {
        this.mView = mView;
        mInteractor = new SeguimientoPedido_Interactor(this);
    }

    @Override
    public void GetTrackingOrder(DatabaseReference Database_Reference, String ID_Pedido) {
        mInteractor.performGetTrackingOrder(Database_Reference, ID_Pedido);
    }

    @Override
    public void GetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido) {
        mInteractor.performGetOrderInfo(Database_Reference, ID_Pedido);
    }

    @Override
    public void GetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetEstablishmentInfo(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void DrawRoute(JSONObject JSON_Object) {
        mInteractor.performDrawRoute(JSON_Object);
    }

    @Override
    public void GetIDEstablishmentAndIDOrder(Context Contexto) {
        mInteractor.performGetIDEstablishmentAndIDOrder(Contexto);
    }

    @Override
    public void onSuccessGetTrackingOrder(SeguimientoPedido_Modelo Seguimiento_Pedido) {
        mView.onGetTrackingOrderSuccessful(Seguimiento_Pedido);
    }

    @Override
    public void onFailureGetTrackingOrder() {
        mView.onGetTrackingOrderFailure();
    }

    @Override
    public void onSuccessGetOrderInfo(Pedido_Modelo Pedido) {
        mView.onGetOrderInfoSuccessful(Pedido);
    }

    @Override
    public void onFailureGetOrderInfo() {
        mView.onGetOrderInfoFailure();
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
    public void onSuccessDrawRoute(List<LatLng> list) {
        mView.onDrawRouteSuccessful(list);
    }

    @Override
    public void onFailureDrawRoute() {
        mView.onDrawRouteFailure();
    }

    @Override
    public void onSuccessGetIDEstablishmentAndIDOrder(String ID_Establecimiento, String ID_Pedido) {
        mView.onGetIDEstablishmentAndIDOrderSuccessful(ID_Establecimiento, ID_Pedido);
    }
}

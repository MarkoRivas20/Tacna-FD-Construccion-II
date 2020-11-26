package com.example.tacnafddelivery.presentador;


import android.content.Context;
import android.view.View;

import com.example.tacnafddelivery.interactor.SeguimientoPedido_Interactor;
import com.example.tacnafddelivery.interfaces.SeguimientoPedido;
import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.modelo.SeguimientoPedido_Modelo;
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
    public void SaveTrackingOrder(DatabaseReference Database_Reference, SeguimientoPedido_Modelo Seguimiento_Pedido) {
        mInteractor.performSaveTrackingOrder(Database_Reference, Seguimiento_Pedido);
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
    public void GetClientName(DatabaseReference Database_Reference, String ID_Usuario_Cliente) {
        mInteractor.performGetClientName(Database_Reference, ID_Usuario_Cliente);
    }

    @Override
    public void DrawRoute(JSONObject JSON_Object) {
        mInteractor.performDrawRoute(JSON_Object);
    }

    @Override
    public void UpdateOrderStatus(DatabaseReference Database_Reference, String ID_Pedido) {
        mInteractor.performUpdateOrderStatus(Database_Reference, ID_Pedido);
    }

    @Override
    public void GetIDEstablishment(Context Contexto) {
        mInteractor.performGetIDEstablishment(Contexto);
    }

    @Override
    public void GetIDOrder(Context Contexto) {
        mInteractor.performGetIDOrder(Contexto);
    }

    @Override
    public void SetBackPressed(View view) {
        mInteractor.performSetBackPressed(view);
    }

    @Override
    public void onSuccessSaveTrackingOrder() {
        mView.onSaveTrackingOrderSuccessful();
    }

    @Override
    public void onFailureSaveTrackingOrder() {
        mView.onSaveTrackingOrderFailure();
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
    public void onSuccessGetClientName(Cliente_Modelo Cliente) {
        mView.onGetClientNameSuccessful(Cliente);
    }

    @Override
    public void onFailureGetClientName() {
        mView.onGetClientNameFailure();
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
    public void onSuccessUpdateOrderStatus() {
        mView.onUpdateOrderStatusSuccess();
    }

    @Override
    public void onFailureUpdateOrderStatus() {
        mView.onUpdateOrderStatusFailure();
    }

    @Override
    public void onSuccessGetIDEstablishment(String ID_Establecimiento) {
        mView.onGetIDEstablishmentSuccessful(ID_Establecimiento);
    }

    @Override
    public void onSuccessGetIDOrder(String ID_Pedido) {
        mView.onGetIDOrderSuccessful(ID_Pedido);
    }

}

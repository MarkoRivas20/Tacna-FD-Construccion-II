package com.example.tacnafddelivery.presentador;


import android.content.Context;
import android.view.View;

import com.example.tacnafddelivery.interactor.DetallePedido_Interactor;
import com.example.tacnafddelivery.interfaces.DetallePedido;
import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.modelo.SeguimientoPedido_Modelo;
import com.google.firebase.database.DatabaseReference;

public class DetallePedido_Presentador implements DetallePedido.Presenter, DetallePedido.onOperationListener {

    private DetallePedido.View mView;
    private DetallePedido_Interactor mInteractor;

    public DetallePedido_Presentador(DetallePedido.View mView) {
        this.mView = mView;
        mInteractor = new DetallePedido_Interactor(this);
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
    public void UpdateOrderStatus(DatabaseReference Database_Reference, String ID_Pedido) {
        mInteractor.performUpdateOrderStatus(Database_Reference, ID_Pedido);
    }

    @Override
    public void SaveTrackingOrder(DatabaseReference Database_Reference, SeguimientoPedido_Modelo Seguimiento_Pedido) {
        mInteractor.performSaveTrackingOrder(Database_Reference, Seguimiento_Pedido);
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
    public void SaveTrackingOrderSharedPreference(Context Contexto, String Seguimiento) {
        mInteractor.performSaveTrackingOrderSharedPreference(Contexto, Seguimiento);
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
    public void onSuccessUpdateOrderStatus() {
        mView.onUpdateOrderStatusSuccess();
    }

    @Override
    public void onFailureUpdateOrderStatus() {
        mView.onUpdateOrderStatusFailure();
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
    public void onSuccessGetIDEstablishment(String ID_Establecimiento) {
        mView.onGetIDEstablishmentSuccessful(ID_Establecimiento);
    }

    @Override
    public void onSuccessGetIDOrder(String ID_Pedido) {
        mView.onGetIDOrderSuccessful(ID_Pedido);
    }

    @Override
    public void onSuccessSetBackPressed(Boolean aBoolean) {
        mView.onSetBackPressedSuccessful(aBoolean);
    }
}

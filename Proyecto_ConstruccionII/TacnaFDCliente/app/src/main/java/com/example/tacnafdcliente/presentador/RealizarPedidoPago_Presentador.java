package com.example.tacnafdcliente.presentador;

import android.content.Context;


import com.example.tacnafdcliente.interactor.RealizarPedidoPago_Interactor;
import com.example.tacnafdcliente.interfaces.RealizarPedidoPago;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.Map;

public class RealizarPedidoPago_Presentador implements RealizarPedidoPago.Presenter, RealizarPedidoPago.onOperationListener {

    private RealizarPedidoPago.View mView;
    private RealizarPedidoPago_Interactor mInteractor;

    public RealizarPedidoPago_Presentador(RealizarPedidoPago.View mView) {
        this.mView = mView;
        mInteractor=new RealizarPedidoPago_Interactor(this);
    }

    @Override
    public void SaveOrder(DatabaseReference Database_Reference, Pedido_Modelo Pedido) {
        mInteractor.performSaveOrder(Database_Reference, Pedido);
    }

    @Override
    public void GetOrderDataSharedPreference(Context Contexto) {
        mInteractor.performGetOrderDataSharedPreference(Contexto);
    }

    @Override
    public void GetClientName(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performGetClientName(Database_Reference, ID_Usuario);
    }

    @Override
    public void GetEstablishmentData(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetEstablishmentData(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void GetJsonResultFixer(String Url_Fixer) {
        mInteractor.performGetJsonResultFixer(Url_Fixer);
    }

    @Override
    public void GetDollarPrice(String Json_Result) {
        mInteractor.performGetDollarPrice(Json_Result);
    }

    @Override
    public void GetPaymentWithCommission(String Precio_Dolar, Double Total) {
        mInteractor.performGetPaymentWithCommission(Precio_Dolar, Total);
    }

    @Override
    public void MakeCardPayment(Context Contexto, String Codigo_Culqi, String ID_Pedido, Double Total_Pagar, String Correo_Electronico, String Numero_Tarjeta,
                                String CVV_Tarjeta, String Fecha_Vencimiento_Tarjeta) {

        mInteractor.performMakeCardPayment(Contexto, Codigo_Culqi, ID_Pedido, Total_Pagar, Correo_Electronico, Numero_Tarjeta, CVV_Tarjeta, Fecha_Vencimiento_Tarjeta);
    }

    @Override
    public void GetCouponInfo(Context Contexto) {
        mInteractor.performGetCouponInfo(Contexto);
    }

    @Override
    public void UpdateStatusCoupon(DatabaseReference Database_Reference, String ID_Cupon) {
        mInteractor.performUpdateStatusCoupon(Database_Reference, ID_Cupon);
    }

    @Override
    public void UpdateCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento) {
        mInteractor.performUpdateCouponInfo(Contexto, ID_Cupon, ID_Cupon_Usuario, Descuento);
    }

    @Override
    public void CheckInternet(Context Contexto) {
        mInteractor.performCheckInternet(Contexto);
    }

    @Override
    public void onSuccessSaveOrder() {
        mView.onSaveOrderSuccessful();
    }

    @Override
    public void onFailureSaveOrder() {
        mView.onSaveOrderFailure();
    }

    @Override
    public void onSuccessGetOrderDataSharedPreference(String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino) {
        mView.onGetOrderDataSharedPreferenceSuccessful(ID_Usuario, ID_Establecimiento, Direccion_Destino, Punto_Geografico_Destino);
    }

    @Override
    public void onSuccessGetClientName(Usuario_Modelo Usuario) {
        mView.onGetClientNameSuccessful(Usuario);
    }

    @Override
    public void onFailureGetClientName() {
        mView.onGetClientNameFailure();
    }

    @Override
    public void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento) {
        mView.onGetEstablishmentDataSuccessful(Establecimiento);
    }

    @Override
    public void onFailureGetEstablishmentData() {
        mView.onGetEstablishmentDataFailure();
    }

    @Override
    public void onSuccessGetJsonResultFixer(String Json_Result) {
        mView.onGetJsonResultFixerSuccessful(Json_Result);
    }

    @Override
    public void onFailureGetJsonResultFixer() {
        mView.onGetJsonResultFixerFailure();
    }

    @Override
    public void onSuccessGetDollarPrice(String Precio_Dolar) {
        mView.onGetDollarPriceSuccessful(Precio_Dolar);
    }

    @Override
    public void onFailureGetDollarPrice() {
        mView.onGetDollarPriceFailure();
    }

    @Override
    public void onSuccessGetPaymentWithCommission(Double Total_Con_Comision) {
        mView.onGetPaymentWithCommissionSuccessful(Total_Con_Comision);
    }

    @Override
    public void onSuccessMakeCardPayment() {
        mView.onMakeCardPaymentSuccessful();
    }

    @Override
    public void onFailureMakeCardPayment() {
        mView.onMakeCardPaymentFailure();
    }

    @Override
    public void onSuccessGetCouponInfo(String ID_Cupon, String ID_Cupon_Usuario, int Descuento) {
        mView.onGetCouponInfoSuccessful(ID_Cupon, ID_Cupon_Usuario, Descuento);
    }

    @Override
    public void onFailureGetCouponInfo() {
        mView.onGetCouponInfoFailure();
    }

    @Override
    public void onSuccessUpdateStatusCoupon() {
        mView.onUpdateStatusCouponSuccess();
    }

    @Override
    public void onFailureUpdateStatusCoupon() {
        mView.onUpdateStatusCouponFailure();
    }

    @Override
    public void onFailureCheckInternet() {
        mView.onCheckInternetFailure();
    }

    @Override
    public void onSuccessCheckInternet() {
        mView.onCheckInternetSuccessful();
    }
}

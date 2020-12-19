package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.example.tacnafdcliente.vista.RealizarPedidoDatos_Vista;
import com.google.firebase.database.DatabaseReference;

import java.util.Map;

public interface RealizarPedidoPago {

    interface View{
        void onSaveOrderSuccessful();
        void onSaveOrderFailure();
        void onGetOrderDataSharedPreferenceSuccessful(String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino);
        void onGetClientNameSuccessful(Usuario_Modelo Usuario);
        void onGetClientNameFailure();
        void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentDataFailure();
        void onGetJsonResultFixerSuccessful(String Json_Result);
        void onGetJsonResultFixerFailure();
        void onGetDollarPriceSuccessful(String Precio_Dolar);
        void onGetDollarPriceFailure();
        void onGetPaymentWithCommissionSuccessful(Double Total_Con_Comision);
        void onMakeCardPaymentSuccessful();
        void onMakeCardPaymentFailure();
        void onGetCouponInfoSuccessful(String ID_Cupon, String ID_Cupon_Usuario, int Descuento);
        void onGetCouponInfoFailure();
        void onUpdateStatusCouponSuccess();
        void onUpdateStatusCouponFailure();
        void onCheckInternetFailure();
        void onCheckInternetSuccessful();
    }

    interface Presenter{
        void SaveOrder(DatabaseReference Database_Reference, Pedido_Modelo Pedido);
        void GetOrderDataSharedPreference(Context Contexto);
        void GetClientName(DatabaseReference Database_Reference, String ID_Usuario);
        void GetEstablishmentData(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetJsonResultFixer(String Url_Fixer);
        void GetDollarPrice(String Json_Result);
        void GetPaymentWithCommission(String Precio_Dolar, Double Total);
        void MakeCardPayment(Context Contexto ,String Codigo_Culqi, String ID_Pedido, Double Total_Pagar, String Correo_Electronico,
                             String Numero_Tarjeta, String CVV_Tarjeta, String Fecha_Vencimiento_Tarjeta);
        void GetCouponInfo(Context Contexto);
        void UpdateStatusCoupon(DatabaseReference Database_Reference, String ID_Cupon_Usuario);
        void UpdateCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento);
        void CheckInternet(Context Contexto);
    }

    interface Interactor{
        void performSaveOrder(DatabaseReference Database_Reference, Pedido_Modelo Pedido);
        void performGetOrderDataSharedPreference(Context Contexto);
        void performGetClientName(DatabaseReference Database_Reference, String ID_Usuario);
        void performGetEstablishmentData(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetJsonResultFixer(String Url_Fixer);
        void performGetDollarPrice(String Json_Result);
        void performGetPaymentWithCommission(String Precio_Dolar, Double Total);
        void performMakeCardPayment(Context Contexto, String Codigo_Culqi, String ID_Pedido, Double Total_Pagar, String Correo_Electronico,
                             String Numero_Tarjeta, String CVV_Tarjeta, String Fecha_Vencimiento_Tarjeta);
        void performGetCouponInfo(Context Contexto);
        void performUpdateStatusCoupon(DatabaseReference Database_Reference, String ID_Cupon_Usuario);
        void performUpdateCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento);
        void performCheckInternet(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessSaveOrder();
        void onFailureSaveOrder();
        void onSuccessGetOrderDataSharedPreference(String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino);
        void onSuccessGetClientName(Usuario_Modelo Usuario);
        void onFailureGetClientName();
        void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentData();
        void onSuccessGetJsonResultFixer(String Json_Result);
        void onFailureGetJsonResultFixer();
        void onSuccessGetDollarPrice(String Precio_Dolar);
        void onFailureGetDollarPrice();
        void onSuccessGetPaymentWithCommission(Double Total_Con_Comision);
        void onSuccessMakeCardPayment();
        void onFailureMakeCardPayment();
        void onSuccessGetCouponInfo(String ID_Cupon, String ID_Cupon_Usuario, int Descuento);
        void onFailureGetCouponInfo();
        void onSuccessUpdateStatusCoupon();
        void onFailureUpdateStatusCoupon();
        void onFailureCheckInternet();
        void onSuccessCheckInternet();
    }

}

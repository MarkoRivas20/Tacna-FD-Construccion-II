package com.example.tacnafddelivery.interfaces;

import android.content.Context;

import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.modelo.SeguimientoPedido_Modelo;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONObject;

import java.util.List;

public interface SeguimientoPedido {

    interface View{
        void onSaveTrackingOrderSuccessful();
        void onSaveTrackingOrderFailure();
        void onGetOrderInfoSuccessful(Pedido_Modelo Pedido);
        void onGetOrderInfoFailure();
        void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentInfoFailure();
        void onGetClientNameSuccessful(Cliente_Modelo Cliente);
        void onGetClientNameFailure();
        void onDrawRouteSuccessful(List<LatLng> list);
        void onDrawRouteFailure();
        void onUpdateOrderStatusSuccess();
        void onUpdateOrderStatusFailure();
        void onGetIDEstablishmentSuccessful(String ID_Establecimiento);
        void onGetIDOrderSuccessful(String ID_Pedido);
    }

    interface Presenter{
        void SaveTrackingOrder(DatabaseReference Database_Reference, SeguimientoPedido_Modelo Seguimiento_Pedido);
        void GetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido);
        void GetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetClientName(DatabaseReference Database_Reference, String ID_Usuario_Cliente);
        void DrawRoute(JSONObject JSON_Object);
        void UpdateOrderStatus(DatabaseReference Database_Reference, String ID_Pedido);
        void GetIDEstablishment(Context Contexto);
        void GetIDOrder(Context Contexto);
        void SetBackPressed(android.view.View view);
        void UpdateTrackingOrderSharedPreference(Context Contexto, String Seguimiento);
    }

    interface Interactor{
        void performSaveTrackingOrder(DatabaseReference Database_Reference, SeguimientoPedido_Modelo Seguimiento_Pedido);
        void performGetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido);
        void performGetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetClientName(DatabaseReference Database_Reference, String ID_Usuario_Cliente);
        void performDrawRoute(JSONObject JSON_Object);
        void performUpdateOrderStatus(DatabaseReference Database_Reference, String ID_Pedido);
        void performGetIDEstablishment(Context Contexto);
        void performGetIDOrder(Context Contexto);
        void performSetBackPressed(android.view.View view);
        void performUpdateTrackingOrderSharedPreference(Context Contexto, String Seguimiento);
    }

    interface onOperationListener{
        void onSuccessSaveTrackingOrder();
        void onFailureSaveTrackingOrder();
        void onSuccessGetOrderInfo(Pedido_Modelo Pedido);
        void onFailureGetOrderInfo();
        void onSuccessGetEstablishmentInfo(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentInfo();
        void onSuccessGetClientName(Cliente_Modelo Cliente);
        void onFailureGetClientName();
        void onSuccessDrawRoute(List<LatLng> list);
        void onFailureDrawRoute();
        void onSuccessUpdateOrderStatus();
        void onFailureUpdateOrderStatus();
        void onSuccessGetIDEstablishment(String ID_Establecimiento);
        void onSuccessGetIDOrder(String ID_Pedido);
    }
}

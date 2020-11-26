package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.SeguimientoPedido_Modelo;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONObject;

import java.util.List;

public interface SeguimientoPedido {

    interface View{
        void onGetTrackingOrderSuccessful(SeguimientoPedido_Modelo Seguimiento_Pedido);
        void onGetTrackingOrderFailure();
        void onGetOrderInfoSuccessful(Pedido_Modelo Pedido);
        void onGetOrderInfoFailure();
        void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentInfoFailure();
        void onDrawRouteSuccessful(List<LatLng> list);
        void onDrawRouteFailure();
        void onGetIDEstablishmentAndIDOrderSuccessful(String ID_Establecimiento, String ID_Pedido);
    }

    interface Presenter{
        void GetTrackingOrder(DatabaseReference Database_Reference, String ID_Pedido);
        void GetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido);
        void GetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void DrawRoute(JSONObject JSON_Object);
        void GetIDEstablishmentAndIDOrder(Context Contexto);
    }

    interface Interactor{
        void performGetTrackingOrder(DatabaseReference Database_Reference, String ID_Pedido);
        void performGetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido);
        void performGetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performDrawRoute(JSONObject JSON_Object);
        void performGetIDEstablishmentAndIDOrder(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetTrackingOrder(SeguimientoPedido_Modelo Seguimiento_Pedido);
        void onFailureGetTrackingOrder();
        void onSuccessGetOrderInfo(Pedido_Modelo Pedido);
        void onFailureGetOrderInfo();
        void onSuccessGetEstablishmentInfo(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentInfo();
        void onSuccessDrawRoute(List<LatLng> list);
        void onFailureDrawRoute();
        void onSuccessGetIDEstablishmentAndIDOrder(String ID_Establecimiento, String ID_Pedido);
    }

}

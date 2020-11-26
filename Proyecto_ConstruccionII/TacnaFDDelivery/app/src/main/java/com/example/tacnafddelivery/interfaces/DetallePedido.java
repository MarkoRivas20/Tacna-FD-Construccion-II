package com.example.tacnafddelivery.interfaces;

import android.content.Context;
import android.view.View;

import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.modelo.SeguimientoPedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface DetallePedido {

    interface View{
        void onGetOrderInfoSuccessful(Pedido_Modelo Pedido);
        void onGetOrderInfoFailure();
        void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentInfoFailure();
        void onGetClientNameSuccessful(Cliente_Modelo Cliente);
        void onGetClientNameFailure();
        void onUpdateOrderStatusSuccess();
        void onUpdateOrderStatusFailure();
        void onSaveTrackingOrderSuccessful();
        void onSaveTrackingOrderFailure();
        void onGetIDEstablishmentSuccessful(String ID_Establecimiento);
        void onGetIDOrderSuccessful(String ID_Pedido);
        void onSetBackPressedSuccessful(Boolean aBoolean);
    }

    interface Presenter{
        void GetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido);
        void GetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetClientName(DatabaseReference Database_Reference, String ID_Usuario_Cliente);
        void UpdateOrderStatus(DatabaseReference Database_Reference, String ID_Pedido);
        void SaveTrackingOrder(DatabaseReference Database_Reference, SeguimientoPedido_Modelo Seguimiento_Pedido);
        void GetIDEstablishment(Context Contexto);
        void GetIDOrder(Context Contexto);
        void SetBackPressed(android.view.View view);
    }

    interface Interactor{
        void performGetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido);
        void performGetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetClientName(DatabaseReference Database_Reference, String ID_Usuario_Cliente);
        void performUpdateOrderStatus(DatabaseReference Database_Reference, String ID_Pedido);
        void performSaveTrackingOrder(DatabaseReference Database_Reference, SeguimientoPedido_Modelo Seguimiento_Pedido);
        void performGetIDEstablishment(Context Contexto);
        void performGetIDOrder(Context Contexto);
        void performSetBackPressed(android.view.View view);
    }

    interface onOperationListener{
        void onSuccessGetOrderInfo(Pedido_Modelo Pedido);
        void onFailureGetOrderInfo();
        void onSuccessGetEstablishmentInfo(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentInfo();
        void onSuccessGetClientName(Cliente_Modelo Cliente);
        void onFailureGetClientName();
        void onSuccessUpdateOrderStatus();
        void onFailureUpdateOrderStatus();
        void onSuccessSaveTrackingOrder();
        void onFailureSaveTrackingOrder();
        void onSuccessGetIDEstablishment(String ID_Establecimiento);
        void onSuccessGetIDOrder(String ID_Pedido);
        void onSuccessSetBackPressed(Boolean aBoolean);
    }
}

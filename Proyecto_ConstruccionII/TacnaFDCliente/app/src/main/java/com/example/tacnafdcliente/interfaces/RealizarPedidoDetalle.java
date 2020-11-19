package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.DetallePedido_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public interface RealizarPedidoDetalle {

    interface View{
        void onGetItemsMenuForEstablishmentSuccessful(ArrayList<ItemMenu_Modelo> Items_Menu, ArrayList<String> Nombre_Items_Menu);
        void onGetItemsMenuForEstablishmentFailure();
        void onGetOrderDescriptionSuccessful(String Descripcion_Pedido);
        void onGetOrderDescriptionFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void GetItemsMenuForEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetOrderDescription(List<DetallePedido_Modelo> Items_Detalle_Pedido);
        void GetEstablishmentInfo(Context Contexto);

    }

    interface Interactor{
        void performGetItemsMenuForEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetOrderDescription(List<DetallePedido_Modelo> Items_Detalle_Pedido);
        void performGetEstablishmentInfo(Context Contexto);

    }

    interface onOperationListener{
        void onSuccessGetItemsMenuForEstablishment(ArrayList<ItemMenu_Modelo> Items_Menu, ArrayList<String> Nombre_Items_Menu);
        void onFailureGetItemsMenuForEstablishment();
        void onSuccessGetOrderDescription(String Descripcion_Pedido);
        void onFailureGetOrderDescription();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

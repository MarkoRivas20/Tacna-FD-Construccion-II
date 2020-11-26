package com.example.tacnafddelivery.interfaces;

import android.content.Context;

import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarPedido {

    interface View{
        void onGetOrdersSuccessful(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Resena);
        void onGetOrdersFailure();
        void onSearchClientNameSuccessful(ArrayList<Pedido_Modelo> Pedidos);
        void onSearchClientNameFailure();
        void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentInfoFailure();
        void onGetIDEstablishmentSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void GetOrders(DatabaseReference Database_Reference, String ID_Establecimiento);
        void SearchClientName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos);
        void GetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetIDEstablishment(Context Contexto);
        void SaveIDOrder(Context Contexto, String ID_Pedido);
    }

    interface Interactor{
        void performGetOrders(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performSearchClientName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos);
        void performGetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetIDEstablishment(Context Contexto);
        void performSaveIDOrder(Context Contexto, String ID_Pedido);
    }

    interface onOperationListener{
        void onSuccessGetOrders(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Resena);
        void onFailureGetOrders();
        void onSuccessSearchClientName(ArrayList<Pedido_Modelo> Pedidos);
        void onFailureSearchClientName();
        void onSuccessGetEstablishmentInfo(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentInfo();
        void onSuccessGetIDEstablishment(String ID_Establecimiento);
    }

}

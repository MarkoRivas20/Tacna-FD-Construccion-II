package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarPedido {

    interface View{
        void onGetOrdersSuccessful(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Resena);
        void onGetOrdersFailure();
        void onSearchEstablishmentNameSuccessful(ArrayList<Pedido_Modelo> Pedidos);
        void onSearchEstablishmentNameFailure();
        void onGetSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void GetOrders(DatabaseReference Database_Reference, String ID_Usuario);
        void SearchEstablishmentName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos);
        void GetSessionData(Context Contexto);
    }

    interface Interactor{
        void performGetOrders(DatabaseReference Database_Reference, String ID_Usuario);
        void performSearchEstablishmentName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos);
        void performGetSessionData(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetOrders(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Resena);
        void onFailureGetOrders();
        void onSuccessSearchEstablishmentName(ArrayList<Pedido_Modelo> Pedidos);
        void onFailureSearchEstablishmentName();
        void onSuccessGetSessionData(String ID_Usuario);
    }
}

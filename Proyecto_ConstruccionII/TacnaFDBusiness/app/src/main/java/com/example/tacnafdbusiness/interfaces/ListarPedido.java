package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarPedido {
    interface View{
        void onGetReviewsSuccessful(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Pedido);
        void onGetReviewsFailure();
        void onSearchClientNameSuccessful(ArrayList<Pedido_Modelo> Pedidos);
        void onSearchClientNameFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void GetReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void SearchClientName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performGetReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performSearchClientName(DatabaseReference Database_Reference, ArrayList<Pedido_Modelo> Pedidos);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetReviews(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Pedido);
        void onFailureGetReviews();
        void onSuccessSearchClientName(ArrayList<Pedido_Modelo> Pedidos);
        void onFailureSearchClientName();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

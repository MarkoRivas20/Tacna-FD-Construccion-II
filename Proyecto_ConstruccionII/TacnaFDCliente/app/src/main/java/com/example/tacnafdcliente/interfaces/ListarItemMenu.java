package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarItemMenu {

    interface View{
        void onListItemMenuSuccessful(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu);
        void onListItemMenuFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void ListItemMenu(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetEstablishmentInfo(Context Contexto);
        void SaveCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Pedido, int Descuento);
    }

    interface Interactor{
        void performListItemMenu(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetEstablishmentInfo(Context Contexto);
        void performSaveCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Pedido, int Descuento);
    }

    interface onOperationListener{
        void onSuccessListItemMenu(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu);
        void onFailureListItemMenu();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }

}

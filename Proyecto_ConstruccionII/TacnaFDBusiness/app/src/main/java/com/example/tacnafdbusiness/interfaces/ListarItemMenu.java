package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarItemMenu {

    interface View{
        void onListItemMenuSuccessful(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu);
        void onListItemMenuFailure();
        void onDeleteItemMenuSuccessful();
        void onDeleteItemMenuFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void ListItemMenu(DatabaseReference Database_Reference, String ID_Establecimiento);
        void DeleteItemMenu(DatabaseReference Database_Reference, String ID_Item_Menu, String Url_Imagen);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performListItemMenu(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performDeleteItemMenu(DatabaseReference Database_Reference, String ID_Item_Menu, String Url_Imagen);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessListItemMenu(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu);
        void onFailureListItemMenu();
        void onSuccessDeleteItemMenu();
        void onFailureDeleteItemMenu();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }

}

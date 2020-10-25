package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public interface ListarItemMenu {

    interface View{
        void onListItemMenuSuccessful(ArrayList<ItemMenu_Modelo> itemMenu_modelos, Boolean Existe_Item_Menu);
        void onListItemMenuFailure();
        void onDeleteItemMenuSuccessful();
        void onDeleteItemMenuFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
    }

    interface Presenter{
        void ListItemMenu(DatabaseReference reference, String Id_Establecimiento);
        void DeleteItemMenu(DatabaseReference Database_Reference, String Id_Item_Menu, String Url_Imagen);
        void GetEstablishmentInfo(Context context);
    }

    interface Interactor{
        void performListItemMenu(DatabaseReference reference, String Id_Establecimiento);
        void performDeleteItemMenu(DatabaseReference Database_Reference, String Id_Item_Menu, String Url_Imagen);
        void performGetEstablishmentInfo(Context context);
    }

    interface onOperationListener{
        void onSuccessListItemMenu(ArrayList<ItemMenu_Modelo> itemMenu_modelos, Boolean Existe_Item_Menu);
        void onFailureListItemMenu();
        void onSuccessDeleteItemMenu();
        void onFailureDeleteItemMenu();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
    }

}

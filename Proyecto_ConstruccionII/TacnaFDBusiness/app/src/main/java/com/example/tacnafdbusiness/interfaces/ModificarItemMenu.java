package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public interface ModificarItemMenu {

    interface View{
        void onUpdateItemMenuDataFailure();
        void onUpdateItemMenuDataSuccessful();
        void onUpdateItemMenuImageFailure();
        void onUpdateItemMenuImageSuccessful(String Url_Imagen);
        void onGetItemMenuDataSuccessful(ItemMenu_Modelo Item_Menu);
        void onGetItemMenuDataFailure();
    }

    interface Presenter{
        void UpdateItemMenuData(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu);
        void UpdateItemMenuImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Imagen_Actual, String ID_Establecimiento, String ID_Item_Menu, Uri Imagen_Uri);
        void GetItemMenuData(DatabaseReference Database_Reference, String Id_Item_Menu);
    }

    interface Interactor{
        void performUpdateItemMenuData(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu);
        void performUpdateItemMenuImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Imagen_Actual, String ID_Establecimiento, String ID_Item_Menu, Uri Imagen_Uri);
        void performGetItemMenuData(DatabaseReference Database_Reference, String Id_Item_Menu);
    }

    interface onOperationListener{
        void onSuccessUpdateItemMenuData();
        void onFailureUpdateItemMenuData();
        void onSuccessUpdateItemMenuImage(String Url_Imagen);
        void onFailureUpdateItemMenuImage();
        void onSuccessGetItemMenuData(ItemMenu_Modelo Item_Menu);
        void onFailureGetItemMenuData();
    }
}

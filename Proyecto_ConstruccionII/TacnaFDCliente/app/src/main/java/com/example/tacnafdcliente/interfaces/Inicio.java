package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface Inicio {

    interface View{
        void onSearchEstablishmentByCategorySuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onSearchEstablishmentByCategoryFailure();
        void onGetFourBestEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos);
        void onListItemMenuSuccessful(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu);
        void onListItemMenuFailure();
        void onGetItemsMenuByCategorySuccessful(ArrayList<ItemMenu_Modelo> Items_Menu);
        void onGetEstablishmentNameSuccessful(String ID_Establecimiento, String Nombre_Establecimiento);
    }

    interface Presenter{
        void SearchEstablishmentByCategory(DatabaseReference Database_Reference, String Categoria);
        void GetFourBestEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos);
        void ListItemMenu(DatabaseReference Database_Reference);
        void GetItemsMenuByCategory(ArrayList<Establecimiento_Modelo> Establecimientos, ArrayList<ItemMenu_Modelo> Items_Menu);
        void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento);
        void GetEstablishmentName(ArrayList<Establecimiento_Modelo> Establecimientos, String ID_Establecimiento);
    }

    interface Interactor{
        void performSearchEstablishmentByCategory(DatabaseReference Database_Reference, String Categoria);
        void performGetFourBestEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos);
        void performListItemMenu(DatabaseReference Database_Reference);
        void performSaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento);
        void performGetItemsMenuByCategory(ArrayList<Establecimiento_Modelo> Establecimientos, ArrayList<ItemMenu_Modelo> Items_Menu);
        void performGetEstablishmentName(ArrayList<Establecimiento_Modelo> Establecimientos, String ID_Establecimiento);
    }

    interface onOperationListener{
        void onSuccessSearchEstablishmentByCategory(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onFailureSearchEstablishmentByCategory();
        void onSuccessGetFourBestEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos);
        void onSuccessListItemMenu(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu);
        void onFailureListItemMenu();
        void onSuccessGetItemsMenuByCategory(ArrayList<ItemMenu_Modelo> Items_Menu);
        void onSuccessGetEstablishmentName(String ID_Establecimiento, String Nombre_Establecimiento);
    }

}

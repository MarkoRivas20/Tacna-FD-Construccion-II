package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.Inicio_Interactor;
import com.example.tacnafdcliente.interactor.ListarCupon_Interactor;
import com.example.tacnafdcliente.interfaces.Inicio;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Inicio_Presentador implements Inicio.Presenter, Inicio.onOperationListener {

    private Inicio.View mView;
    private Inicio_Interactor mInteractor;

    public Inicio_Presentador(Inicio.View mView) {
        this.mView = mView;
        mInteractor=new Inicio_Interactor(this);
    }

    @Override
    public void SearchEstablishmentByCategory(DatabaseReference Database_Reference, String Categoria) {
        mInteractor.performSearchEstablishmentByCategory(Database_Reference, Categoria);
    }

    @Override
    public void GetFourBestEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos) {
        mInteractor.performGetFourBestEstablishment(Establecimientos);
    }

    @Override
    public void ListItemMenu(DatabaseReference Database_Reference) {
        mInteractor.performListItemMenu(Database_Reference);
    }

    @Override
    public void GetItemsMenuByCategory(ArrayList<Establecimiento_Modelo> Establecimientos, ArrayList<ItemMenu_Modelo> Items_Menu) {
        mInteractor.performGetItemsMenuByCategory(Establecimientos, Items_Menu);
    }

    @Override
    public void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento) {
        mInteractor.performSaveEstablishmentInfo(Contexto, ID_Establecimiento, Nombre_Establecimiento);
    }

    @Override
    public void GetEstablishmentName(ArrayList<Establecimiento_Modelo> Establecimientos, String ID_Establecimiento) {
        mInteractor.performGetEstablishmentName(Establecimientos, ID_Establecimiento);
    }

    @Override
    public void onSuccessSearchEstablishmentByCategory(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento) {
        mView.onSearchEstablishmentByCategorySuccessful(Establecimientos, Existe_Establecimiento);
    }

    @Override
    public void onFailureSearchEstablishmentByCategory() {
        mView.onSearchEstablishmentByCategoryFailure();
    }

    @Override
    public void onSuccessGetFourBestEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos) {
        mView.onGetFourBestEstablishmentSuccessful(Establecimientos);
    }

    @Override
    public void onSuccessListItemMenu(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu) {
        mView.onListItemMenuSuccessful(Items_Menu, Existe_Item_Menu);
    }

    @Override
    public void onFailureListItemMenu() {
        mView.onListItemMenuFailure();
    }

    @Override
    public void onSuccessGetItemsMenuByCategory(ArrayList<ItemMenu_Modelo> Items_Menu) {
        mView.onGetItemsMenuByCategorySuccessful(Items_Menu);
    }

    @Override
    public void onSuccessGetEstablishmentName(String ID_Establecimiento, String Nombre_Establecimiento) {
        mView.onGetEstablishmentNameSuccessful(ID_Establecimiento, Nombre_Establecimiento);
    }
}

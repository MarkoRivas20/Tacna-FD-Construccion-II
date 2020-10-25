package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.CRUDImagenes_Interactor;
import com.example.tacnafdbusiness.interactor.ListarItemMenu_Interactor;
import com.example.tacnafdbusiness.interfaces.CRUDImagenes;
import com.example.tacnafdbusiness.interfaces.ListarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarItemMenu_Presentador implements ListarItemMenu.Presenter, ListarItemMenu.onOperationListener {

    private ListarItemMenu.View mView;
    private ListarItemMenu_Interactor mInteractor;

    public ListarItemMenu_Presentador(ListarItemMenu.View mView) {
        this.mView = mView;
        mInteractor=new ListarItemMenu_Interactor(this);
    }

    @Override
    public void ListItemMenu(DatabaseReference reference, String Id_Establecimiento) {
        mInteractor.performListItemMenu(reference, Id_Establecimiento);
    }

    @Override
    public void DeleteItemMenu(DatabaseReference Database_Reference, String Id_Item_Menu, String Url_Imagen) {
        mInteractor.performDeleteItemMenu(Database_Reference, Id_Item_Menu, Url_Imagen);
    }

    @Override
    public void GetEstablishmentInfo(Context context) {
        mInteractor.performGetEstablishmentInfo(context);
    }

    @Override
    public void onSuccessListItemMenu(ArrayList<ItemMenu_Modelo> itemMenu_modelos, Boolean Existe_Item_Menu) {
        mView.onListItemMenuSuccessful(itemMenu_modelos, Existe_Item_Menu);
    }

    @Override
    public void onFailureListItemMenu() {
        mView.onListItemMenuFailure();
    }

    @Override
    public void onSuccessDeleteItemMenu() {
        mView.onDeleteItemMenuSuccessful();
    }

    @Override
    public void onFailureDeleteItemMenu() {
        mView.onDeleteItemMenuFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String Id_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(Id_Establecimiento);
    }
}

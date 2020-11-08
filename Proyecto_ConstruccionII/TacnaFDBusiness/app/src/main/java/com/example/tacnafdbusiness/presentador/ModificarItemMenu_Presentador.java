package com.example.tacnafdbusiness.presentador;

import android.net.Uri;

import com.example.tacnafdbusiness.interactor.ModificarItemMenu_Interactor;
import com.example.tacnafdbusiness.interfaces.ModificarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ModificarItemMenu_Presentador implements ModificarItemMenu.Presenter, ModificarItemMenu.onOperationListener {

    private ModificarItemMenu.View mView;
    private ModificarItemMenu_Interactor mInteractor;

    public ModificarItemMenu_Presentador(ModificarItemMenu.View mView) {
        this.mView = mView;
        mInteractor=new ModificarItemMenu_Interactor(this);
    }

    @Override
    public void UpdateItemMenuData(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu) {
        mInteractor.performUpdateItemMenuData(Database_Reference, Item_Menu);
    }

    @Override
    public void UpdateItemMenuImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Imagen_Actual, String ID_Establecimiento, String ID_Item_Menu,
                                    Uri Imagen_Uri) {
        mInteractor.performUpdateItemMenuImage(Storage_Reference, Database_Reference, Url_Imagen_Actual, ID_Establecimiento, ID_Item_Menu, Imagen_Uri);
    }

    @Override
    public void GetItemMenuData(DatabaseReference Database_Reference, String ID_Item_Menu) {
        mInteractor.performGetItemMenuData(Database_Reference, ID_Item_Menu);
    }

    @Override
    public void onSuccessUpdateItemMenuData() {
        mView.onUpdateItemMenuDataSuccessful();
    }

    @Override
    public void onFailureUpdateItemMenuData() {
        mView.onUpdateItemMenuDataFailure();
    }

    @Override
    public void onSuccessUpdateItemMenuImage(String Url_Imagen) {
        mView.onUpdateItemMenuImageSuccessful(Url_Imagen);
    }

    @Override
    public void onFailureUpdateItemMenuImage() {
        mView.onUpdateItemMenuImageFailure();
    }

    @Override
    public void onSuccessGetItemMenuData(ItemMenu_Modelo Item_Menu) {
        mView.onGetItemMenuDataSuccessful(Item_Menu);
    }

    @Override
    public void onFailureGetItemMenuData() {
        mView.onGetItemMenuDataFailure();
    }
}

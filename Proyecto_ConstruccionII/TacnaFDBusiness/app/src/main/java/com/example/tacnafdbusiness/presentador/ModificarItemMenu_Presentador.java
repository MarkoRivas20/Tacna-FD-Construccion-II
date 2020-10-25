package com.example.tacnafdbusiness.presentador;

import android.net.Uri;

import com.example.tacnafdbusiness.interactor.ModificarEstablecimiento_Interactor;
import com.example.tacnafdbusiness.interactor.ModificarItemMenu_Interactor;
import com.example.tacnafdbusiness.interfaces.ModificarEstablecimiento;
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
    public void UpdateItemMenuData(DatabaseReference reference, ItemMenu_Modelo itemMenu_modelo) {
        mInteractor.performUpdateItemMenuData(reference, itemMenu_modelo);
    }

    @Override
    public void UpdateItemMenuImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Imagen_Actual, String Id_Establecimiento, String Id_Item_Menu, Uri Imagen_Uri) {
        mInteractor.performUpdateItemMenuImage(Storage_Reference, Database_Reference, Url_Imagen_Actual, Id_Establecimiento, Id_Item_Menu, Imagen_Uri);
    }

    @Override
    public void GetItemMenuData(DatabaseReference Database_Reference, String Id_Item_Menu) {
        mInteractor.performGetItemMenuData(Database_Reference, Id_Item_Menu);
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
    public void onSuccessGetItemMenuData(ItemMenu_Modelo itemMenu_modelo) {
        mView.onGetItemMenuDataSuccessful(itemMenu_modelo);
    }

    @Override
    public void onFailureGetItemMenuData() {
        mView.onGetItemMenuDataFailure();
    }
}

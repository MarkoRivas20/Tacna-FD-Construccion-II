package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.RegistrarItemMenu_Interactor;
import com.example.tacnafdbusiness.interfaces.RegistrarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class RegistrarItemMenu_Presentador implements RegistrarItemMenu.Presenter, RegistrarItemMenu.onOperationListener {

    private RegistrarItemMenu.View mView;
    private RegistrarItemMenu_Interactor mInteractor;

    public RegistrarItemMenu_Presentador(RegistrarItemMenu.View mView) {
        this.mView = mView;
        mInteractor=new RegistrarItemMenu_Interactor(this);
    }

    @Override
    public void SaveItemMenu(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu) {
        mInteractor.performSaveItemMenu(Database_Reference, Item_Menu);
    }

    @Override
    public void UploadItemMenuImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri) {
        mInteractor.performUploadItemMenuImage(Storage_Reference, ID_Establecimiento, Imagen_Uri);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessSaveItemMenu() {
        mView.onSaveItemMenuSuccessful();
    }

    @Override
    public void onFailureSaveItemMenu() {
        mView.onSaveItemMenuFailure();
    }

    @Override
    public void onSuccessUploadItemMenuImage(String Url_Imagen) {
        mView.onUploadItemMenuImageSuccessful(Url_Imagen);
    }

    @Override
    public void onFailureUploadItemMenuImage() {
        mView.onUploadItemMenuImageFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

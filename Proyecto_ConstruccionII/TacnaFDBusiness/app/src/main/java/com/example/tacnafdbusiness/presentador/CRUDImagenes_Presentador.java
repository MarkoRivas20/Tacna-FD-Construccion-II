package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.CRUDImagenes_Interactor;
import com.example.tacnafdbusiness.interfaces.CRUDImagenes;
import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CRUDImagenes_Presentador implements CRUDImagenes.Presenter, CRUDImagenes.onOperationListener {

    private CRUDImagenes.View mView;
    private CRUDImagenes_Interactor mInteractor;

    public CRUDImagenes_Presentador(CRUDImagenes.View mView) {
        this.mView = mView;
        mInteractor=new CRUDImagenes_Interactor(this);
    }

    @Override
    public void UploadImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, ImagenEstablecimiento_Modelo Imagen_Establecimiento, Uri Imagen_Uri) {
        mInteractor.performUploadImage(Storage_Reference, Database_Reference, Imagen_Establecimiento, Imagen_Uri);
    }

    @Override
    public void DeleteImage(DatabaseReference Database_Reference, String ID_Imagen_Establecimiento, String Url_Imagen) {
        mInteractor.performDeleteImage(Database_Reference, ID_Imagen_Establecimiento, Url_Imagen);
    }

    @Override
    public void GetAllImages(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetAllImages(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessUploadImage() {
        mView.onUploadImageSuccessful();
    }

    @Override
    public void onFailureUploadImage() {
        mView.onUploadImageFailure();
    }

    @Override
    public void onSuccessDeleteImage() {
        mView.onDeleteImageSuccessful();
    }

    @Override
    public void onFailureDeleteImage() {
        mView.onDeleteImageFailure();
    }

    @Override
    public void onSuccessGetAllImages(ArrayList<ImagenEstablecimiento_Modelo> Imagenes_Establecimiento, Boolean Existe_Imagen) {
        mView.onGetAllImagesSuccessful(Imagenes_Establecimiento, Existe_Imagen);
    }

    @Override
    public void onFailureGetAllImages() {
        mView.onGetAllImagesFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

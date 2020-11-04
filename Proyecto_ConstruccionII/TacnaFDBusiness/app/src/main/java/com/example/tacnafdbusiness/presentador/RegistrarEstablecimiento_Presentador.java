package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.RegistrarEstablecimiento_Interactor;
import com.example.tacnafdbusiness.interfaces.RegistrarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class RegistrarEstablecimiento_Presentador implements RegistrarEstablecimiento.Presenter, RegistrarEstablecimiento.onOperationListener {

    private RegistrarEstablecimiento.View mView;
    private RegistrarEstablecimiento_Interactor mInteractor;

    public RegistrarEstablecimiento_Presentador(RegistrarEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new RegistrarEstablecimiento_Interactor(this);
    }

    @Override
    public void CreateNewEstablishment(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento) {
        mInteractor.performCreateEstablishment(Database_Reference, Establecimiento);
    }

    @Override
    public void UploadLogo(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri) {
        mInteractor.performUploadLogo(Storage_Reference, ID_Establecimiento, Imagen_Uri);
    }

    @Override
    public void UploadDocument(StorageReference Storage_Reference, String ID_Establecimiento, Uri Documento_Uri) {
        mInteractor.performUploadDocument(Storage_Reference, ID_Establecimiento, Documento_Uri);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void onSuccess() {
        mView.onCreateEstablishmentSuccessful();
    }

    @Override
    public void onFailure() {
        mView.onCreateEstablishmentFailure();
    }

    @Override
    public void onSuccessUploadLogo(String Url) {
        mView.onUploadLogoSuccessful(Url);
    }

    @Override
    public void onFailureUploadLogo() {
        mView.onUploadLogoFailure();
    }

    @Override
    public void onSuccessUploadDocument(String Url) {
        mView.onUploadDocumentSuccessful(Url);
    }

    @Override
    public void onFailureUploadDocument() {
        mView.onUploadDocumentFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onSessionDataSuccessful(ID_Usuario);
    }
}

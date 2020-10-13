package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.RecuperarContrasena_Interactor;
import com.example.tacnafdbusiness.interactor.RegistrarEstablecimiento_Interactor;
import com.example.tacnafdbusiness.interfaces.RecuperarContrasena;
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
    public void CreateNewEstablishment(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo) {
        mInteractor.performCreateEstablishment(reference, establecimiento_modelo);
    }

    @Override
    public void UploadLogo(StorageReference reference, String Id_Establecimiento, Uri Image_Uri) {
        mInteractor.performUploadLogo(reference, Id_Establecimiento, Image_Uri);
    }

    @Override
    public void UploadDocument(StorageReference reference, String Id_Establecimiento, Uri Document_Uri) {
        mInteractor.performUploadDocument(reference, Id_Establecimiento, Document_Uri);
    }

    @Override
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
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

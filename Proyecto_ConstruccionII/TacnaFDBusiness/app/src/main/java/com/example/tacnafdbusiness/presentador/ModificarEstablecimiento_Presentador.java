package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.ModificarEstablecimiento_Interactor;
import com.example.tacnafdbusiness.interfaces.ModificarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ModificarEstablecimiento_Presentador implements ModificarEstablecimiento.Presenter, ModificarEstablecimiento.onOperationListener {

    private ModificarEstablecimiento.View mView;
    private ModificarEstablecimiento_Interactor mInteractor;

    public ModificarEstablecimiento_Presentador(ModificarEstablecimiento.View mView) {
        this.mView = mView;
        mInteractor=new ModificarEstablecimiento_Interactor(this);
    }

    @Override
    public void UpdateEstablismentData(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento) {
        mInteractor.performUpdateEstablismentData(Database_Reference, Establecimiento);
    }

    @Override
    public void UpdateEstablismentLogo(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String ID_Establecimiento, Uri Logo_Uri) {
        mInteractor.performUpdateEstablismentLogo(Storage_Reference, Database_Reference, Url_Logo_Actual, ID_Establecimiento, Logo_Uri);
    }

    @Override
    public void UpdateEstablismentDocument(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Document_Actual, String ID_Establecimiento,
                                           Uri Documento_Uri) {
        mInteractor.performUpdateEstablismentDocument(Storage_Reference, Database_Reference, Url_Document_Actual, ID_Establecimiento, Documento_Uri);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void GetEstablishmentData(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetEstablishmentData(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void UpdateEstablishmentInfo(Context Contexto, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {
        mInteractor.performUpdateEstablishmentInfo(Contexto, Nombre_Establecimiento, Url_Logo, Url_Documento);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }


    @Override
    public void onSuccessUpdateEstablismentData() {
        mView.onUpdateEstablismentDataSuccessful();
    }

    @Override
    public void onFailureUpdateEstablismentData() {
        mView.onUpdateEstablismentDataFailure();
    }

    @Override
    public void onSuccessUpdateEstablismentLogo(String Url_Logo) {
        mView.onUpdateEstablismentLogoSuccessful(Url_Logo);
    }

    @Override
    public void onFailureUpdateEstablismentLogo() {
        mView.onUpdateEstablismentLogoFailure();
    }

    @Override
    public void onSuccessUpdateEstablismentDocument(String Url_Documento) {
        mView.onUpdateEstablismentDocumentSuccessful(Url_Documento);
    }

    @Override
    public void onFailureUpdateEstablismentDocument() {
        mView.onUpdateEstablismentDocumentFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento, String Url_Logo, String Url_Documento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento, Url_Logo, Url_Documento);
    }

    @Override
    public void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento) {
        mView.onGetEstablishmentDataSuccessful(Establecimiento);
    }

    @Override
    public void onFailureGetEstablishmentData() {
        mView.onGetEstablishmentDataFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onGetSessionDataSuccessful(ID_Usuario);
    }

    @Override
    public void onSuccessUpdateEstablishmentInfo() {
        mView.onUpdateEstablishmentInfoSuccessful();
    }
}

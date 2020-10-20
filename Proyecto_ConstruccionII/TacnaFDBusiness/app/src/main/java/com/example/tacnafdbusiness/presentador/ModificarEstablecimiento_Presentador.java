package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.Login_Interactor;
import com.example.tacnafdbusiness.interactor.ModificarEstablecimiento_Interactor;
import com.example.tacnafdbusiness.interactor.ModificarUsuario_Interactor;
import com.example.tacnafdbusiness.interfaces.Login;
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
    public void UpdateEstablismentData(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo) {
        mInteractor.performUpdateEstablismentData(reference, establecimiento_modelo);
    }

    @Override
    public void UpdateEstablismentLogo(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String Id_Establecimiento, Uri Logo_Uri) {
        mInteractor.performUpdateEstablismentLogo(Storage_Reference, Database_Reference, Url_Logo_Actual, Id_Establecimiento, Logo_Uri);
    }

    @Override
    public void UpdateEstablismentDocument(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Document_Actual, String Id_Establecimiento, Uri Documento_Uri) {
        mInteractor.performUpdateEstablismentDocument(Storage_Reference, Database_Reference, Url_Document_Actual, Id_Establecimiento, Documento_Uri);
    }

    @Override
    public void GetEstablishmentInfo(Context context) {
        mInteractor.performGetEstablishmentInfo(context);
    }

    @Override
    public void GetEstablishmentData(DatabaseReference reference, String Id_Establecimiento) {
        mInteractor.performGetEstablishmentData(reference, Id_Establecimiento);
    }

    @Override
    public void UpdateEstablishmentInfo(Context context, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {
        mInteractor.performUpdateEstablishmentInfo(context, Nombre_Establecimiento, Url_Logo, Url_Documento);
    }

    @Override
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
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
    public void onSuccessGetEstablishmentInfo(String Id_Establecimiento, String Url_Logo, String Url_Documento) {
        mView.onGetEstablishmentInfoSuccessful(Id_Establecimiento, Url_Logo, Url_Documento);
    }

    @Override
    public void onSuccessGetEstablishmentData(ArrayList<Establecimiento_Modelo> establecimiento) {
        mView.onGetEstablishmentDataSuccessful(establecimiento);
    }

    @Override
    public void onFailureGetEstablishmentData() {
        mView.onGetEstablishmentDataFailure();
    }

    @Override
    public void onSuccessGetSessionData(String Id_Usuario) {
        mView.onGetSessionDataSuccessful(Id_Usuario);
    }

    @Override
    public void onSuccessUpdateEstablishmentInfo() {
        mView.onUpdateEstablishmentInfoSuccessful();
    }
}

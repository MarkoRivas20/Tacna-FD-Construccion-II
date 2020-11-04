package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.ConfigurarMetodosPago_Interactor;
import com.example.tacnafdbusiness.interfaces.ConfigurarMetodosPago;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class ConfigurarMetodosPago_Presentador implements ConfigurarMetodosPago.Presenter, ConfigurarMetodosPago.onOperationListener {

    private ConfigurarMetodosPago.View mView;
    private ConfigurarMetodosPago_Interactor mInteractor;

    public ConfigurarMetodosPago_Presentador(ConfigurarMetodosPago.View mView) {
        this.mView = mView;
        mInteractor=new ConfigurarMetodosPago_Interactor(this);
    }

    @Override
    public void GetPaymentsMethods(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performGetPaymentsMethods(Database_Reference, ID_Usuario);
    }

    @Override
    public void UpdatePaymentsMethods(DatabaseReference Database_Reference, String ID_Usuario, String Codigo_Paypal, String Codigo_Culqi, String Url_Qr) {
        mInteractor.performUpdatePaymentsMethods(Database_Reference, ID_Usuario, Codigo_Paypal, Codigo_Culqi, Url_Qr);
    }

    @Override
    public void UpdateQRImage(StorageReference Storage_Reference, String ID_Usuario, Uri Imagen_Uri) {
        mInteractor.performUpdateQRImage(Storage_Reference, ID_Usuario, Imagen_Uri);
    }

    @Override
    public void DeleteQRImage(String Url_Qr) {
        mInteractor.performDeleteQRImage(Url_Qr);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessGetPaymentsMethods(Establecimiento_Modelo Establecimiento) {
        mView.onGetPaymentsMethodsSuccessful(Establecimiento);
    }

    @Override
    public void onFailureGetPaymentsMethods() {
        mView.onGetPaymentsMethodsFailure();
    }

    @Override
    public void onSuccessUpdatePaymentsMethods() {
        mView.onUpdatePaymentsMethodsSuccessful();
    }

    @Override
    public void onFailureUpdatePaymentsMethods() {
        mView.onUpdatePaymentsMethodsFailure();
    }

    @Override
    public void onSuccessUpdateQRImage(String Url_Qr) {
        mView.onUpdateQRImageSuccessful(Url_Qr);
    }

    @Override
    public void onFailureUpdateQRImage() {
        mView.onUpdateQRImageFailure();
    }

    @Override
    public void onSuccessDeleteQRImage() {
        mView.onDeleteQRImageSuccessful();
    }

    @Override
    public void onFailureDeleteQRImage() {
        mView.onDeleteQRImageFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

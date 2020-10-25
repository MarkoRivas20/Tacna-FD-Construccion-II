package com.example.tacnafdbusiness.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.interactor.RegistrarCupon_Interactor;
import com.example.tacnafdbusiness.interactor.RegistrarItemMenu_Interactor;
import com.example.tacnafdbusiness.interfaces.RegistrarCupon;
import com.example.tacnafdbusiness.interfaces.RegistrarItemMenu;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class RegistrarCupon_Presentador implements RegistrarCupon.Presenter, RegistrarCupon.onOperationListener {

    private RegistrarCupon.View mView;
    private RegistrarCupon_Interactor mInteractor;

    public RegistrarCupon_Presentador(RegistrarCupon.View mView) {
        this.mView = mView;
        mInteractor=new RegistrarCupon_Interactor(this);
    }

    @Override
    public void SaveCoupon(DatabaseReference Database_Reference, Cupon_Modelo cupon_modelo) {
        mInteractor.performSaveCoupon(Database_Reference, cupon_modelo);
    }

    @Override
    public void UploadCouponImage(StorageReference Storage_Reference, String Id_Establecimiento, Uri Imagen_Uri) {
        mInteractor.performUploadCouponImage(Storage_Reference, Id_Establecimiento, Imagen_Uri);
    }

    @Override
    public void GetEstablishmentInfo(Context context) {
        mInteractor.performGetEstablishmentInfo(context);
    }

    @Override
    public void onSuccessSaveCoupon() {
        mView.onSaveCouponSuccessful();
    }

    @Override
    public void onFailureSaveCoupon() {
        mView.onSaveCouponFailure();
    }

    @Override
    public void onSuccessUploadCouponImage(String Url_Imagen) {
        mView.onUploadCouponImageSuccessful(Url_Imagen);
    }

    @Override
    public void onFailureUploadCouponImage() {
        mView.onUploadCouponImageFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String Id_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(Id_Establecimiento);
    }
}

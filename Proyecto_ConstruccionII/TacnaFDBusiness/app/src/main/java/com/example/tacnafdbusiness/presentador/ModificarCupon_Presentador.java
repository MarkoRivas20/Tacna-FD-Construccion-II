package com.example.tacnafdbusiness.presentador;

import android.net.Uri;

import com.example.tacnafdbusiness.interactor.ModificarCupon_Interactor;
import com.example.tacnafdbusiness.interfaces.ModificarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class ModificarCupon_Presentador implements ModificarCupon.Presenter, ModificarCupon.onOperationListener {

    private ModificarCupon.View mView;
    private ModificarCupon_Interactor mInteractor;

    public ModificarCupon_Presentador(ModificarCupon.View mView) {
        this.mView = mView;
        mInteractor=new ModificarCupon_Interactor(this);
    }

    @Override
    public void UpdateCouponData(DatabaseReference reference, Cupon_Modelo cupon_modelo) {
        mInteractor.performUpdateCouponData(reference, cupon_modelo);
    }

    @Override
    public void UpdateCouponImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Imagen_Actual, String Id_Establecimiento, String Id_Cupon, Uri Imagen_Uri) {
        mInteractor.performUpdateCouponImage(Storage_Reference, Database_Reference, Url_Imagen_Actual, Id_Establecimiento, Id_Cupon, Imagen_Uri);
    }

    @Override
    public void GetCouponData(DatabaseReference Database_Reference, String Id_Cupon) {
        mInteractor.performGetCouponData(Database_Reference, Id_Cupon);
    }

    @Override
    public void onSuccessUpdateCouponData() {
        mView.onUpdateCouponDataSuccessful();
    }

    @Override
    public void onFailureUpdateCouponData() {
        mView.onUpdateCouponDataFailure();
    }

    @Override
    public void onSuccessUpdateCouponImage(String Url_Imagen) {
        mView.onUpdateCouponImageSuccessful(Url_Imagen);
    }

    @Override
    public void onFailureUpdateCouponImage() {
        mView.onUpdateCouponImageFailure();
    }

    @Override
    public void onSuccessGetCouponData(Cupon_Modelo cupon_modelo) {
        mView.onGetCouponDataSuccessful(cupon_modelo);
    }

    @Override
    public void onFailureGetCouponData() {
        mView.onGetCouponDataFailure();
    }
}

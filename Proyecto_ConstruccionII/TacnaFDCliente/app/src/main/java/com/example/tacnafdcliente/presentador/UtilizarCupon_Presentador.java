package com.example.tacnafdcliente.presentador;


import android.content.Context;

import com.example.tacnafdcliente.interactor.UtilizarCupon_Interactor;
import com.example.tacnafdcliente.interfaces.UtilizarCupon;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;

public class UtilizarCupon_Presentador implements UtilizarCupon.Presenter, UtilizarCupon.onOperationListener {

    private UtilizarCupon.View mView;
    private UtilizarCupon_Interactor mInteractor;

    public UtilizarCupon_Presentador(UtilizarCupon.View mView) {
        this.mView = mView;
        mInteractor=new UtilizarCupon_Interactor(this);
    }

    @Override
    public void GetCouponInfo(DatabaseReference Database_Reference, String ID_Cupon) {
        mInteractor.performGetCouponInfo(Database_Reference, ID_Cupon);
    }

    @Override
    public void GetEstablishmentByID(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetEstablishmentByID(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void SaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento) {
        mInteractor.performSaveEstablishmentInfo(Contexto, ID_Establecimiento, Nombre_Establecimiento);
    }

    @Override
    public void SaveCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento) {
        mInteractor.performSaveCouponInfo(Contexto, ID_Cupon, ID_Cupon_Usuario, Descuento);
    }

    @Override
    public void onSuccessGetCouponInfo(Cupon_Modelo Cupon) {
        mView.onGetCouponInfoSuccessful(Cupon);
    }

    @Override
    public void onFailureGetCouponInfo() {
        mView.onGetCouponInfoFailure();
    }

    @Override
    public void onSuccessGetEstablishmentByID(Establecimiento_Modelo Establecimiento) {
        mView.onGetEstablishmentByIDSuccessful(Establecimiento);
    }

    @Override
    public void onFailureGetEstablishmentByID() {
        mView.onGetEstablishmentByIDFailure();
    }
}

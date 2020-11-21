package com.example.tacnafdcliente.presentador;


import android.content.Context;

import com.example.tacnafdcliente.interactor.ListarMiCupon_Interactor;
import com.example.tacnafdcliente.interfaces.ListarMiCupon;
import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarMiCupon_Presentador implements ListarMiCupon.Presenter, ListarMiCupon.onOperationListener {

    private ListarMiCupon.View mView;
    private ListarMiCupon_Interactor mInteractor;

    public ListarMiCupon_Presentador(ListarMiCupon.View mView) {
        this.mView = mView;
        mInteractor=new ListarMiCupon_Interactor(this);
    }

    @Override
    public void GetNumberOfCoupons(Context Contexto) {
        mInteractor.performGetNumberOfCoupons(Contexto);
    }

    @Override
    public void GetCouponUser(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performGetCouponUser(Database_Reference, ID_Usuario);
    }

    @Override
    public void SearchEstablishmentName(DatabaseReference Database_Reference, ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {
        mInteractor.performSearchEstablishmentName(Database_Reference, Cupones_Usuario);
    }

    @Override
    public void SearchCouponInfo(DatabaseReference Database_Reference, ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {
        mInteractor.performSearchCouponInfo(Database_Reference, Cupones_Usuario);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void onSuccessGetNumberOfCoupons(int Numero_Cupones) {
        mView.onGetNumberOfCouponsSuccessful(Numero_Cupones);
    }

    @Override
    public void onSuccessGetCouponUser(ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {
        mView.onGetCouponUserSuccessful(Cupones_Usuario);
    }

    @Override
    public void onFailureGetCouponUser() {
        mView.onGetCouponUserFailure();
    }

    @Override
    public void onSuccessSearchEstablishmentName(ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {
        mView.onSearchEstablishmentNameSuccessful(Cupones_Usuario);
    }

    @Override
    public void onFailureSearchEstablishmentName() {
        mView.onSearchEstablishmentNameFailure();
    }

    @Override
    public void onSuccessSearchCouponInfo(ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {
        mView.onSearchCouponInfoSuccessful(Cupones_Usuario);
    }

    @Override
    public void onFailureSearchCouponInfo() {
        mView.onSearchCouponInfoFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onGetSessionDataSuccessful(ID_Usuario);
    }
}

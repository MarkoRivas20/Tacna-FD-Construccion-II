package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.ListarCupon_Interactor;
import com.example.tacnafdcliente.interfaces.ListarCupon;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarCupon_Presentador implements ListarCupon.Presenter, ListarCupon.onOperationListener {

    private ListarCupon.View mView;
    private ListarCupon_Interactor mInteractor;

    public ListarCupon_Presentador(ListarCupon.View mView) {
        this.mView = mView;
        mInteractor=new ListarCupon_Interactor(this);
    }

    @Override
    public void ListCoupon(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performListCoupon(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessListCoupon(ArrayList<Cupon_Modelo> Cupones, Boolean Existe_Cupon) {
        mView.onListCouponSuccessful(Cupones, Existe_Cupon);
    }

    @Override
    public void onFailureListCoupon() {
        mView.onListCouponFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

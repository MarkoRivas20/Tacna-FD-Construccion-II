package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ListarCupon_Interactor;
import com.example.tacnafdbusiness.interfaces.ListarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
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
    public void ListCoupon(DatabaseReference reference, String Id_Establecimiento) {
        mInteractor.performListCoupon(reference, Id_Establecimiento);
    }

    @Override
    public void GetEstablishmentInfo(Context context) {
        mInteractor.performGetEstablishmentInfo(context);
    }

    @Override
    public void onSuccessListCoupon(ArrayList<Cupon_Modelo> cupon_modelos, Boolean Existe_Cupon) {
        mView.onListCouponSuccessful(cupon_modelos, Existe_Cupon);
    }

    @Override
    public void onFailureListCoupon() {
        mView.onListCouponFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String Id_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(Id_Establecimiento);
    }
}

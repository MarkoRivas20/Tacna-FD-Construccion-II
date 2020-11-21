package com.example.tacnafdcliente.presentador;


import com.example.tacnafdcliente.interactor.UtilizarCupon_Interactor;
import com.example.tacnafdcliente.interfaces.UtilizarCupon;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
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
    public void onSuccessGetCouponInfo(Cupon_Modelo Cupon) {
        mView.onGetCouponInfoSuccessful(Cupon);
    }

    @Override
    public void onFailureGetCouponInfo() {
        mView.onGetCouponInfoFailure();
    }
}

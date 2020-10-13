package com.example.tacnafdbusiness.presentador;

import com.example.tacnafdbusiness.interactor.BuscarEmail_Interactor;
import com.example.tacnafdbusiness.interactor.Login_Interactor;
import com.example.tacnafdbusiness.interfaces.BuscarEmail;
import com.example.tacnafdbusiness.interfaces.Login;
import com.google.firebase.database.DatabaseReference;

public class BuscarEmail_Presentador implements BuscarEmail.Presenter, BuscarEmail.onOperationListener {

    private BuscarEmail.View mView;
    private BuscarEmail_Interactor mInteractor;

    public BuscarEmail_Presentador(BuscarEmail.View mView) {
        this.mView = mView;
        mInteractor=new BuscarEmail_Interactor(this);
    }

    @Override
    public void SearchEmail(DatabaseReference reference, String correo_electronico, int codigo) {
        mInteractor.performSearchEmail(reference,correo_electronico,codigo);
    }

    @Override
    public void onSuccess() {
        mView.onSearchEmailSuccessful();
    }

    @Override
    public void onFailure() {
        mView.onSearchEmailFailure();
    }

    @Override
    public void onNotFoundEMail() {
        mView.onNotFoundEmailFailure();
    }
}

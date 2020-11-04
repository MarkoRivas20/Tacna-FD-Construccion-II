package com.example.tacnafdbusiness.presentador;

import com.example.tacnafdbusiness.interactor.RecuperarContrasena_Interactor;
import com.example.tacnafdbusiness.interfaces.RecuperarContrasena;
import com.google.firebase.database.DatabaseReference;

public class RecuperarContrasena_Presentador implements RecuperarContrasena.Presenter, RecuperarContrasena.onOperationListener {

    private RecuperarContrasena.View mView;
    private RecuperarContrasena_Interactor mInteractor;

    public RecuperarContrasena_Presentador(RecuperarContrasena.View mView) {
        this.mView = mView;
        mInteractor=new RecuperarContrasena_Interactor(this);
    }

    @Override
    public void RestorePassword(DatabaseReference Database_Reference, String Correo_Electronico, String Nueva_Contrasena) {
        mInteractor.performRestorePassword(Database_Reference,Correo_Electronico,Nueva_Contrasena);
    }

    @Override
    public void onSuccess() {
        mView.onRestorePasswordSuccessful();
    }

    @Override
    public void onFailure() {
        mView.onRestorePasswordFailure();
    }
}

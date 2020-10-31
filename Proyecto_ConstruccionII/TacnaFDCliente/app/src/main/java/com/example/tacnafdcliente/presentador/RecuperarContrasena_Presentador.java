package com.example.tacnafdcliente.presentador;

import com.example.tacnafdcliente.interactor.RecuperarContrasena_Interactor;
import com.example.tacnafdcliente.interfaces.RecuperarContrasena;
import com.google.firebase.database.DatabaseReference;

public class RecuperarContrasena_Presentador implements RecuperarContrasena.Presenter, RecuperarContrasena.onOperationListener {

    private RecuperarContrasena.View mView;
    private RecuperarContrasena_Interactor mInteractor;

    public RecuperarContrasena_Presentador(RecuperarContrasena.View mView) {
        this.mView = mView;
        mInteractor=new RecuperarContrasena_Interactor(this);
    }

    @Override
    public void RestorePassword(DatabaseReference reference, String correo_electronico, String nueva_contrasena) {
        mInteractor.performRestorePassword(reference,correo_electronico,nueva_contrasena);
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

package com.example.tacnafdcliente.presentador;

import com.example.tacnafdcliente.interactor.RegistroUsuario_Interactor;
import com.example.tacnafdcliente.interfaces.RegistrarUsuario;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public class RegistroUsuario_Presentador implements RegistrarUsuario.Presenter, RegistrarUsuario.onOperationListener {

    private RegistrarUsuario.View mView;
    private RegistroUsuario_Interactor mInteractor;

    public RegistroUsuario_Presentador(RegistrarUsuario.View mView) {
        this.mView = mView;
        mInteractor=new RegistroUsuario_Interactor(this);
    }

    @Override
    public void CreateNewUser(DatabaseReference reference, Usuario_Modelo usuario_modelo) {
        mInteractor.performCreateUser(reference,usuario_modelo);
    }

    @Override
    public void onSuccess() {
        mView.onCreateUserSuccessful();
    }

    @Override
    public void onFailure() {
        mView.onCreateUserFailure();
    }

    @Override
    public void onUsedMail() {
        mView.onCreateUserFailureUsedMail();
    }
}

package com.example.tacnafdbusiness.presentador;

import com.example.tacnafdbusiness.interactor.RegistroUsuario_Interactor;
import com.example.tacnafdbusiness.interfaces.RegistrarUsuario;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public class RegistroUsuario_Presentador implements RegistrarUsuario.Presenter, RegistrarUsuario.onOperationListener {

    private RegistrarUsuario.View mView;
    private RegistroUsuario_Interactor mInteractor;

    public RegistroUsuario_Presentador(RegistrarUsuario.View mView) {
        this.mView = mView;
        mInteractor=new RegistroUsuario_Interactor(this);
    }

    @Override
    public void CreateNewUser(DatabaseReference Database_Reference, Usuario_Modelo Usuario) {
        mInteractor.performCreateUser(Database_Reference,Usuario);
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

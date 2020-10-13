package com.example.tacnafdbusiness.presentador;

import com.example.tacnafdbusiness.interactor.RegistroUsuario_Interactor;
import com.example.tacnafdbusiness.interfaces.Registro;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public class RegistroUsuario_Presentador implements Registro.Presenter, Registro.onOperationListener {

    private Registro.View mView;
    private RegistroUsuario_Interactor mInteractor;

    public RegistroUsuario_Presentador(Registro.View mView) {
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

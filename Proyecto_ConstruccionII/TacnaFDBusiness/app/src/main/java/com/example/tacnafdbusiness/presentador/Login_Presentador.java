package com.example.tacnafdbusiness.presentador;

import com.example.tacnafdbusiness.interactor.Login_Interactor;
import com.example.tacnafdbusiness.interactor.RegistroUsuario_Interactor;
import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public class Login_Presentador implements Login.Presenter, Login.onOperationListener {

    private Login.View mView;
    private Login_Interactor mInteractor;

    public Login_Presentador(Login.View mView) {
        this.mView = mView;
        mInteractor=new Login_Interactor(this);
    }

    @Override
    public void LogIn(DatabaseReference reference, String correo_electronico, String contrasena) {
        mInteractor.performLogIn(reference,correo_electronico,contrasena);
    }

    @Override
    public void onSuccess() {
        mView.onLogInSuccessful();
    }

    @Override
    public void onFailure() {
        mView.onLogInFailure();
    }
}

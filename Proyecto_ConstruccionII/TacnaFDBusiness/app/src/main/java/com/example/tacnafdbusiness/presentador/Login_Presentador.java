package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.Login_Interactor;
import com.example.tacnafdbusiness.interfaces.Login;
import com.google.firebase.database.DatabaseReference;

public class Login_Presentador implements Login.Presenter, Login.onOperationListener {

    private Login.View mView;
    private Login_Interactor mInteractor;

    public Login_Presentador(Login.View mView) {
        this.mView = mView;
        mInteractor=new Login_Interactor(this);
    }

    @Override
    public void LogIn(DatabaseReference Database_Reference, String Correo_Electronico, String Contrasena) {
        mInteractor.performLogIn(Database_Reference,Correo_Electronico,Contrasena);
    }

    @Override
    public void SaveSession(Context Contexto, String Correo_Electronico, String Nombre_Usuario, String ID_Usuario) {
        mInteractor.performSaveSession(Contexto, Correo_Electronico, Nombre_Usuario, ID_Usuario);
    }

    @Override
    public void CheckSession(Context Contexto) {
        mInteractor.performCheckSession(Contexto);
    }

    @Override
    public void onSuccessCheck() {
        mView.onSuccessfulCheck();
    }

    @Override
    public void onSuccess(String Nombre_Usuario, String ID_Usuario) {
        mView.onLogInSuccessful(Nombre_Usuario, ID_Usuario);
    }

    @Override
    public void onFailure() {
        mView.onLogInFailure();
    }
}

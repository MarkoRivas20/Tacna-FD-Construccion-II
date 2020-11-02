package com.example.tacnafddelivery.presentador;

import android.content.Context;

import com.example.tacnafddelivery.interactor.Login_Interactor;
import com.example.tacnafddelivery.interfaces.Login;
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
    public void SaveSession(Context context, String correo_electronico, String nombre_usuario, String id_usuario, String Url_Foto) {
        mInteractor.performSaveSession(context, correo_electronico, nombre_usuario, id_usuario, Url_Foto);
    }

    @Override
    public void CheckSession(Context context) {
        mInteractor.performCheckSession(context);
    }

    @Override
    public void onSuccessCheck() {
        mView.onSuccessfulCheck();
    }

    @Override
    public void onSuccess(String nombre_usuario, String id_usuario, String Url_Foto) {
        mView.onLogInSuccessful(nombre_usuario, id_usuario, Url_Foto);
    }

    @Override
    public void onFailure() {
        mView.onLogInFailure();
    }
}

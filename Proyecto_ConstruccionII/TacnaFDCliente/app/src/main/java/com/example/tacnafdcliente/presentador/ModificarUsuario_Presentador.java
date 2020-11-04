package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.ModificarUsuario_Interactor;
import com.example.tacnafdcliente.interfaces.ModificarUsuario;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;

public class ModificarUsuario_Presentador implements ModificarUsuario.Presenter, ModificarUsuario.onOperationListener {

    private ModificarUsuario.View mView;
    private ModificarUsuario_Interactor mInteractor;

    public ModificarUsuario_Presentador(ModificarUsuario.View mView) {
        this.mView = mView;
        mInteractor=new ModificarUsuario_Interactor(this);
    }

    @Override
    public void UpdateUser(DatabaseReference Database_Reference, Usuario_Modelo Usuario) {
        mInteractor.performUpdateUser(Database_Reference, Usuario);
    }

    @Override
    public void ShowUserData(DatabaseReference Database_Reference, String Correo_Electronico) {
        mInteractor.performShowUserData(Database_Reference, Correo_Electronico);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void onSuccessUpdateUser() {
        mView.onUpdateUserSuccessful();
    }

    @Override
    public void onFailureUpdateUser() {
        mView.onUpdateUserFailure();
    }

    @Override
    public void onSuccessShowUserData(Usuario_Modelo Usuario) {
        mView.onShowUserDataSuccessful(Usuario);
    }

    @Override
    public void onFailureShowUserData() {
        mView.onShowUserDataFailure();
    }

    @Override
    public void onSuccessGetSessionData(String Correo_Electronico) {
        mView.onGetSessionDataSuccessful(Correo_Electronico);
    }
}

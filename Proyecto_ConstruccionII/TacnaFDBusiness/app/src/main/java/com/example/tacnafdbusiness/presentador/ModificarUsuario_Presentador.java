package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ModificarUsuario_Interactor;
import com.example.tacnafdbusiness.interfaces.ModificarUsuario;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
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
    public void onSuccess() {
        mView.onUpdateUserSuccessful();
    }

    @Override
    public void onFailure() {
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
    public void onSuccess(String Correo_Electronico) {
        mView.onSessionDataSuccessful(Correo_Electronico);
    }
}

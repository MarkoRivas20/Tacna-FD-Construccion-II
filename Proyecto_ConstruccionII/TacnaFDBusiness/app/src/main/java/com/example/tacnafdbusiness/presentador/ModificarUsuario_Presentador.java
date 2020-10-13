package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.Login_Interactor;
import com.example.tacnafdbusiness.interactor.ModificarUsuario_Interactor;
import com.example.tacnafdbusiness.interfaces.Login;
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
    public void UpdateUser(DatabaseReference reference, Usuario_Modelo usuario_modelo) {
        mInteractor.performUpdateUser(reference, usuario_modelo);
    }

    @Override
    public void ShowUserData(DatabaseReference reference, String correo_electronico) {
        mInteractor.performShowUserData(reference, correo_electronico);
    }

    @Override
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
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
    public void onSuccessShowUserData(Usuario_Modelo usuario_modelo) {
        mView.onShowUserDataSuccessful(usuario_modelo);
    }

    @Override
    public void onFailureShowUserData() {
        mView.onShowUserDataFailure();
    }

    @Override
    public void onSuccess(String correo_electronico) {
        mView.onSessionDataSuccessful(correo_electronico);
    }
}

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
    public void UpdateUserData(DatabaseReference reference, Usuario_Modelo usuario_modelo) {
        mInteractor.performUpdateUserData(reference, usuario_modelo);
    }

    @Override
    public void ShowUserData(DatabaseReference reference, String correo_electronico) {
        mInteractor.performShowUserData(reference, correo_electronico);
    }

    @Override
    public void SaveSession(Context context, String nombre_usuario) {
        mInteractor.performSaveSession(context, nombre_usuario);
    }

    @Override
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
    }

    @Override
    public void onSuccessUpdateUserData() {
        mView.onUpdateUserDataSuccessful();
    }

    @Override
    public void onFailureUpdateUserData() {
        mView.onUpdateUserDataFailure();
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
    public void onSuccessGetSessionData(String correo_electronico) {
        mView.onSessionDataSuccessful(correo_electronico);
    }

    @Override
    public void onSuccessSaveSession() {
        mView.onSaveSessionSuccessful();
    }
}

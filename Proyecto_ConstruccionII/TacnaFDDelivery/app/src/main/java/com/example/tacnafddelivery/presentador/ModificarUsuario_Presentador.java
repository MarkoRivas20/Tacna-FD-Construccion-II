package com.example.tacnafddelivery.presentador;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafddelivery.interactor.ModificarUsuario_Interactor;
import com.example.tacnafddelivery.interfaces.ModificarUsuario;
import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

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
    public void UpdateUserPhoto(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String Id_Usuario, Uri Foto_Uri) {
        mInteractor.performUpdateUserPhoto(Storage_Reference, Database_Reference, Url_Logo_Actual, Id_Usuario, Foto_Uri);
    }

    @Override
    public void ShowUserData(DatabaseReference reference, String correo_electronico) {
        mInteractor.performShowUserData(reference, correo_electronico);
    }

    @Override
    public void SaveSession(Context context, String nombre_usuario, String Url_Foto) {
        mInteractor.performSaveSession(context, nombre_usuario, Url_Foto);
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
    public void onSuccessUpdateUserPhoto(String Url_Foto) {
        mView.onUpdateUserPhotoSuccessful(Url_Foto);
    }

    @Override
    public void onFailureUpdateUserPhoto() {
        mView.onUpdateUserPhotoFailure();
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

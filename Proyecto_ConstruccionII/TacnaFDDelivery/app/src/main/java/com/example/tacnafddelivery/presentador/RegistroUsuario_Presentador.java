package com.example.tacnafddelivery.presentador;

import android.net.Uri;

import com.example.tacnafddelivery.interactor.RegistroUsuario_Interactor;
import com.example.tacnafddelivery.interfaces.RegistrarUsuario;
import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class RegistroUsuario_Presentador implements RegistrarUsuario.Presenter, RegistrarUsuario.onOperationListener {

    private RegistrarUsuario.View mView;
    private RegistroUsuario_Interactor mInteractor;

    public RegistroUsuario_Presentador(RegistrarUsuario.View mView) {
        this.mView = mView;
        mInteractor=new RegistroUsuario_Interactor(this);
    }

    @Override
    public void CreateNewUser(DatabaseReference reference, Usuario_Modelo usuario_modelo) {
        mInteractor.performCreateUser(reference,usuario_modelo);
    }

    @Override
    public void UploadPhoto(StorageReference reference, String Id_Usuario, Uri Image_Uri) {
        mInteractor.performUploadPhoto(reference, Id_Usuario, Image_Uri);
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

    @Override
    public void onSuccessUploadPhoto(String Url_Foto) {
        mView.onUploadPhotoSuccessful(Url_Foto);
    }

    @Override
    public void onFailureUploadPhoto() {
        mView.onUploadPhotoFailure();
    }
}

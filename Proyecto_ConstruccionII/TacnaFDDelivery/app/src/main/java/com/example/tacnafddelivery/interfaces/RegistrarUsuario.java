package com.example.tacnafddelivery.interfaces;

import android.net.Uri;

import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface RegistrarUsuario {

    interface View{
        void onCreateUserSuccessful();
        void onCreateUserFailure();
        void onCreateUserFailureUsedMail();
        void onUploadPhotoSuccessful(String Url_Foto);
        void onUploadPhotoFailure();
    }

    interface Presenter{
        void CreateNewUser(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void UploadPhoto(StorageReference reference, String Id_Usuario, Uri Image_Uri);
    }

    interface Interactor{
        void performCreateUser(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void performUploadPhoto(StorageReference reference, String Id_Usuario, Uri Image_Uri);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onUsedMail();
        void onSuccessUploadPhoto(String Url_Foto);
        void onFailureUploadPhoto();
    }

}

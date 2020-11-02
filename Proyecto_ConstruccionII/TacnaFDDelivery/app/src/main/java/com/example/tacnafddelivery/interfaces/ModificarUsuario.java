package com.example.tacnafddelivery.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface ModificarUsuario {

    interface View{
        void onUpdateUserDataSuccessful();
        void onUpdateUserDataFailure();
        void onUpdateUserPhotoSuccessful(String Url_Foto);
        void onUpdateUserPhotoFailure();
        void onShowUserDataSuccessful(Usuario_Modelo usuario_modelo);
        void onShowUserDataFailure();
        void onSessionDataSuccessful(String correo_electronico);
        void onSaveSessionSuccessful();
    }

    interface Presenter{
        void UpdateUserData(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void UpdateUserPhoto(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String Id_Usuario, Uri Foto_Uri);
        void ShowUserData(DatabaseReference reference, String correo_electronico);
        void SaveSession(Context context, String nombre_usuario, String Url_Foto);
        void GetSessionData(Context context);
    }

    interface Interactor{
        void performUpdateUserData(DatabaseReference reference, Usuario_Modelo usuario_modelo);
        void performUpdateUserPhoto(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String Id_Usuario, Uri Foto_Uri);
        void performShowUserData(DatabaseReference reference, String correo_electronico);
        void performSaveSession(Context context, String nombre_usuario, String Url_Foto);
        void performGetSessionData(Context context);
    }

    interface onOperationListener{
        void onSuccessUpdateUserData();
        void onFailureUpdateUserData();
        void onSuccessUpdateUserPhoto(String Url_Foto);
        void onFailureUpdateUserPhoto();
        void onSuccessShowUserData(Usuario_Modelo usuario_modelo);
        void onFailureShowUserData();
        void onSuccessGetSessionData(String correo_electronico);
        void onSuccessSaveSession();
    }

}

package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface RegistrarEstablecimiento {

    interface View{
        void onCreateEstablishmentSuccessful();
        void onCreateEstablishmentFailure();
        void onUploadLogoSuccessful(String Url);
        void onUploadLogoFailure();
        void onUploadDocumentSuccessful(String Url);
        void onUploadDocumentFailure();
        void onSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void CreateNewEstablishment(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo);
        void UploadLogo(StorageReference reference, String Id_Establecimiento, Uri Image_Uri);
        void UploadDocument(StorageReference reference, String Id_Establecimiento, Uri Document_Uri);
        void GetSessionData(Context context);
    }

    interface Interactor{
        void performCreateEstablishment(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo);
        void performUploadLogo(StorageReference reference, String Id_Establecimiento, Uri Image_Uri);
        void performUploadDocument(StorageReference reference, String Id_Establecimiento, Uri Document_Uri);
        void performGetSessionData(Context context);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onSuccessUploadLogo(String Url);
        void onFailureUploadLogo();
        void onSuccessUploadDocument(String Url);
        void onFailureUploadDocument();
        void onSuccessGetSessionData(String ID_Usuario);
    }
}

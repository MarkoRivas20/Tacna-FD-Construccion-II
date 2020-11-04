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
        void CreateNewEstablishment(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento);
        void UploadLogo(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void UploadDocument(StorageReference Storage_Reference, String ID_Establecimiento, Uri Documento_Uri);
        void GetSessionData(Context Contexto);
    }

    interface Interactor{
        void performCreateEstablishment(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento);
        void performUploadLogo(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void performUploadDocument(StorageReference Storage_Reference, String ID_Establecimiento, Uri Documento_Uri);
        void performGetSessionData(Context Contexto);
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

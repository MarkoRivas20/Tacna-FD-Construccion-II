package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public interface ModificarEstablecimiento {

    interface View{
        void onUpdateEstablismentDataFailure();
        void onUpdateEstablismentDataSuccessful();
        void onUpdateEstablismentLogoFailure();
        void onUpdateEstablismentLogoSuccessful(String Url_Logo);
        void onUpdateEstablismentDocumentFailure();
        void onUpdateEstablismentDocumentSuccessful(String Url_Documento);
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento,String Url_Logo, String Url_Documento);
        void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetEstablishmentDataFailure();
        void onGetSessionDataSuccessful(String ID_Usuario);
        void onUpdateEstablishmentInfoSuccessful();
    }

    interface Presenter{
        void UpdateEstablismentData(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento);
        void UpdateEstablismentLogo(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String ID_Establecimiento, Uri Logo_Uri);
        void UpdateEstablismentDocument(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Document_Actual, String ID_Establecimiento, Uri Documento_Uri);
        void GetEstablishmentInfo(Context Contexto);
        void GetEstablishmentData(DatabaseReference Database_Reference, String ID_Establecimiento);
        void UpdateEstablishmentInfo(Context Contexto, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void GetSessionData(Context Contexto);
    }

    interface Interactor{
        void performUpdateEstablismentData(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento);
        void performUpdateEstablismentLogo(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String ID_Establecimiento, Uri Logo_Uri);
        void performUpdateEstablismentDocument(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Document_Actual, String ID_Establecimiento, Uri Documento_Uri);
        void performGetEstablishmentInfo(Context Contexto);
        void performGetEstablishmentData(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performUpdateEstablishmentInfo(Context Contexto,String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void performGetSessionData(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessUpdateEstablismentData();
        void onFailureUpdateEstablismentData();
        void onSuccessUpdateEstablismentLogo(String Url_Logo);
        void onFailureUpdateEstablismentLogo();
        void onSuccessUpdateEstablismentDocument(String Url_Documento);
        void onFailureUpdateEstablismentDocument();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento, String Url_Logo, String Url_Documento);
        void onSuccessGetEstablishmentData(Establecimiento_Modelo Establecimiento);
        void onFailureGetEstablishmentData();
        void onSuccessGetSessionData(String ID_Usuario);
        void onSuccessUpdateEstablishmentInfo();
    }

}

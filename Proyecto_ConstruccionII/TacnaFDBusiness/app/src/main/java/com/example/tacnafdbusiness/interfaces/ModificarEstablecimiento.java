package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
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
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento,String Url_Logo, String Url_Documento);
        void onGetEstablishmentDataSuccessful(Establecimiento_Modelo establecimiento);
        void onGetEstablishmentDataFailure();
        void onGetSessionDataSuccessful(String Id_Usuario);
        void onUpdateEstablishmentInfoSuccessful();
    }

    interface Presenter{
        void UpdateEstablismentData(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo);
        void UpdateEstablismentLogo(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String Id_Establecimiento, Uri Logo_Uri);
        void UpdateEstablismentDocument(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Document_Actual, String Id_Establecimiento, Uri Documento_Uri);
        void GetEstablishmentInfo(Context context);
        void GetEstablishmentData(DatabaseReference reference, String Id_Establecimiento);
        void UpdateEstablishmentInfo(Context context, String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void GetSessionData(Context context);
    }

    interface Interactor{
        void performUpdateEstablismentData(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo);
        void performUpdateEstablismentLogo(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Logo_Actual, String Id_Establecimiento, Uri Logo_Uri);
        void performUpdateEstablismentDocument(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Document_Actual, String Id_Establecimiento, Uri Documento_Uri);
        void performGetEstablishmentInfo(Context context);
        void performGetEstablishmentData(DatabaseReference reference, String Id_Establecimiento);
        void performUpdateEstablishmentInfo(Context context,String Nombre_Establecimiento, String Url_Logo, String Url_Documento);
        void performGetSessionData(Context context);
    }

    interface onOperationListener{
        void onSuccessUpdateEstablismentData();
        void onFailureUpdateEstablismentData();
        void onSuccessUpdateEstablismentLogo(String Url_Logo);
        void onFailureUpdateEstablismentLogo();
        void onSuccessUpdateEstablismentDocument(String Url_Documento);
        void onFailureUpdateEstablismentDocument();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento, String Url_Logo, String Url_Documento);
        void onSuccessGetEstablishmentData(Establecimiento_Modelo establecimiento);
        void onFailureGetEstablishmentData();
        void onSuccessGetSessionData(String Id_Usuario);
        void onSuccessUpdateEstablishmentInfo();
    }

}

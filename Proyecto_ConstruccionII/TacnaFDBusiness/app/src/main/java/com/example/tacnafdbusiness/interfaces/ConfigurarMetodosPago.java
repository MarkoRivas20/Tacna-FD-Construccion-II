package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface ConfigurarMetodosPago {

    interface View{
        void onGetPaymentsMethodsSuccessful(Establecimiento_Modelo Establecimiento);
        void onGetPaymentsMethodsFailure();
        void onUpdatePaymentsMethodsSuccessful();
        void onUpdatePaymentsMethodsFailure();
        void onUpdateQRImageSuccessful(String Url_Qr);
        void onUpdateQRImageFailure();
        void onDeleteQRImageSuccessful();
        void onDeleteQRImageFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void GetPaymentsMethods(DatabaseReference Database_Reference, String ID_Establecimiento);
        void UpdatePaymentsMethods(DatabaseReference Database_Reference, String ID_Establecimiento, String Codigo_Paypal, String Codigo_Culqi, String Url_Qr);
        void UpdateQRImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void DeleteQRImage(String Url_Qr);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performGetPaymentsMethods(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performUpdatePaymentsMethods(DatabaseReference Database_Reference, String ID_Establecimiento, String Codigo_Paypal, String Codigo_Culqi, String Url_Qr);
        void performUpdateQRImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void performDeleteQRImage(String Url_Qr);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetPaymentsMethods(Establecimiento_Modelo Establecimiento);
        void onFailureGetPaymentsMethods();
        void onSuccessUpdatePaymentsMethods();
        void onFailureUpdatePaymentsMethods();
        void onSuccessUpdateQRImage(String Url_Qr);
        void onFailureUpdateQRImage();
        void onSuccessDeleteQRImage();
        void onFailureDeleteQRImage();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

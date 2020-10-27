package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface ConfigurarMetodosPago {

    interface View{
        void onGetPaymentsMethodsSuccessful(Establecimiento_Modelo establecimiento_modelo);
        void onGetPaymentsMethodsFailure();
        void onUpdatePaymentsMethodsSuccessful();
        void onUpdatePaymentsMethodsFailure();
        void onUpdateQRImageSuccessful(String Url_Qr);
        void onUpdateQRImageFailure();
        void onDeleteQRImageSuccessful();
        void onDeleteQRImageFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
    }

    interface Presenter{
        void GetPaymentsMethods(DatabaseReference reference, String Id_Establecimiento);
        void UpdatePaymentsMethods(DatabaseReference reference, String Id_Establecimiento, String Codigo_Paypal, String Codigo_Culqi, String Url_Qr);
        void UpdateQRImage(StorageReference reference, String Id_Establecimiento, Uri Imagen_Uri);
        void DeleteQRImage(String Url_Qr);
        void GetEstablishmentInfo(Context context);
    }

    interface Interactor{
        void performGetPaymentsMethods(DatabaseReference reference, String Id_Establecimiento);
        void performUpdatePaymentsMethods(DatabaseReference reference, String Id_Establecimiento, String Codigo_Paypal, String Codigo_Culqi, String Url_Qr);
        void performUpdateQRImage(StorageReference reference, String Id_Establecimiento, Uri Imagen_Uri);
        void performDeleteQRImage(String Url_Qr);
        void performGetEstablishmentInfo(Context context);
    }

    interface onOperationListener{
        void onSuccessGetPaymentsMethods(Establecimiento_Modelo establecimiento_modelo);
        void onFailureGetPaymentsMethods();
        void onSuccessUpdatePaymentsMethods();
        void onFailureUpdatePaymentsMethods();
        void onSuccessUpdateQRImage(String Url_Qr);
        void onFailureUpdateQRImage();
        void onSuccessDeleteQRImage();
        void onFailureDeleteQRImage();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
    }
}

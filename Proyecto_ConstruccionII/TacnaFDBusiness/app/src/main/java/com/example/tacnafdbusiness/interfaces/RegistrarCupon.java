package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface RegistrarCupon {

    interface View{
        void onSaveCouponSuccessful();
        void onSaveCouponFailure();
        void onUploadCouponImageSuccessful(String Url_Imagen);
        void onUploadCouponImageFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void SaveCoupon(DatabaseReference Database_Reference, Cupon_Modelo Cupon);
        void UploadCouponImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performSaveCoupon(DatabaseReference Database_Reference, Cupon_Modelo Cupon);
        void performUploadCouponImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessSaveCoupon();
        void onFailureSaveCoupon();
        void onSuccessUploadCouponImage(String Url_Imagen);
        void onFailureUploadCouponImage();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

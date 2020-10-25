package com.example.tacnafdbusiness.interfaces;

import android.net.Uri;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface ModificarCupon {

    interface View{
        void onUpdateCouponDataFailure();
        void onUpdateCouponDataSuccessful();
        void onUpdateCouponImageFailure();
        void onUpdateCouponImageSuccessful(String Url_Imagen);
        void onGetCouponDataSuccessful(Cupon_Modelo cupon_modelo);
        void onGetCouponDataFailure();
    }

    interface Presenter{
        void UpdateCouponData(DatabaseReference reference, Cupon_Modelo cupon_modelo);
        void UpdateCouponImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Imagen_Actual, String Id_Establecimiento, String Id_Cupon, Uri Imagen_Uri);
        void GetCouponData(DatabaseReference Database_Reference, String Id_Cupon);
    }

    interface Interactor{
        void performUpdateCouponData(DatabaseReference reference, Cupon_Modelo cupon_modelo);
        void performUpdateCouponImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, String Url_Imagen_Actual, String Id_Establecimiento, String Id_Cupon, Uri Imagen_Uri);
        void performGetCouponData(DatabaseReference Database_Reference, String Id_Cupon);
    }

    interface onOperationListener{
        void onSuccessUpdateCouponData();
        void onFailureUpdateCouponData();
        void onSuccessUpdateCouponImage(String Url_Imagen);
        void onFailureUpdateCouponImage();
        void onSuccessGetCouponData(Cupon_Modelo cupon_modelo);
        void onFailureGetCouponData();
    }
}

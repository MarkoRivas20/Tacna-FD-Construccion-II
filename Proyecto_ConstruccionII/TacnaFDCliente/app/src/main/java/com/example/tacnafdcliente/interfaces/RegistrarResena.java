package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface RegistrarResena {

    interface View{
        void onGetReviewsSuccessful(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena);
        void onGetReviewsFailure();
        void onSearchClientNameSuccessful(ArrayList<Resena_Modelo> Resenas);
        void onSearchClientNameFailure();
        void onSaveReviewSuccessful();
        void onSaveReviewFailure();
        void onUpdateEstablishmentSuccessful();
        void onUpdateEstablishmentFailure();
        void onGetCurrentReviewsSuccessful(ArrayList<Resena_Modelo> Resenas);
        void onGetCurrentReviewsFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
        void onGetSessionDataSuccessful(String ID_Usuario);
        void onGetNumberOfCouponsSuccessful(int Numero_Cupones);
        void onGetActiveCouponsSuccessful(ArrayList<Cupon_Modelo> Cupones);
        void onGetActiveCouponsFailure();
        void onSaveCouponUserSuccessful();
        void onSaveCouponUserFailure();
    }

    interface Presenter{
        void GetReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void SearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas);
        void SaveReview(DatabaseReference Database_Reference, Resena_Modelo Resena);
        void UpdateEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento, Double Calificacion, int Total_Resenas);
        void GetCurrentReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetEstablishmentInfo(Context Contexto);
        void GetSessionData(Context Contexto);
        void SaveNumberOfCoupons(Context Contexto, int Numero_Cupones);
        void GetNumberOfCoupons(Context Contexto);
        void GetActiveCoupons(DatabaseReference Database_Reference);
        void SaveCouponUser(DatabaseReference Database_Reference, CuponUsuario_Modelo Cupon_Usuario);
    }

    interface Interactor{
        void performGetReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performSearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas);
        void performSaveReview(DatabaseReference Database_Reference, Resena_Modelo Resena);
        void performUpdateEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento, Double Calificacion, int Total_Resenas);
        void performGetCurrentReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetEstablishmentInfo(Context Contexto);
        void performGetSessionData(Context Contexto);
        void performSaveNumberOfCoupons(Context Contexto, int Numero_Cupones);
        void performGetNumberOfCoupons(Context Contexto);
        void performGetActiveCoupons(DatabaseReference Database_Reference);
        void performSaveCouponUser(DatabaseReference Database_Reference, CuponUsuario_Modelo Cupon_Usuario);
    }

    interface onOperationListener{
        void onSuccessGetReviews(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena);
        void onFailureGetReviews();
        void onSuccessSearchClientName(ArrayList<Resena_Modelo> Resenas);
        void onFailureSearchClientName();
        void onSuccessSaveReview();
        void onFailureSaveReview();
        void onSuccessUpdateEstablishment();
        void onFailureUpdateEstablishment();
        void onSuccessGetCurrentReviews(ArrayList<Resena_Modelo> Resenas);
        void onFailureGetCurrentReviews();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
        void onSuccessGetSessionData(String ID_Usuario);
        void onSuccessGetNumberOfCoupons(int Numero_Cupones);
        void onSuccessGetActiveCoupons(ArrayList<Cupon_Modelo> Cupones);
        void onFailureGetActiveCoupons();
        void onSuccessSaveCouponUser();
        void onFailureSaveCouponUser();
    }

}

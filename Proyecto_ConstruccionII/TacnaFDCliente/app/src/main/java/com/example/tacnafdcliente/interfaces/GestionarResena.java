package com.example.tacnafdcliente.interfaces;

import android.content.Context;

import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface GestionarResena {

    interface View{
        void onGetReviewsSuccessful(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena);
        void onGetReviewsFailure();
        void onSearchClientNameSuccessful(ArrayList<Resena_Modelo> Resenas);
        void onSearchClientNameFailure();
        void onGetUserReviewSuccessful(Resena_Modelo Resena);
        void onGetUserReviewFailure();
        void onUpdateReviewSuccessful();
        void onUpdateReviewFailure();
        void onDeleteReviewSuccessful();
        void onDeleteReviewFailure();
        void onUpdateEstablishmentSuccessful();
        void onUpdateEstablishmentFailure();
        void onGetCurrentReviewsSuccessful(ArrayList<Resena_Modelo> Resenas);
        void onGetCurrentReviewsFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
        void onGetSessionDataSuccessful(String ID_Usuario);
        void onGetNumberOfCouponsSuccessful(int Numero_Cupones);
        void onGetCouponUserFromUserSuccessful(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onGetCouponUserFromUserFailure();
        void onDeleteCouponUserSuccessful();
        void onDeleteCouponUserFailure();
    }

    interface Presenter{
        void GetReviews(DatabaseReference Database_Reference, String ID_Establecimiento, String ID_Usuario);
        void SearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas);
        void GetUserReview(DatabaseReference Database_Reference, String ID_Establecimiento, String ID_Usuario);
        void UpdateReview(DatabaseReference Database_Reference, Resena_Modelo Resena);
        void DeleteReview(DatabaseReference Database_Reference, String ID_Resena);
        void UpdateEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento, Double Calificacion, int Total_Resenas);
        void GetCurrentReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetEstablishmentInfo(Context Contexto);
        void GetSessionData(Context Contexto);
        void SaveNumberOfCoupons(Context Contexto, int Numero_Cupones);
        void GetNumberOfCoupons(Context Contexto);
        void GetCouponUserFromUser(DatabaseReference Database_Reference, String ID_Usuario);
        void DeleteCouponUser(DatabaseReference Database_Reference, String ID_Cupon_Usuario);

    }

    interface Interactor{
        void performGetReviews(DatabaseReference Database_Reference, String ID_Establecimiento, String ID_Usuario);
        void performSearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas);
        void performGetUserReview(DatabaseReference Database_Reference, String ID_Establecimiento, String ID_Usuario);
        void performUpdateReview(DatabaseReference Database_Reference, Resena_Modelo Resena);
        void performDeleteReview(DatabaseReference Database_Reference, String ID_Resena);
        void performUpdateEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento, Double Calificacion, int Total_Resenas);
        void performGetCurrentReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetEstablishmentInfo(Context Contexto);
        void performGetSessionData(Context Contexto);
        void performSaveNumberOfCoupons(Context Contexto, int Numero_Cupones);
        void performGetNumberOfCoupons(Context Contexto);
        void performGetCouponUserFromUser(DatabaseReference Database_Reference, String ID_Usuario);
        void performDeleteCouponUser(DatabaseReference Database_Reference, String ID_Cupon_Usuario);
    }

    interface onOperationListener{
        void onSuccessGetReviews(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena);
        void onFailureGetReviews();
        void onSuccessSearchClientName(ArrayList<Resena_Modelo> Resenas);
        void onFailureSearchClientName();
        void onSuccessGetUserReview(Resena_Modelo Resena);
        void onFailureGetUserReview();
        void onSuccessUpdateReview();
        void onFailureUpdateReview();
        void onSuccessDeleteReview();
        void onFailureDeleteReview();
        void onSuccessUpdateEstablishment();
        void onFailureUpdateEstablishment();
        void onSuccessGetCurrentReviews(ArrayList<Resena_Modelo> Resenas);
        void onFailureGetCurrentReviews();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
        void onSuccessGetSessionData(String ID_Usuario);
        void onSuccessGetNumberOfCoupons(int Numero_Cupones);
        void onSuccessGetCouponUserFromUser(ArrayList<CuponUsuario_Modelo> Cupones_Usuario);
        void onFailureGetCouponUserFromUser();
        void onSuccessDeleteCouponUser();
        void onFailureDeleteCouponUser();
    }

}

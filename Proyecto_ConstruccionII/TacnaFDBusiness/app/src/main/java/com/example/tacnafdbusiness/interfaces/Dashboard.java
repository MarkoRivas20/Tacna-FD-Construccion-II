package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface Dashboard {

    interface View{
        void onGetEstablismentWithMoreReviewsSuccessful(String Nombre_Establecimiento_Mas_Comentarios);
        void onGetEstablismentWithMoreReviewsFailure();
        void onSearchEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> establecimiento);
        void onSearchEstablishmentFailure();
        void onGetMonthSalesSuccessful(int Ventas_Mes, String Nombre_Establecimiento_Mas_Ventas);
        void onGetMonthSalestFailure();
        void onSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void SearchEstablishment(DatabaseReference reference, String ID_Usuario);
        void GetEstablismentWithMoreReviews(DatabaseReference reference, ArrayList<Establecimiento_Modelo> establecimiento);
        void GetMonthSales(DatabaseReference reference, ArrayList<Establecimiento_Modelo> establecimiento, String Numero_Mes);
        void GetSessionData(Context context);
    }

    interface Interactor{
        void performSearchEstablishment(DatabaseReference reference, String ID_Usuario);
        void performGetEstablismentWithMoreReviews(DatabaseReference reference, ArrayList<Establecimiento_Modelo> establecimiento);
        void performGetMonthSales(DatabaseReference reference, ArrayList<Establecimiento_Modelo> establecimiento, String Numero_Mes);
        void performGetSessionData(Context context);
    }

    interface onOperationListener{
        void onSuccessSearchEstablishment(ArrayList<Establecimiento_Modelo> establecimiento);
        void onFailureSearchEstablishment();
        void onSuccessGetEstablismentWithMoreReviews(String Nombre_Establecimiento_Mas_Comentarios);
        void onFailureGetEstablismentWithMoreReviews();
        void onSuccessGetMonthSales(int Ventas_Mes, String Nombre_Establecimiento_Mas_Ventas);
        void onFailureGetMonthSales();
        void onSuccessGetSessionData(String ID_Usuario);
    }
}

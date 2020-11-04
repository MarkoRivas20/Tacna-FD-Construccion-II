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
        void onSearchEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onSearchEstablishmentFailure();
        void onGetMonthSalesSuccessful(int Ventas_Mes, String Nombre_Establecimiento_Mas_Ventas);
        void onGetMonthSalestFailure();
        void onSessionDataSuccessful(String ID_Usuario);
    }

    interface Presenter{
        void SearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario);
        void GetEstablismentWithMoreReviews(DatabaseReference Database_Reference, ArrayList<Establecimiento_Modelo> Establecimientos);
        void GetMonthSales(DatabaseReference Database_Reference, ArrayList<Establecimiento_Modelo> Establecimientos, String Numero_Mes);
        void GetSessionData(Context Contexto);
    }

    interface Interactor{
        void performSearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario);
        void performGetEstablismentWithMoreReviews(DatabaseReference Database_Reference, ArrayList<Establecimiento_Modelo> Establecimientos);
        void performGetMonthSales(DatabaseReference Database_Reference, ArrayList<Establecimiento_Modelo> Establecimientos, String Numero_Mes);
        void performGetSessionData(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessSearchEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento);
        void onFailureSearchEstablishment();
        void onSuccessGetEstablismentWithMoreReviews(String Nombre_Establecimiento_Mas_Comentarios);
        void onFailureGetEstablismentWithMoreReviews();
        void onSuccessGetMonthSales(int Ventas_Mes, String Nombre_Establecimiento_Mas_Ventas);
        void onFailureGetMonthSales();
        void onSuccessGetSessionData(String ID_Usuario);
    }
}

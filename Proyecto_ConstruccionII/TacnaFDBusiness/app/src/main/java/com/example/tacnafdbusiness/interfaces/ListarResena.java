package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarResena {

    interface View{
        void onGetReviewsSuccessful(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena);
        void onGetReviewsFailure();
        void onSearchClientNameSuccessful(ArrayList<Resena_Modelo> Resenas);
        void onSearchClientNameFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void GetReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void SearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performGetReviews(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performSearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessGetReviews(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena);
        void onFailureGetReviews();
        void onSuccessSearchClientName(ArrayList<Resena_Modelo> Resenas);
        void onFailureSearchClientName();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

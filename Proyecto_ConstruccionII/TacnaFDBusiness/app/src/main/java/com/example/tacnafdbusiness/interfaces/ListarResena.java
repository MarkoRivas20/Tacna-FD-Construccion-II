package com.example.tacnafdbusiness.interfaces;

import android.content.Context;

import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface ListarResena {

    interface View{
        void onGetReviewsSuccessful(ArrayList<Resena_Modelo> resena_modelos, Boolean Existe_Resena);
        void onGetReviewsFailure();
        void onSearchClientNameSuccessful(ArrayList<Resena_Modelo> resena_modelos);
        void onSearchClientNameFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
    }

    interface Presenter{
        void GetReviews(DatabaseReference reference, String Id_Establecimiento);
        void SearchClientName(DatabaseReference reference, ArrayList<Resena_Modelo> resena_modelos);
        void GetEstablishmentInfo(Context context);
    }

    interface Interactor{
        void performGetReviews(DatabaseReference reference, String Id_Establecimiento);
        void performSearchClientName(DatabaseReference reference, ArrayList<Resena_Modelo> resena_modelos);
        void performGetEstablishmentInfo(Context context);
    }

    interface onOperationListener{
        void onSuccessGetReviews(ArrayList<Resena_Modelo> resena_modelos, Boolean Existe_Resena);
        void onFailureGetReviews();
        void onSuccessSearchClientName(ArrayList<Resena_Modelo> resena_modelos);
        void onFailureSearchClientName();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
    }
}

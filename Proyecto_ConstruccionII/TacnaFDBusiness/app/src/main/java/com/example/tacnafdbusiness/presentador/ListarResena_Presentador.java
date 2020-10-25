package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ListarEstablecimineto_Interactor;
import com.example.tacnafdbusiness.interactor.ListarResena_Interactor;
import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.interfaces.ListarResena;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListarResena_Presentador implements ListarResena.Presenter, ListarResena.onOperationListener {

    private ListarResena.View mView;
    private ListarResena.Interactor mInteractor;

    public ListarResena_Presentador(ListarResena.View mView) {
        this.mView = mView;
        mInteractor=new ListarResena_Interactor(this);
    }

    @Override
    public void GetReviews(DatabaseReference reference, String Id_Establecimiento) {
        mInteractor.performGetReviews(reference, Id_Establecimiento);
    }

    @Override
    public void SearchClientName(DatabaseReference reference, ArrayList<Resena_Modelo> resena_modelos) {
        mInteractor.performSearchClientName(reference, resena_modelos);
    }

    @Override
    public void GetEstablishmentInfo(Context context) {
        mInteractor.performGetEstablishmentInfo(context);
    }

    @Override
    public void onSuccessGetReviews(ArrayList<Resena_Modelo> resena_modelos, Boolean Existe_Resena) {
        mView.onGetReviewsSuccessful(resena_modelos, Existe_Resena);
    }

    @Override
    public void onFailureGetReviews() {
        mView.onGetReviewsFailure();
    }

    @Override
    public void onSuccessSearchClientName(ArrayList<Resena_Modelo> resena_modelos) {
        mView.onSearchClientNameSuccessful(resena_modelos);
    }

    @Override
    public void onFailureSearchClientName() {
        mView.onSearchClientNameFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String Id_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(Id_Establecimiento);
    }
}

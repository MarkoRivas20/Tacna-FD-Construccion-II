package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.ListarResena_Interactor;
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
    public void GetReviews(DatabaseReference Database_Reference, String Id_Establecimiento) {
        mInteractor.performGetReviews(Database_Reference, Id_Establecimiento);
    }

    @Override
    public void SearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas) {
        mInteractor.performSearchClientName(Database_Reference, Resenas);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void onSuccessGetReviews(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena) {
        mView.onGetReviewsSuccessful(Resenas, Existe_Resena);
    }

    @Override
    public void onFailureGetReviews() {
        mView.onGetReviewsFailure();
    }

    @Override
    public void onSuccessSearchClientName(ArrayList<Resena_Modelo> Resenas) {
        mView.onSearchClientNameSuccessful(Resenas);
    }

    @Override
    public void onFailureSearchClientName() {
        mView.onSearchClientNameFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }
}

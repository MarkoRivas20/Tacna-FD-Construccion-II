package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.RegistrarResena_Interactor;
import com.example.tacnafdcliente.interfaces.RegistrarResena;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RegistrarResena_Presentador implements RegistrarResena.Presenter, RegistrarResena.onOperationListener {

    private RegistrarResena.View mView;
    private RegistrarResena_Interactor mInteractor;

    public RegistrarResena_Presentador(RegistrarResena.View mView) {
        this.mView = mView;
        mInteractor=new RegistrarResena_Interactor(this);
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
    public void SaveReview(DatabaseReference Database_Reference, Resena_Modelo Resena) {
        mInteractor.performSaveReview(Database_Reference, Resena);
    }

    @Override
    public void UpdateEstablishment(DatabaseReference Database_Reference, String ID_Establecimiento, Double Calificacion, int Total_Resenas) {
        mInteractor.performUpdateEstablishment(Database_Reference, ID_Establecimiento, Calificacion, Total_Resenas);
    }

    @Override
    public void GetCurrentReviews(DatabaseReference Database_Reference, String ID_Establecimiento) {
        mInteractor.performGetCurrentReviews(Database_Reference, ID_Establecimiento);
    }

    @Override
    public void GetEstablishmentInfo(Context Contexto) {
        mInteractor.performGetEstablishmentInfo(Contexto);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
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
    public void onSuccessSaveReview() {
        mView.onSaveReviewSuccessful();
    }

    @Override
    public void onFailureSaveReview() {
        mView.onSaveReviewFailure();
    }

    @Override
    public void onSuccessUpdateEstablishment() {
        mView.onUpdateEstablishmentSuccessful();
    }

    @Override
    public void onFailureUpdateEstablishment() {
        mView.onUpdateEstablishmentFailure();
    }

    @Override
    public void onSuccessGetCurrentReviews(ArrayList<Resena_Modelo> Resenas) {
        mView.onGetCurrentReviewsSuccessful(Resenas);
    }

    @Override
    public void onFailureGetCurrentReviews() {
        mView.onGetCurrentReviewsFailure();
    }

    @Override
    public void onSuccessGetEstablishmentInfo(String ID_Establecimiento) {
        mView.onGetEstablishmentInfoSuccessful(ID_Establecimiento);
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onGetSessionDataSuccessful(ID_Usuario);
    }
}

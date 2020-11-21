package com.example.tacnafdcliente.presentador;

import android.content.Context;

import com.example.tacnafdcliente.interactor.GestionarResena_Interactor;
import com.example.tacnafdcliente.interfaces.GestionarResena;
import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class GestionarResena_Presentador implements GestionarResena.Presenter, GestionarResena.onOperationListener {

    private GestionarResena.View mView;
    private GestionarResena_Interactor mInteractor;

    public GestionarResena_Presentador(GestionarResena.View mView) {
        this.mView = mView;
        mInteractor=new GestionarResena_Interactor(this);
    }

    @Override
    public void GetReviews(DatabaseReference Database_Reference, String Id_Establecimiento, String ID_Usuario) {
        mInteractor.performGetReviews(Database_Reference, Id_Establecimiento, ID_Usuario);
    }

    @Override
    public void SearchClientName(DatabaseReference Database_Reference, ArrayList<Resena_Modelo> Resenas) {
        mInteractor.performSearchClientName(Database_Reference, Resenas);
    }

    @Override
    public void GetUserReview(DatabaseReference Database_Reference, String ID_Establecimiento, String ID_Usuario) {
        mInteractor.performGetUserReview(Database_Reference, ID_Establecimiento, ID_Usuario);
    }

    @Override
    public void UpdateReview(DatabaseReference Database_Reference, Resena_Modelo Resena) {
        mInteractor.performUpdateReview(Database_Reference, Resena);
    }

    @Override
    public void DeleteReview(DatabaseReference Database_Reference, String ID_Resena) {
        mInteractor.performDeleteReview(Database_Reference, ID_Resena);
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
    public void SaveNumberOfCoupons(Context Contexto, int Numero_Cupones) {
        mInteractor.performSaveNumberOfCoupons(Contexto, Numero_Cupones);
    }

    @Override
    public void GetNumberOfCoupons(Context Contexto) {
        mInteractor.performGetNumberOfCoupons(Contexto);
    }

    @Override
    public void GetCouponUserFromUser(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performGetCouponUserFromUser(Database_Reference, ID_Usuario);
    }

    @Override
    public void DeleteCouponUser(DatabaseReference Database_Reference, String ID_Cupon_Usuario) {
        mInteractor.performDeleteCouponUser(Database_Reference, ID_Cupon_Usuario);
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
    public void onSuccessGetUserReview(Resena_Modelo Resena) {
        mView.onGetUserReviewSuccessful(Resena);
    }

    @Override
    public void onFailureGetUserReview() {
        mView.onGetUserReviewFailure();
    }

    @Override
    public void onSuccessUpdateReview() {
        mView.onUpdateReviewSuccessful();
    }

    @Override
    public void onFailureUpdateReview() {
        mView.onUpdateReviewFailure();
    }

    @Override
    public void onSuccessDeleteReview() {
        mView.onDeleteReviewSuccessful();
    }

    @Override
    public void onFailureDeleteReview() {
        mView.onDeleteReviewFailure();
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

    @Override
    public void onSuccessGetNumberOfCoupons(int Numero_Cupones) {
        mView.onGetNumberOfCouponsSuccessful(Numero_Cupones);
    }

    @Override
    public void onSuccessGetCouponUserFromUser(ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {
        mView.onGetCouponUserFromUserSuccessful(Cupones_Usuario);
    }

    @Override
    public void onFailureGetCouponUserFromUser() {
        mView.onGetCouponUserFromUserFailure();
    }

    @Override
    public void onSuccessDeleteCouponUser() {
        mView.onDeleteCouponUserSuccessful();
    }

    @Override
    public void onFailureDeleteCouponUser() {
        mView.onDeleteCouponUserFailure();
    }
}

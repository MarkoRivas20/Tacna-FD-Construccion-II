package com.example.tacnafdbusiness.presentador;

import android.content.Context;

import com.example.tacnafdbusiness.interactor.Dashboard_Interactor;
import com.example.tacnafdbusiness.interactor.ListarEstablecimineto_Interactor;
import com.example.tacnafdbusiness.interfaces.Dashboard;
import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Dashboard_Presentador implements Dashboard.Presenter, Dashboard.onOperationListener {

    private Dashboard.View mView;
    private Dashboard_Interactor mInteractor;

    public Dashboard_Presentador(Dashboard.View mView) {
        this.mView = mView;
        mInteractor = new Dashboard_Interactor(this);
    }

    @Override
    public void SearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario) {
        mInteractor.performSearchEstablishment(Database_Reference, ID_Usuario);
    }

    @Override
    public void GetEstablismentWithMoreReviews(DatabaseReference Database_Reference, ArrayList<Establecimiento_Modelo> Establecimientos) {
        mInteractor.performGetEstablismentWithMoreReviews(Database_Reference, Establecimientos);
    }

    @Override
    public void GetMonthSales(DatabaseReference Database_Reference, ArrayList<Establecimiento_Modelo> Establecimientos, String Numero_Mes) {
        mInteractor.performGetMonthSales(Database_Reference, Establecimientos, Numero_Mes);
    }

    @Override
    public void GetSessionData(Context Contexto) {
        mInteractor.performGetSessionData(Contexto);
    }

    @Override
    public void onSuccessSearchEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento) {
        mView.onSearchEstablishmentSuccessful(Establecimientos, Existe_Establecimiento);
    }

    @Override
    public void onFailureSearchEstablishment() {
        mView.onSearchEstablishmentFailure();
    }

    @Override
    public void onSuccessGetEstablismentWithMoreReviews(String Nombre_Establecimiento_Mas_Comentarios) {
        mView.onGetEstablismentWithMoreReviewsSuccessful(Nombre_Establecimiento_Mas_Comentarios);
    }

    @Override
    public void onFailureGetEstablismentWithMoreReviews() {
        mView.onGetEstablismentWithMoreReviewsFailure();
    }

    @Override
    public void onSuccessGetMonthSales(int Ventas_Mes, String Nombre_Establecimiento_Mas_Ventas) {
        mView.onGetMonthSalesSuccessful(Ventas_Mes, Nombre_Establecimiento_Mas_Ventas);
    }

    @Override
    public void onFailureGetMonthSales() {
        mView.onGetMonthSalestFailure();
    }

    @Override
    public void onSuccessGetSessionData(String ID_Usuario) {
        mView.onSessionDataSuccessful(ID_Usuario);
    }
}

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
    public void SearchEstablishment(DatabaseReference reference, String ID_Usuario) {
        mInteractor.performSearchEstablishment(reference, ID_Usuario);
    }

    @Override
    public void GetEstablismentWithMoreReviews(DatabaseReference reference, ArrayList<Establecimiento_Modelo> establecimiento) {
        mInteractor.performGetEstablismentWithMoreReviews(reference, establecimiento);
    }

    @Override
    public void GetMonthSales(DatabaseReference reference, ArrayList<Establecimiento_Modelo> establecimiento, String Numero_Mes) {
        mInteractor.performGetMonthSales(reference, establecimiento, Numero_Mes);
    }

    @Override
    public void GetSessionData(Context context) {
        mInteractor.performGetSessionData(context);
    }

    @Override
    public void onSuccessSearchEstablishment(ArrayList<Establecimiento_Modelo> establecimiento, Boolean Existe_Establecimiento) {
        mView.onSearchEstablishmentSuccessful(establecimiento, Existe_Establecimiento);
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

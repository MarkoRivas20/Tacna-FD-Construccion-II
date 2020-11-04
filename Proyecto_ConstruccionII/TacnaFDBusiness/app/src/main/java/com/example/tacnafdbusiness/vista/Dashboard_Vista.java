package com.example.tacnafdbusiness.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.Dashboard;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.example.tacnafdbusiness.presentador.Dashboard_Presentador;
import com.example.tacnafdbusiness.presentador.ListarEstablecimiento_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Dashboard_Vista extends Fragment implements Dashboard.View {


    public Dashboard_Vista() {
        // Required empty public constructor
    }

    public Dashboard_Presentador mPresenter;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Resenas;
    public DatabaseReference mReference_Pedidos;

    TextView TxtEstablecimientos;
    TextView TxtEstablecimiento_Mejor_Puntuacion;
    TextView TxtEstablecimiento_Mas_Comentarios;
    TextView TxtVentas_Mes;
    TextView TxtEstablecimiento_Mas_Ventas;

    String Id_Usuario = "";
    String Patron_Fecha = "MM";
    String Numero_Mes = "";

    Double Mejor_Puntuacion = 0.0;
    int Posicion_Mejor_Establecimiento = 0;

    ArrayList<Establecimiento_Modelo> establecimiento_modelos = new ArrayList<>();

    SimpleDateFormat simpleDateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard__vista, container, false);

        mPresenter = new Dashboard_Presentador(this);
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Resenas = FirebaseDatabase.getInstance().getReference().child("Resena");
        mReference_Pedidos = FirebaseDatabase.getInstance().getReference().child("Pedido");

        simpleDateFormat = new SimpleDateFormat(Patron_Fecha);
        Numero_Mes = simpleDateFormat.format(new Date());

        TxtEstablecimientos = (TextView) view.findViewById(R.id.TxtEstablecimientos);
        TxtEstablecimiento_Mejor_Puntuacion = (TextView) view.findViewById(R.id.TxtEstablecimiento_Mejor_Puntuacion);
        TxtEstablecimiento_Mas_Comentarios = (TextView) view.findViewById(R.id.TxtEstablecimiento_Mas_Comentarios);
        TxtVentas_Mes = (TextView) view.findViewById(R.id.TxtVentas_Mes);
        TxtEstablecimiento_Mas_Ventas = (TextView) view.findViewById(R.id.TxtEstablecimiento_Mas_Ventas);

        mPresenter.GetSessionData(getActivity());
        mPresenter.SearchEstablishment(mReference_Establecimiento, Id_Usuario);

        return view;
    }

    @Override
    public void onGetEstablismentWithMoreReviewsSuccessful(String Nombre_Establecimiento_Mas_Comentarios) {

        if(Nombre_Establecimiento_Mas_Comentarios == null){
            TxtEstablecimiento_Mas_Comentarios.setText("Ninguno");
        }
        else
        {
            TxtEstablecimiento_Mas_Comentarios.setText(Nombre_Establecimiento_Mas_Comentarios);
        }

        /*
        for(int i = 0; i < establecimiento_modelos.size(); i++){
            if(establecimiento_modelos.get(i).getID_Establecimiento().equals(Id_Establecimiento_Mas_Comentarios)){
                TxtEstablecimiento_Mas_Comentarios.setText(establecimiento_modelos.get(i).getNombre());
                break;
            }
        }*/
    }

    @Override
    public void onGetEstablismentWithMoreReviewsFailure() {

    }

    @Override
    public void onSearchEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> establecimiento, Boolean Existe_Establecimiento) {
        establecimiento_modelos = establecimiento;

        TxtEstablecimientos.setText(String.valueOf(establecimiento.size()));

        Mejor_Puntuacion = 0.0;
        for(int i = 0; i < establecimiento.size(); i++){

            if(Double.valueOf(establecimiento.get(i).getPuntuacion()) > Mejor_Puntuacion){
                Mejor_Puntuacion = Double.valueOf(establecimiento.get(i).getPuntuacion());
                Posicion_Mejor_Establecimiento = i;
            }
        }

        if(Existe_Establecimiento){
            TxtEstablecimiento_Mejor_Puntuacion.setText(establecimiento.get(Posicion_Mejor_Establecimiento).getNombre());
        }
        else
        {
            TxtEstablecimiento_Mejor_Puntuacion.setText("Ninguno");
        }


        mPresenter.GetEstablismentWithMoreReviews(mReference_Resenas, establecimiento);
        mPresenter.GetMonthSales(mReference_Pedidos, establecimiento, Numero_Mes);



    }

    @Override
    public void onSearchEstablishmentFailure() {

    }

    @Override
    public void onGetMonthSalesSuccessful(int Ventas_Mes, String Nombre_Establecimiento_Mas_Ventas) {
        TxtVentas_Mes.setText(String.valueOf(Ventas_Mes));

        if(Nombre_Establecimiento_Mas_Ventas == null){
            TxtEstablecimiento_Mas_Ventas.setText("Ninguno");
        }
        else
        {
            TxtEstablecimiento_Mas_Ventas.setText(Nombre_Establecimiento_Mas_Ventas);

        }


    }

    @Override
    public void onGetMonthSalestFailure() {

    }

    @Override
    public void onSessionDataSuccessful(String ID_Usuario) {
        Id_Usuario = ID_Usuario;
    }
}
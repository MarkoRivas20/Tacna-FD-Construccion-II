package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.Dashboard;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard_Interactor implements Dashboard.Interactor {

    private Dashboard.onOperationListener mListener;

    private ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();

    private int Numero_Mayor_Resenas=0;
    private int Contador_Resenas = 0;
    private int Contador_Pedidos = 0;
    private int contador = 0;
    private int contador_mayor = 0;

    private String Nombre_Establecimiento_Mas_Comentarios = null;
    private String Nombre_Establecimiento_Mas_Ventas = null;

    public Dashboard_Interactor(Dashboard.onOperationListener mListener) {
        this.mListener = mListener;
    }
    /*Buscando los establecimientos que tiene registrado un usuario*/
    @Override
    public void performSearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario) {

        Query query=Database_Reference.orderByChild("id_Usuario_Propietario").startAt(ID_Usuario);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Boolean Existe_Establecimiento = false;
                Establecimientos.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Establecimiento = true;
                    Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                    Establecimientos.add(Establecimiento);
                }

                mListener.onSuccessSearchEstablishment(Establecimientos, Existe_Establecimiento);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                mListener.onFailureSearchEstablishment();
            }
        });
    }

    /*Buscando el establecimiento con mas comentarios*/
    @Override
    public void performGetEstablismentWithMoreReviews(DatabaseReference Database_Reference, final ArrayList<Establecimiento_Modelo> Establecimientos) {

        /*Recorrer los establecimientos que tiene el usuario*/
        for(int i = 0; i < Establecimientos.size(); i++)
        {
            final int posicion = i;
            Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(Establecimientos.get(i).getID_Establecimiento());

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Contador_Resenas = 0;
                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Contador_Resenas++;
                    }

                    if(Contador_Resenas > Numero_Mayor_Resenas)
                    {
                        Numero_Mayor_Resenas = Contador_Resenas;
                        Nombre_Establecimiento_Mas_Comentarios = Establecimientos.get(posicion).getNombre();
                    }
                    mListener.onSuccessGetEstablismentWithMoreReviews(Nombre_Establecimiento_Mas_Comentarios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureGetEstablismentWithMoreReviews();
                }
            });

        }


    }

    /*Obteniendo los pedidos del mes*/
    @Override
    public void performGetMonthSales(DatabaseReference Database_Reference, final ArrayList<Establecimiento_Modelo> Establecimientos, final String Numero_Mes) {

        Database_Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Contador_Pedidos=0;
                contador_mayor=0;
                Nombre_Establecimiento_Mas_Ventas = null;

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Pedido_Modelo Pedido = postSnapshot.getValue(Pedido_Modelo.class);

                    /*Recorriengo los establecimientos del usuario*/
                    for(int i = 0; i < Establecimientos.size(); i++)
                    {
                        contador = 0;
                        if(Pedido.getID_Establecimiento().equals(Establecimientos.get(i).getID_Establecimiento()))
                        {
                            /*Compara la fecha del pedido con la fecha del mes actual(dispositivo)*/
                            if(Pedido.getFecha().contains("/"+Numero_Mes+"/"))
                            {
                                Contador_Pedidos++;
                                contador++;
                            }
                            if(contador > contador_mayor)
                            {
                                contador_mayor = contador;
                                Nombre_Establecimiento_Mas_Ventas = Establecimientos.get(i).getNombre();
                            }

                            break;
                        }



                    }
                }
                mListener.onSuccessGetMonthSales(Contador_Pedidos, Nombre_Establecimiento_Mas_Ventas);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetMonthSales();
            }
        });


    }

    /*Obteniendo el ID de usuario del SharedPreferences*/
    @Override
    public void performGetSessionData(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
        else{
            mListener.onFailureSearchEstablishment();
        }

    }
}

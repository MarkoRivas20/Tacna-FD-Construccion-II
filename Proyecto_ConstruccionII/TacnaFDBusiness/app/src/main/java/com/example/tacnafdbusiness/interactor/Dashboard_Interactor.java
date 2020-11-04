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

    private ArrayList<Establecimiento_Modelo> establecimiento_modelos = new ArrayList<>();

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

    @Override
    public void performSearchEstablishment(DatabaseReference reference, String ID_Usuario) {

        Query query=reference.orderByChild("id_Usuario_Propietario").startAt(ID_Usuario);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Boolean Existe_Establecimiento = false;
                establecimiento_modelos.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Existe_Establecimiento = true;
                    Establecimiento_Modelo establecimiento_modelo = postSnapshot.getValue(Establecimiento_Modelo.class);
                    establecimiento_modelos.add(establecimiento_modelo);
                }

                mListener.onSuccessSearchEstablishment(establecimiento_modelos, Existe_Establecimiento);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                mListener.onFailureSearchEstablishment();
            }
        });
    }

    @Override
    public void performGetEstablismentWithMoreReviews(DatabaseReference reference, final ArrayList<Establecimiento_Modelo> establecimiento) {

        for(int i = 0; i < establecimiento.size(); i++){

            final int posicion = i;
            Query query = reference.orderByChild("id_Establecimiento").equalTo(establecimiento.get(i).getID_Establecimiento());

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Contador_Resenas = 0;
                    for(DataSnapshot postSnapshot : snapshot.getChildren()){
                        Contador_Resenas++;
                    }
                    if(Contador_Resenas > Numero_Mayor_Resenas){
                        Numero_Mayor_Resenas = Contador_Resenas;
                        Nombre_Establecimiento_Mas_Comentarios = establecimiento.get(posicion).getNombre();
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

    @Override
    public void performGetMonthSales(DatabaseReference reference, final ArrayList<Establecimiento_Modelo> establecimiento, final String Numero_Mes) {
/*
        Contador_Pedidos = 0;

        for(int i = 0; i < establecimiento.size(); i++){

            final int posicion = i;
            contador = 0;

            Query query = reference.orderByChild("id_Establecimiento").equalTo(establecimiento.get(i).getID_Establecimiento());

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    for(DataSnapshot postSnapshot : snapshot.getChildren()){

                        Pedido_Modelo pedido_modelo = postSnapshot.getValue(Pedido_Modelo.class);

                        if(pedido_modelo.getFecha().contains("/"+Numero_Mes+"/")){
                            Contador_Pedidos++;
                            contador++;
                        }

                    }

                    if(contador > contador_mayor){
                        contador_mayor = contador;
                        Id_Establecimiento_Mas_Ventas = establecimiento.get(posicion).getID_Establecimiento();
                    }


                    mListener.onSuccessGetMonthSales(Contador_Pedidos, Id_Establecimiento_Mas_Ventas);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureGetMonthSales();
                }
            });

        }
        */
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Contador_Pedidos=0;
                contador_mayor=0;
                Nombre_Establecimiento_Mas_Ventas = null;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Pedido_Modelo pedido_modelo = postSnapshot.getValue(Pedido_Modelo.class);

                    for(int i = 0; i < establecimiento.size(); i++){
                        contador = 0;
                        if(pedido_modelo.getID_Establecimiento().equals(establecimiento.get(i).getID_Establecimiento())){

                            if(pedido_modelo.getFecha().contains("/"+Numero_Mes+"/")){
                                Contador_Pedidos++;
                                contador++;
                            }
                            if(contador > contador_mayor){
                                contador_mayor = contador;
                                Nombre_Establecimiento_Mas_Ventas = establecimiento.get(i).getNombre();
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

    @Override
    public void performGetSessionData(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String id_Usuario = sharedPref.getString("id_usuario","");

        if(id_Usuario.length() != 0){
            mListener.onSuccessGetSessionData(id_Usuario);
        }
        else{
            mListener.onFailureSearchEstablishment();
        }

    }
}

package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.ListarMiCupon;
import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarMiCupon_Interactor implements ListarMiCupon.Interactor {

    private ListarMiCupon.onOperationListener mListener;

    private ValueEventListener valueEventListener_Get_Coupon_User;
    private ValueEventListener valueEventListener_Search_Coupon_Info;
    private ValueEventListener valueEventListener_Search_Establishment_Name;

    private ArrayList<CuponUsuario_Modelo> Cupones_Usuario = new ArrayList<>();

    public ListarMiCupon_Interactor(ListarMiCupon.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetNumberOfCoupons(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("numero_cupones", Context.MODE_PRIVATE);
        int Numero_Cupones = sharedPref.getInt("numero_cupones",0);

        mListener.onSuccessGetNumberOfCoupons(Numero_Cupones);
    }

    @Override
    public void performGetCouponUser(DatabaseReference Database_Reference, String ID_Usuario) {
        final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(ID_Usuario);

        valueEventListener_Get_Coupon_User = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cupones_Usuario.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    CuponUsuario_Modelo Cupon_Usuario = postSnapshot.getValue(CuponUsuario_Modelo.class);
                    Cupones_Usuario.add(Cupon_Usuario);
                }
                mListener.onSuccessGetCouponUser(Cupones_Usuario);
                query.removeEventListener(valueEventListener_Get_Coupon_User);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetCouponUser();
            }
        };

        query.addValueEventListener(valueEventListener_Get_Coupon_User);
    }

    @Override
    public void performSearchEstablishmentName(DatabaseReference Database_Reference, final ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {

        for(int i = 0; i < Cupones_Usuario.size(); i++)
        {
            final int Posicion = i;

            final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(Cupones_Usuario.get(i).getID_Establecimiento());

            valueEventListener_Search_Establishment_Name = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                        Cupones_Usuario.get(Posicion).setNombre_Establecimiento(Establecimiento.getNombre());
                    }

                    mListener.onSuccessSearchEstablishmentName(Cupones_Usuario);
                    query.removeEventListener(valueEventListener_Search_Establishment_Name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchEstablishmentName();
                }
            };

            query.addListenerForSingleValueEvent(valueEventListener_Search_Establishment_Name);
        }

    }

    @Override
    public void performSearchCouponInfo(DatabaseReference Database_Reference, final ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {

        for(int i = 0; i < Cupones_Usuario.size(); i++)
        {
            final int Posicion = i;

            final Query query = Database_Reference.orderByChild("id_Cupon").equalTo(Cupones_Usuario.get(i).getID_Cupon());

            valueEventListener_Search_Coupon_Info = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Cupon_Modelo Cupon = postSnapshot.getValue(Cupon_Modelo.class);
                        Cupones_Usuario.get(Posicion).setTitulo_Cupon(Cupon.getTitulo());
                        Cupones_Usuario.get(Posicion).setUrl_Imagen_Cupon(Cupon.getUrl_Imagen());
                        Cupones_Usuario.get(Posicion).setID_Establecimiento(Cupon.getId_Establecimiento());
                    }

                    mListener.onSuccessSearchCouponInfo(Cupones_Usuario);
                    query.removeEventListener(valueEventListener_Search_Coupon_Info);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchCouponInfo();
                }
            };

            query.addListenerForSingleValueEvent(valueEventListener_Search_Coupon_Info);
        }

    }

    @Override
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
    }
}

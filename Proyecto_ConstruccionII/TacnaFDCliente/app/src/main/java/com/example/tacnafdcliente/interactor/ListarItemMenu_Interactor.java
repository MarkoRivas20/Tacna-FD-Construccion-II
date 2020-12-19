package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.ListarItemMenu;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarItemMenu_Interactor implements ListarItemMenu.Interactor {

    private ListarItemMenu.onOperationListener mListener;

    private ArrayList<ItemMenu_Modelo> Items_Menu = new ArrayList<>();

    public ListarItemMenu_Interactor(ListarItemMenu.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Obteniendo los Items del Menu registrados en el establecimiento*/

    @Override
    public void performListItemMenu(DatabaseReference Database_Reference, String ID_Establecimiento) {
        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Item_Menu = false;
                Items_Menu.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Existe_Item_Menu = true;
                    ItemMenu_Modelo Item_Menu = postSnapshot.getValue(ItemMenu_Modelo.class);
                    Items_Menu.add(Item_Menu);
                }

                mListener.onSuccessListItemMenu(Items_Menu, Existe_Item_Menu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureListItemMenu();
            }
        });
    }


    /*Obteniendo el ID del establecimiento del SharedPreferences*/

    @Override
    public void performGetEstablishmentInfo(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento", "");

        if (ID_Establecimiento.length() != 0) {
            mListener.onSuccessGetEstablishmentInfo(ID_Establecimiento);
        }
    }

    @Override
    public void performSaveCouponInfo(Context Contexto, String ID_Cupon, String ID_Cupon_Usuario, int Descuento) {
        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_cupon", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_cupon", ID_Cupon);
        editor.putString("id_cupon_usuario", ID_Cupon_Usuario);
        editor.putInt("descuento", Descuento);
        editor.apply();
    }
}
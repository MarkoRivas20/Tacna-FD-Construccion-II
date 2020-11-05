package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
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

    /*Eliminar un Item Menu de un establecimiento*/
    @Override
    public void performDeleteItemMenu(DatabaseReference Database_Reference, String ID_Item_Menu, String Url_Imagen) {

        /*Eliminando el registro de la base de datos*/
        Database_Reference.child(ID_Item_Menu).removeValue().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteItemMenu();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        /*Eliminando la Imagen de Storage*/
        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url_Imagen);

        reference.delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteItemMenu();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mListener.onSuccessDeleteItemMenu();
            }
        });
    }
    /*Obteniendo el ID del establecimiento del SharedPreferences*/
    @Override
    public void performGetEstablishmentInfo(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(ID_Establecimiento);
        }
    }
}

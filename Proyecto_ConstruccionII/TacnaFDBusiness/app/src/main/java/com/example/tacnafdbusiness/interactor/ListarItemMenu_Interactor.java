package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.interfaces.ListarItemMenu;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
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

    private ArrayList<ItemMenu_Modelo> itemMenu_modelos = new ArrayList<>();

    public ListarItemMenu_Interactor(ListarItemMenu.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performListItemMenu(DatabaseReference reference, String Id_Establecimiento) {
        Query query = reference.orderByChild("id_Establecimiento").equalTo(Id_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Item_Menu = false;
                itemMenu_modelos.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Existe_Item_Menu = true;
                    ItemMenu_Modelo itemMenu_modelo = postSnapshot.getValue(ItemMenu_Modelo.class);
                    itemMenu_modelos.add(itemMenu_modelo);
                }

                mListener.onSuccessListItemMenu(itemMenu_modelos, Existe_Item_Menu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureListItemMenu();
            }
        });
    }

    @Override
    public void performDeleteItemMenu(DatabaseReference Database_Reference, String Id_Item_Menu, String Url_Imagen) {

        Database_Reference.child(Id_Item_Menu).removeValue().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteItemMenu();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

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

    @Override
    public void performGetEstablishmentInfo(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(Id_Establecimiento.length() != 0){
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento);
        }
    }
}

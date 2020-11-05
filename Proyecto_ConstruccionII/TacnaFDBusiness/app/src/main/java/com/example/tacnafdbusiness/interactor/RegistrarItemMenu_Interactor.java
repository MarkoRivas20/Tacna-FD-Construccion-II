package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.RegistrarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrarItemMenu_Interactor implements RegistrarItemMenu.Interactor {

    private RegistrarItemMenu.onOperationListener mListener;

    public RegistrarItemMenu_Interactor(RegistrarItemMenu.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Registrando los datos de un nuevo item Menu en la base de datos*/
    @Override
    public void performSaveItemMenu(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu) {

        Database_Reference.child(Item_Menu.getID_Item_Menu()).setValue(Item_Menu).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    mListener.onSuccessSaveItemMenu();
                }
                else
                {
                    mListener.onFailureSaveItemMenu();
                }
            }
        });
    }

    /*Guardando la Imagen del Item Menu en el Storage*/
    @Override
    public void performUploadItemMenuImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri) {

        final StorageReference filePath = Storage_Reference.child(ID_Establecimiento).child("ItemMenu").child(Imagen_Uri.getLastPathSegment());

        filePath.putFile(Imagen_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUploadItemMenuImage();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        mListener.onSuccessUploadItemMenuImage(uri.toString());
                    }
                });
            }
        });

    }

    /*Obteniendo ID establecimiento del SharedPreferences*/
    @Override
    public void performGetEstablishmentInfo(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(Id_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento);
        }
    }
}

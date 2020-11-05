package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.RegistrarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class RegistrarEstablecimiento_Interactor implements RegistrarEstablecimiento.Interactor {

    private RegistrarEstablecimiento.onOperationListener mListener;

    public RegistrarEstablecimiento_Interactor(RegistrarEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Registrando los datos de un nuevo Establecimiento en la base de datos*/
    @Override
    public void performCreateEstablishment(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento) {

        Database_Reference.child(Establecimiento.getID_Establecimiento()).setValue(Establecimiento).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    mListener.onSuccess();
                }
                else
                {
                    mListener.onFailure();
                }

            }
        });

    }

    /*Guardando el Logo del establecimiento en el Storage*/
    @Override
    public void performUploadLogo(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri) {

        final StorageReference filePath = Storage_Reference.child(ID_Establecimiento).child("Logo").child(Imagen_Uri.getLastPathSegment());

        filePath.putFile(Imagen_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUploadLogo();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        mListener.onSuccessUploadLogo(uri.toString());

                    }
                });

            }
        });



    }

    /*Guardando la imagen del documento del establecimiento en el Storage*/
    @Override
    public void performUploadDocument(StorageReference Storage_Reference, String ID_Establecimiento, Uri Documento_Uri) {

        final StorageReference filePath = Storage_Reference.child(ID_Establecimiento).child("Documento").child(Documento_Uri.getLastPathSegment());

        filePath.putFile(Documento_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUploadDocument();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        mListener.onSuccessUploadDocument(uri.toString());

                    }
                });
            }
        });


    }

    /*Obteniendo el ID del usuario del SharedPreferences*/
    @Override
    public void performGetSessionData(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
        else{
            mListener.onFailure();
        }
    }

}

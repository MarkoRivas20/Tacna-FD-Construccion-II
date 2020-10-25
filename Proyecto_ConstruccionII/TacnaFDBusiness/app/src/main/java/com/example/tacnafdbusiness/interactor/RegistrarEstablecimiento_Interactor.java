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

    @Override
    public void performCreateEstablishment(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo) {

        reference.child(establecimiento_modelo.getID_Establecimiento()).setValue(establecimiento_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    mListener.onSuccess();

                }
                else
                {
                    mListener.onFailure();
                }

            }
        });

    }

    @Override
    public void performUploadLogo(StorageReference reference, String Id_Establecimiento, Uri Image_Uri) {

        final StorageReference filePath = reference.child(Id_Establecimiento).child("Logo").child(Image_Uri.getLastPathSegment());

        filePath.putFile(Image_Uri).addOnFailureListener(new OnFailureListener() {
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

    @Override
    public void performUploadDocument(StorageReference reference, String Id_Establecimiento, Uri Document_Uri) {

        final StorageReference filePath = reference.child(Id_Establecimiento).child("Documento").child(Document_Uri.getLastPathSegment());

        filePath.putFile(Document_Uri).addOnFailureListener(new OnFailureListener() {
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

    @Override
    public void performGetSessionData(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String id_Usuario = sharedPref.getString("id_usuario","");

        if(id_Usuario.length() != 0){
            mListener.onSuccessGetSessionData(id_Usuario);
        }
        else{
            mListener.onFailure();
        }
    }

}

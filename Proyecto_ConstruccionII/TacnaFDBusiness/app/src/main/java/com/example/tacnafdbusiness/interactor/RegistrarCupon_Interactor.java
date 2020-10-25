package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.RegistrarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrarCupon_Interactor implements RegistrarCupon.Interactor {

    private RegistrarCupon.onOperationListener mListener;

    public RegistrarCupon_Interactor(RegistrarCupon.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performSaveCoupon(DatabaseReference Database_Reference, Cupon_Modelo cupon_modelo) {

        Database_Reference.child(cupon_modelo.getId_Cupon()).setValue(cupon_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    mListener.onSuccessSaveCoupon();

                }
                else
                {
                    mListener.onFailureSaveCoupon();
                }
            }
        });
    }

    @Override
    public void performUploadCouponImage(StorageReference Storage_Reference, String Id_Establecimiento, Uri Imagen_Uri) {

        final StorageReference filePath = Storage_Reference.child(Id_Establecimiento).child("Cupon").child(Imagen_Uri.getLastPathSegment());

        filePath.putFile(Imagen_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUploadCouponImage();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        mListener.onSuccessUploadCouponImage(uri.toString());
                    }
                });
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

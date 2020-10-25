package com.example.tacnafdbusiness.interactor;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ModificarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ModificarCupon_Interactor implements ModificarCupon.Interactor {

    private ModificarCupon.onOperationListener mListener;
    private ValueEventListener valueEventListener;

    public ModificarCupon_Interactor(ModificarCupon.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performUpdateCouponData(DatabaseReference reference, Cupon_Modelo cupon_modelo) {

        reference.child(cupon_modelo.getId_Cupon()).setValue(cupon_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    mListener.onSuccessUpdateCouponData();

                }
                else
                {
                    mListener.onFailureUpdateCouponData();
                }
            }
        });

    }

    @Override
    public void performUpdateCouponImage(StorageReference Storage_Reference, final DatabaseReference Database_Reference, final String Url_Imagen_Actual, String Id_Establecimiento, final String Id_Cupon, Uri Imagen_Uri) {

        final StorageReference filePath = Storage_Reference.child(Id_Establecimiento).child("Cupon").child(Imagen_Uri.getLastPathSegment());

        filePath.putFile(Imagen_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mListener.onFailureUpdateCouponImage();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {

                        Database_Reference.child(Id_Cupon).child("url_Imagen").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    mListener.onSuccessUpdateCouponImage(uri.toString());

                                }
                                else
                                {
                                    mListener.onFailureUpdateCouponImage();
                                }
                            }
                        });

                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url_Imagen_Actual);
                        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }
                });

            }
        });

    }

    @Override
    public void performGetCouponData(final DatabaseReference Database_Reference, final String Id_Cupon) {

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Cupon_Modelo cupon_modelo = snapshot.getValue(Cupon_Modelo.class);
                mListener.onSuccessGetCouponData(cupon_modelo);

                Database_Reference.child(Id_Cupon).removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetCouponData();
            }
        };
        Database_Reference.child(Id_Cupon).addValueEventListener(valueEventListener);

    }
}

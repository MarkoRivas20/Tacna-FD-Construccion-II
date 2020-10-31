package com.example.tacnafddelivery.interactor;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafddelivery.interfaces.RegistrarUsuario;
import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistroUsuario_Interactor implements RegistrarUsuario.Interactor {

    private RegistrarUsuario.onOperationListener mListener;

    public RegistroUsuario_Interactor(RegistrarUsuario.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performCreateUser(final DatabaseReference reference, final Usuario_Modelo usuario_modelo) {

        Query query=reference.orderByChild("correo_Electronico").equalTo(usuario_modelo.getCorreo_Electronico());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    booleano = true;

                }

                if(!booleano){

                    reference.child(usuario_modelo.getID_Usuario_Repartidor()).setValue(usuario_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                else {
                    mListener.onUsedMail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailure();
            }
        });
    }

    @Override
    public void performUploadPhoto(StorageReference reference, String Id_Usuario, Uri Image_Uri) {

        final StorageReference filePath = reference.child(Id_Usuario).child(Image_Uri.getLastPathSegment());

        filePath.putFile(Image_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUploadPhoto();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        mListener.onSuccessUploadPhoto(uri.toString());

                    }
                });

            }
        });
    }
}

package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.CRUDImagenes;
import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CRUDImagenes_Interactor implements CRUDImagenes.Interactor {

    private CRUDImagenes.onOperationListener mListener;

    private ArrayList<ImagenEstablecimiento_Modelo> Imagenes_Establecimiento = new ArrayList<>();

    public CRUDImagenes_Interactor(CRUDImagenes.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Subiendo la imagen del Establecimiento al Firebase Storage*/

    @Override
    public void performUploadImage(StorageReference Storage_Reference, final DatabaseReference Database_Reference,
                                   final ImagenEstablecimiento_Modelo Imagen_Establecimiento, Uri Imagen_Uri) {

        final StorageReference filePath = Storage_Reference.child(Imagen_Establecimiento.getID_Establecimiento()).child("Imagenes").child(Imagen_Uri.getLastPathSegment());

        filePath.putFile(Imagen_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUploadImage();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        /*Guardar la URL de la imagen a la base de datos*/

                        Imagen_Establecimiento.setUrl_Imagen(uri.toString());
                        Database_Reference.child(Imagen_Establecimiento.getID_Imagen_Establecimiento()).setValue(Imagen_Establecimiento).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    mListener.onSuccessUploadImage();

                                }
                                else
                                {
                                    mListener.onFailureUploadImage();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    /*Borrar una imagen del establecimiento*/

    @Override
    public void performDeleteImage(DatabaseReference Database_Reference, String ID_Imagen_Establecimiento, final String Url_Imagen) {

        /*Borrando el registro de la base de datos*/

        Database_Reference.child(ID_Imagen_Establecimiento).removeValue().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteImage();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        /*Borrando la imagen del Storage*/

        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url_Imagen);

        reference.delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteImage();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mListener.onSuccessDeleteImage();
            }
        });

    }

    /*Obteniendo todas las imagenes registradads del establecimiento*/

    @Override
    public void performGetAllImages(DatabaseReference Database_Reference, String ID_Establecimiento) {

        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Boolean Existe_Imagen = false;
                Imagenes_Establecimiento.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Imagen = true;
                    ImagenEstablecimiento_Modelo Imagen_Establecimiento = postSnapshot.getValue(ImagenEstablecimiento_Modelo.class);
                    Imagenes_Establecimiento.add(Imagen_Establecimiento);
                }

                mListener.onSuccessGetAllImages(Imagenes_Establecimiento, Existe_Imagen);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetAllImages();
            }
        });


    }

    /*Obteniendo el ID del establecimiento del SharedPreferences*/

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

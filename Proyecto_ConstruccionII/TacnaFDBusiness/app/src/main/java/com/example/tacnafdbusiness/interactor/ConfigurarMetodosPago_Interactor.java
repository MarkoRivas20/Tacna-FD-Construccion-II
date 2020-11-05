package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ConfigurarMetodosPago;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
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

public class ConfigurarMetodosPago_Interactor implements ConfigurarMetodosPago.Interactor {

    private ConfigurarMetodosPago.onOperationListener mListener;
    private ValueEventListener valueEventListener;

    public ConfigurarMetodosPago_Interactor(ConfigurarMetodosPago.onOperationListener mListener) {
        this.mListener = mListener;
    }
    /*Obtener los metodos de pago que estan registrados en ele stablecimiento*/
    @Override
    public void performGetPaymentsMethods(final DatabaseReference Database_Reference, final String ID_Establecimiento) {

        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postsnapshot: snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postsnapshot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetPaymentsMethods(Establecimiento);
                    query.removeEventListener(valueEventListener);//Remueve el evento de Escucha
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    /*Actualizar los métodos del pago que tiene el establecimiento*/
    @Override
    public void performUpdatePaymentsMethods(final DatabaseReference Database_Reference, final String ID_Establecimiento, final String Codigo_Paypal, final String Codigo_Culqi, final String Url_Qr) {

        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postsnapshot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postsnapshot.getValue(Establecimiento_Modelo.class);

                    Establecimiento.setCodigo_Paypal(Codigo_Paypal);
                    Establecimiento.setCodigo_Culqi(Codigo_Culqi);
                    Establecimiento.setUrl_Qr(Url_Qr);
                    /*Actualización del Establecimiento con los nuevos metodos de pago en la base de datos */
                    Database_Reference.child(ID_Establecimiento).setValue(Establecimiento).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                mListener.onSuccessUpdatePaymentsMethods();
                            }
                            else
                            {
                                mListener.onFailureUpdatePaymentsMethods();
                            }

                        }
                    });
                    query.removeEventListener(valueEventListener);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }
    /*Subir la imagen del QR al Firebase Storage*/
    @Override
    public void performUpdateQRImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri) {
        final StorageReference filePath = Storage_Reference.child(ID_Establecimiento).child("MetodosPago").child(Imagen_Uri.getLastPathSegment());

        filePath.putFile(Imagen_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUpdateQRImage();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mListener.onSuccessUpdateQRImage(uri.toString());
                    }
                });
            }
        });
    }
    /*Eliminar la imagen QR del Firebase Storage*/
    @Override
    public void performDeleteQRImage(String Url_Qr) {

        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url_Qr);

        reference.delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteQRImage();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mListener.onSuccessDeleteQRImage();
            }
        });
    }
    /*Obteniendo el ID del Establecimiento del SharedPreferences*/
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

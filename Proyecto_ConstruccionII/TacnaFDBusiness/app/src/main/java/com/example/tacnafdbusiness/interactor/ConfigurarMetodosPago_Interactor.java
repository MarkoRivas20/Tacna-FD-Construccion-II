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

    @Override
    public void performGetPaymentsMethods(final DatabaseReference reference, final String id_establecimiento) {

        final Query query = reference.orderByChild("id_Establecimiento").equalTo(id_establecimiento);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postsnapshot: snapshot.getChildren()){

                    Establecimiento_Modelo establecimiento_modelo = postsnapshot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetPaymentsMethods(establecimiento_modelo);
                    query.removeEventListener(valueEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void performUpdatePaymentsMethods(final DatabaseReference reference, final String id_establecimiento, final String Codigo_Paypal, final String Codigo_Culqi, final String Url_Qr) {

        final Query query = reference.orderByChild("id_Establecimiento").equalTo(id_establecimiento);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postsnapshot : snapshot.getChildren()){

                    Establecimiento_Modelo establecimiento = postsnapshot.getValue(Establecimiento_Modelo.class);

                    establecimiento.setCodigo_Paypal(Codigo_Paypal);
                    establecimiento.setCodigo_Culqi(Codigo_Culqi);
                    establecimiento.setUrl_Qr(Url_Qr);

                    reference.child(id_establecimiento).setValue(establecimiento).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
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

    @Override
    public void performUpdateQRImage(StorageReference reference, String id_establecimiento, Uri Imagen_Uri) {
        final StorageReference filePath = reference.child(id_establecimiento).child("MetodosPago").child(Imagen_Uri.getLastPathSegment());

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

    @Override
    public void performGetEstablishmentInfo(Context context) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(Id_Establecimiento.length() != 0){
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento);
        }

    }
}

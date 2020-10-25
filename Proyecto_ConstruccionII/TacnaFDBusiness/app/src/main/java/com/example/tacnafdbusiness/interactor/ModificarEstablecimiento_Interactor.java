package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ModificarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
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


public class ModificarEstablecimiento_Interactor implements ModificarEstablecimiento.Interactor {

    private ModificarEstablecimiento.onOperationListener mListener;

    private ValueEventListener valueEventListener;

    public ModificarEstablecimiento_Interactor(ModificarEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performUpdateEstablismentData(DatabaseReference reference, Establecimiento_Modelo establecimiento_modelo) {

        reference.child(establecimiento_modelo.getID_Establecimiento()).setValue(establecimiento_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    mListener.onSuccessUpdateEstablismentData();

                }
                else
                {
                    mListener.onFailureUpdateEstablismentData();
                }

            }
        });

    }

    @Override
    public void performUpdateEstablismentLogo(final StorageReference Storage_Reference, final DatabaseReference Database_Reference, final String Url_Logo_Actual, final String Id_Establecimiento, final Uri Logo_Uri) {

        final StorageReference filePath = Storage_Reference.child(Id_Establecimiento).child("Logo").child(Logo_Uri.getLastPathSegment());

        filePath.putFile(Logo_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUpdateEstablismentLogo();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {

                        Database_Reference.child(Id_Establecimiento).child("url_Imagen_Logo").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    mListener.onSuccessUpdateEstablismentLogo(uri.toString());

                                }
                                else
                                {
                                    mListener.onFailureUpdateEstablismentLogo();
                                }

                            }
                        });

                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url_Logo_Actual);
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
    public void performUpdateEstablismentDocument(StorageReference Storage_Reference, final DatabaseReference Database_Reference, final String Url_Document_Actual, final String Id_Establecimiento, Uri Documento_Uri) {

        final StorageReference filePath = Storage_Reference.child(Id_Establecimiento).child("Documento").child(Documento_Uri.getLastPathSegment());

        filePath.putFile(Documento_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUpdateEstablismentDocument();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {

                        Database_Reference.child(Id_Establecimiento).child("url_Imagen_Documento").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    mListener.onSuccessUpdateEstablismentDocument(uri.toString());
                                }
                                else
                                {
                                    mListener.onFailureUpdateEstablismentDocument();
                                }

                            }
                        });

                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url_Document_Actual);
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
    public void performGetEstablishmentInfo(Context context) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");
        String Url_Documento = sharedPref.getString("url_documento","");
        String Url_Logo = sharedPref.getString("url_logo","");

        if(Id_Establecimiento.length() != 0){
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento, Url_Logo, Url_Documento);
        }

    }

    @Override
    public void performGetEstablishmentData(final DatabaseReference reference, final String Id_Establecimiento) {

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Establecimiento_Modelo establecimiento_modelo = snapshot.getValue(Establecimiento_Modelo.class);
                mListener.onSuccessGetEstablishmentData(establecimiento_modelo);

                reference.child(Id_Establecimiento).removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentData();
            }
        };
        reference.child(Id_Establecimiento).addValueEventListener(valueEventListener);

    }

    @Override
    public void performUpdateEstablishmentInfo(Context context, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {

        SharedPreferences sharedPref = context.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre_establecimiento", Nombre_Establecimiento);
        editor.putString("url_logo", Url_Logo);
        editor.putString("url_documento", Url_Documento);
        editor.apply();

        mListener.onSuccessUpdateEstablishmentInfo();
    }

    @Override
    public void performGetSessionData(Context context) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Id_Usuario = sharedPref.getString("id_usuario","");

        if(Id_Usuario.length() != 0){
            mListener.onSuccessGetSessionData(Id_Usuario);
        }
    }
}

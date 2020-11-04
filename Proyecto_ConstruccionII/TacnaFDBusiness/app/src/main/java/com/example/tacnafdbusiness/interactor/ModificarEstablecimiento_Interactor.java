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
    public void performUpdateEstablismentData(DatabaseReference Database_Reference, Establecimiento_Modelo Establecimiento) {

        Database_Reference.child(Establecimiento.getID_Establecimiento()).setValue(Establecimiento).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
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
    public void performUpdateEstablismentLogo(final StorageReference Storage_Reference, final DatabaseReference Database_Reference, final String Url_Logo_Actual, final String ID_Establecimiento, final Uri Logo_Uri) {

        final StorageReference filePath = Storage_Reference.child(ID_Establecimiento).child("Logo").child(Logo_Uri.getLastPathSegment());

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

                        Database_Reference.child(ID_Establecimiento).child("url_Imagen_Logo").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
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
    public void performUpdateEstablismentDocument(StorageReference Storage_Reference, final DatabaseReference Database_Reference, final String Url_Document_Actual, final String ID_Establecimiento, Uri Documento_Uri) {

        final StorageReference filePath = Storage_Reference.child(ID_Establecimiento).child("Documento").child(Documento_Uri.getLastPathSegment());

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

                        Database_Reference.child(ID_Establecimiento).child("url_Imagen_Documento").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
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
    public void performGetEstablishmentInfo(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");
        String Url_Documento = sharedPref.getString("url_documento","");
        String Url_Logo = sharedPref.getString("url_logo","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(ID_Establecimiento, Url_Logo, Url_Documento);
        }

    }

    @Override
    public void performGetEstablishmentData(final DatabaseReference Database_Reference, final String ID_Establecimiento) {

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Establecimiento_Modelo Establecimiento = snapshot.getValue(Establecimiento_Modelo.class);
                mListener.onSuccessGetEstablishmentData(Establecimiento);

                Database_Reference.child(ID_Establecimiento).removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentData();
            }
        };
        Database_Reference.child(ID_Establecimiento).addValueEventListener(valueEventListener);

    }

    @Override
    public void performUpdateEstablishmentInfo(Context Contexto, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre_establecimiento", Nombre_Establecimiento);
        editor.putString("url_logo", Url_Logo);
        editor.putString("url_documento", Url_Documento);
        editor.apply();

        mListener.onSuccessUpdateEstablishmentInfo();
    }

    @Override
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
    }
}

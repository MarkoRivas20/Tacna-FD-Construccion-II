package com.example.tacnafddelivery.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafddelivery.interfaces.ModificarUsuario;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ModificarUsuario_Interactor implements ModificarUsuario.Interactor {

    private ModificarUsuario.onOperationListener mListener;
    private ValueEventListener valueEventListener;

    public ModificarUsuario_Interactor(ModificarUsuario.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performUpdateUserData(DatabaseReference reference, Usuario_Modelo usuario_modelo) {

        reference.child(usuario_modelo.getID_Usuario_Repartidor()).setValue(usuario_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    mListener.onSuccessUpdateUserData();

                }
                else
                {
                    mListener.onFailureUpdateUserData();
                }

            }
        });
    }

    @Override
    public void performUpdateUserPhoto(StorageReference Storage_Reference, final DatabaseReference Database_Reference, final String Url_Logo_Actual, final String Id_Usuario, Uri Foto_Uri) {

        final StorageReference filePath = Storage_Reference.child(Id_Usuario).child(Foto_Uri.getLastPathSegment());

        filePath.putFile(Foto_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureUpdateUserPhoto();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {

                        Database_Reference.child(Id_Usuario).child("url_Foto").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    mListener.onSuccessUpdateUserPhoto(uri.toString());

                                }
                                else
                                {
                                    mListener.onFailureUpdateUserPhoto();
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
    public void performShowUserData(DatabaseReference reference, String correo_electronico) {

        Query query=reference.orderByChild("correo_Electronico").equalTo(correo_electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    booleano = true;
                    Usuario_Modelo usuario_modelo = postSnapshot.getValue(Usuario_Modelo.class);

                    mListener.onSuccessShowUserData(usuario_modelo);

                }

                if(!booleano){
                    mListener.onFailureShowUserData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureShowUserData();
            }
        });

    }

    @Override
    public void performSaveSession(Context context, String nombre_usuario, String Url_Foto) {

        SharedPreferences sharedPref = context.getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre_usuario", nombre_usuario);
        editor.putString("url_foto", Url_Foto);
        editor.apply();
        mListener.onSuccessSaveSession();
    }

    @Override
    public void performGetSessionData(Context context) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Correo_Electronico = sharedPref.getString("correo_electronico","");

        if(Correo_Electronico.length() != 0){
            mListener.onSuccessGetSessionData(Correo_Electronico);
        }
    }
}

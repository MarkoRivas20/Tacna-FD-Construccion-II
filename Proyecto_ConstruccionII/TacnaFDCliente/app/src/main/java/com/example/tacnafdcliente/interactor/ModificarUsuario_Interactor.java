package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.ModificarUsuario;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ModificarUsuario_Interactor implements ModificarUsuario.Interactor {

    private ModificarUsuario.onOperationListener mListener;

    public ModificarUsuario_Interactor(ModificarUsuario.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performUpdateUserData(DatabaseReference reference, Usuario_Modelo usuario_modelo) {

        reference.child(usuario_modelo.getID_Usuario_Cliente()).setValue(usuario_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void performSaveSession(Context context, String nombre_usuario) {

        SharedPreferences sharedPref = context.getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre_usuario", nombre_usuario);
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

package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ModificarUsuario;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ModificarUsuario_Interactor  implements ModificarUsuario.Interactor{

    private ModificarUsuario.onOperationListener mListener;
    private ValueEventListener valueEventListener;

    public ModificarUsuario_Interactor(ModificarUsuario.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performUpdateUser(final DatabaseReference Database_Reference, final Usuario_Modelo Usuario) {

        final Query query = Database_Reference.orderByChild("id_Usuario").equalTo(Usuario.getID_Usuario());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postsnapshot : snapshot.getChildren())
                {
                    Database_Reference.child(Usuario.getID_Usuario()).setValue(Usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                mListener.onSuccess();
                            }
                            else
                            {
                                mListener.onFailure();
                            }

                        }
                    });
                    query.removeEventListener(valueEventListener);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailure();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);




    }

    @Override
    public void performShowUserData(DatabaseReference Database_Reference, String Correo_Electronico) {

        Query query=Database_Reference.orderByChild("correo_Electronico").equalTo(Correo_Electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    booleano = true;
                    Usuario_Modelo Usuario = postSnapshot.getValue(Usuario_Modelo.class);

                    mListener.onSuccessShowUserData(Usuario);
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
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Correo_Electronico = sharedPref.getString("correo_electronico","");

        if(Correo_Electronico.length() != 0)
        {
            mListener.onSuccess(Correo_Electronico);
        }
        else{
            mListener.onFailure();
        }
    }
}

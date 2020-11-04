package com.example.tacnafdbusiness.interactor;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.RegistrarUsuario;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistroUsuario_Interactor implements RegistrarUsuario.Interactor {

    private RegistrarUsuario.onOperationListener mListener;

    public RegistroUsuario_Interactor(RegistrarUsuario.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performCreateUser(final DatabaseReference Database_Reference, final Usuario_Modelo Usuario) {

        Query query = Database_Reference.orderByChild("correo_Electronico").equalTo(Usuario.getCorreo_Electronico());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    booleano = true;
                }

                if(!booleano)
                {
                    Database_Reference.child(Usuario.getID_Usuario()).setValue(Usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                else
                {
                    mListener.onUsedMail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailure();
            }
        });


    }
}

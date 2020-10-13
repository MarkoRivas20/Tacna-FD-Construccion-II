package com.example.tacnafdbusiness.interactor;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.Registro;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistroUsuario_Interactor implements Registro.Interactor {

    private Registro.onOperationListener mListener;

    private ArrayList<Usuario_Modelo> usuario_modelos=new ArrayList<>();

    public RegistroUsuario_Interactor(Registro.onOperationListener mListener) {
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

                    reference.child(usuario_modelo.getID_Usuario()).setValue(usuario_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}

package com.example.tacnafdbusiness.interactor;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.BuscarEmail;
import com.example.tacnafdbusiness.interfaces.RecuperarContrasena;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RecuperarContrasena_Interactor implements RecuperarContrasena.Interactor {

    private RecuperarContrasena.onOperationListener mListener;

    public RecuperarContrasena_Interactor(RecuperarContrasena.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performRestorePassword(final DatabaseReference reference, String correo_electronico, final String nueva_contrasena) {

        Query query=reference.orderByChild("correo_Electronico").equalTo(correo_electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    Usuario_Modelo usuario_modelo = postSnapshot.getValue(Usuario_Modelo.class);

                    usuario_modelo.setContrasena(nueva_contrasena);

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                mListener.onFailure();
            }
        });
    }
}

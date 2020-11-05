package com.example.tacnafdbusiness.interactor;

import androidx.annotation.NonNull;

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

    /*Modificando la contraseña del usuario en la base de datos*/
    @Override
    public void performRestorePassword(final DatabaseReference Database_Reference, String Correo_Electronico, final String Nueva_Contrasena) {

        Query query = Database_Reference.orderByChild("correo_Electronico").equalTo(Correo_Electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Usuario_Modelo usuario_modelo = postSnapshot.getValue(Usuario_Modelo.class);

                    usuario_modelo.setContrasena(Nueva_Contrasena);

                    Database_Reference.child(usuario_modelo.getID_Usuario()).setValue(usuario_modelo).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                mListener.onFailure();
            }
        });
    }
}

package com.example.tacnafdbusiness.interactor;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login_Interactor implements Login.Interactor {

    private Login.onOperationListener mListener;

    private ArrayList<Usuario_Modelo> usuario_modelos=new ArrayList<>();

    public Login_Interactor(Login.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performLogIn(DatabaseReference reference, String correo_electronico, final String contrasena) {

        Query query=reference.orderByChild("correo_Electronico").equalTo(correo_electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    Usuario_Modelo usuario_modelo = postSnapshot.getValue(Usuario_Modelo.class);

                    if(usuario_modelo.getContrasena().equals(contrasena)){
                        booleano = true;
                        mListener.onSuccess();
                    }
                }

                if(!booleano){
                    mListener.onFailure();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailure();
            }
        });
    }
}

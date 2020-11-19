package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.Login;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login_Interactor implements Login.Interactor {

    private Login.onOperationListener mListener;

    private ArrayList<Usuario_Modelo> usuario_modelos = new ArrayList<>();

    private String Nombre_Usuario = "";

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
                        Nombre_Usuario = usuario_modelo.getNombre() + " " + usuario_modelo.getApellido();
                        mListener.onSuccess(Nombre_Usuario, usuario_modelo.getID_Usuario_Cliente());
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

    @Override
    public void performSaveSession(Context context, String correo_electronico, String nombre_usuario, String id_usuario) {

        SharedPreferences sharedPref = context.getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("correo_electronico", correo_electronico);
        editor.putString("nombre_usuario", nombre_usuario);
        editor.putString("id_usuario", id_usuario);
        editor.apply();

    }

    @Override
    public void performCheckSession(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Correo_Electronico = sharedPref.getString("correo_electronico","");

        if(Correo_Electronico.length() != 0){
            mListener.onSuccessCheck();
        }

    }
}

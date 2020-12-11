package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

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

    private String Nombre_Usuario = "";

    public Login_Interactor(Login.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Buscando un usuario a travez del correo_Electronico y la Contrasena*/

    @Override
    public void performLogIn(DatabaseReference Database_Reference, String Correo_Electronico, final String Contrasena) {


        Query query = Database_Reference.orderByChild("correo_Electronico").equalTo(Correo_Electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Usuario_Modelo Usuario = postSnapshot.getValue(Usuario_Modelo.class);

                    if(Usuario.getContrasena().equals(Contrasena))
                    {
                        booleano = true;
                        Nombre_Usuario = Usuario.getNombre() + " " + Usuario.getApellido();
                        mListener.onSuccess(Nombre_Usuario, Usuario.getID_Usuario());
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

    /*Guardando el id_usuario, nombre_usuario, correo_electronico en un SharedPreferences*/

    @Override
    public void performSaveSession(Context Contexto, String Correo_Electronico, String Nombre_Usuario, String ID_Usuario) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("correo_electronico", Correo_Electronico);
        editor.putString("nombre_usuario", Nombre_Usuario);
        editor.putString("id_usuario", ID_Usuario);
        editor.apply();

    }

    /*Comprobando el inicio de sesion*/

    @Override
    public void performCheckSession(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String Correo_Electronico = sharedPref.getString("correo_electronico","");

        if(Correo_Electronico.length() != 0)
        {
            mListener.onSuccessCheck();
        }
    }
}

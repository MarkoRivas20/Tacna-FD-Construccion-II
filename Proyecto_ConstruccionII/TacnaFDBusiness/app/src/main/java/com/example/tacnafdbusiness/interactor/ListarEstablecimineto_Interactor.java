package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarEstablecimineto_Interactor implements ListarEstablecimiento.Interactor {

    private ListarEstablecimiento.onOperationListener mListener;

    private ArrayList<Establecimiento_Modelo> establecimiento_modelos = new ArrayList<>();

    private ArrayList<Establecimiento_Modelo> lista_filtrada;

    Boolean buscar_establecimiento = false;

    public ListarEstablecimineto_Interactor(ListarEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performSearchEstablishment(DatabaseReference reference, String ID_Usuario) {

        Query query=reference.orderByChild("id_Usuario_Propietario").startAt(ID_Usuario);



        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean Existe_Establecimiento = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Existe_Establecimiento = true;
                    Establecimiento_Modelo establecimiento_modelo = postSnapshot.getValue(Establecimiento_Modelo.class);
                    establecimiento_modelos.add(establecimiento_modelo);
                }
                mListener.onSuccess(establecimiento_modelos, Existe_Establecimiento);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                mListener.onFailure();
            }
        });

    }

    @Override
    public void performGetSessionData(Context context) {

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String id_Usuario = sharedPref.getString("id_usuario","");

        if(id_Usuario.length() != 0){
            mListener.onSuccessGetSessionData(id_Usuario);
        }
        else{
            mListener.onFailure();
        }

    }

    @Override
    public void performSaveEstablishmentInfo(Context context, String Id_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {
        SharedPreferences sharedPref = context.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_establecimiento", Id_Establecimiento);
        editor.putString("nombre_establecimiento", Nombre_Establecimiento);
        editor.putString("url_logo", Url_Logo);
        editor.putString("url_documento", Url_Documento);
        editor.apply();
    }

    @Override
    public void performFilterEstablishment(ArrayList<Establecimiento_Modelo> establecimientos, String Nombre_Establecimiento) {
        lista_filtrada = new ArrayList<>();

        for (Establecimiento_Modelo item:establecimientos)
        {
            if(item.getNombre().toLowerCase().contains(Nombre_Establecimiento.toLowerCase()))
            {
                lista_filtrada.add(item);
                buscar_establecimiento = true;
            }
        }
        mListener.onSuccessFilter(lista_filtrada, buscar_establecimiento);
    }
}

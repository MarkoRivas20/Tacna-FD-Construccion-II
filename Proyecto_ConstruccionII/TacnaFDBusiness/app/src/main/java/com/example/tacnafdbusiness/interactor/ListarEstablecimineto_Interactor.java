package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarEstablecimineto_Interactor implements ListarEstablecimiento.Interactor {

    private ListarEstablecimiento.onOperationListener mListener;

    private ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();

    private ArrayList<Establecimiento_Modelo> Lista_Filtrada;

    Boolean Buscar_Establecimiento = false;

    public ListarEstablecimineto_Interactor(ListarEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Obteniendo los establecimientos registrados por el usuario*/
    @Override
    public void performSearchEstablishment(DatabaseReference Database_Reference, String ID_Usuario) {

        Query query = Database_Reference.orderByChild("id_Usuario_Propietario").startAt(ID_Usuario);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean Existe_Establecimiento = false;
                Establecimientos.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Establecimiento = true;
                    Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                    Establecimientos.add(Establecimiento);
                }
                mListener.onSuccess(Establecimientos, Existe_Establecimiento);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                mListener.onFailure();
            }
        });

    }

    /*Obteniendo el ID de usuario del SharedPreferences*/
    @Override
    public void performGetSessionData(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
        else{
            mListener.onFailure();
        }

    }
    /*Guardando el id_establecimiento, nombre_establecimiento, url_logo, url_documento en un SharedPreferences*/
    @Override
    public void performSaveEstablishmentInfo(Context Contexto, String Id_Establecimiento, String Nombre_Establecimiento, String Url_Logo, String Url_Documento) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_establecimiento", Id_Establecimiento);
        editor.putString("nombre_establecimiento", Nombre_Establecimiento);
        editor.putString("url_logo", Url_Logo);
        editor.putString("url_documento", Url_Documento);
        editor.apply();
    }

    /*Filtrar la lista de los establecientos por el Nombre_Establecimiento*/
    @Override
    public void performFilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento) {
        Lista_Filtrada = new ArrayList<>();

        for (Establecimiento_Modelo item:Establecimientos)
        {
            if(item.getNombre().toLowerCase().contains(Nombre_Establecimiento.toLowerCase()))
            {
                Lista_Filtrada.add(item);
                Buscar_Establecimiento = true;
            }
        }
        mListener.onSuccessFilter(Lista_Filtrada, Buscar_Establecimiento);
    }
}

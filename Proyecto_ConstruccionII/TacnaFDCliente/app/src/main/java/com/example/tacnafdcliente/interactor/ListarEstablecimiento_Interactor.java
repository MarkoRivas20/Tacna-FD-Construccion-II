package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.ListarEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarEstablecimiento_Interactor implements ListarEstablecimiento.Interactor {

    private ListarEstablecimiento.onOperationListener mListener;

    private ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();

    private ArrayList<Establecimiento_Modelo> Lista_Filtrada;

    Boolean Buscar_Establecimiento = false;

    public ListarEstablecimiento_Interactor(ListarEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetAllEstablishment(DatabaseReference Database_Reference) {

        Database_Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean Existe_Establecimiento = false;
                Establecimientos.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Establecimiento = true;
                    Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);

                    if(Establecimiento.getEstado().equals("Activo"))
                    {
                        Establecimientos.add(Establecimiento);
                    }

                }
                mListener.onSuccessGetAllEstablishment(Establecimientos, Existe_Establecimiento);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetAllEstablishment();
            }
        });
    }

    @Override
    public void performSaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_establecimiento", ID_Establecimiento);
        editor.putString("nombre_establecimiento", Nombre_Establecimiento);
        editor.apply();

    }

    @Override
    public void performFilterEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos, String Nombre_Establecimiento, String Categoria_Establecimiento,
                                           String Distrito_Establecimiento) {

        if(Categoria_Establecimiento.equals("Seleccione una Categoria"))
        {
            Categoria_Establecimiento="";
        }
        if(Distrito_Establecimiento.equals("Seleccione un Distrito"))
        {
            Distrito_Establecimiento="";
        }

        Lista_Filtrada = new ArrayList<>();

        for (Establecimiento_Modelo item:Establecimientos)
        {
            if(item.getNombre().toLowerCase().contains(Nombre_Establecimiento.toLowerCase())
                    & item.getCategoria().toLowerCase().contains(Categoria_Establecimiento.toLowerCase())
                    & item.getDistrito().toLowerCase().contains(Distrito_Establecimiento.toLowerCase()))
            {
                Lista_Filtrada.add(item);
                Buscar_Establecimiento = true;
            }
        }
        mListener.onSuccessFilter(Lista_Filtrada, Buscar_Establecimiento);

    }
}

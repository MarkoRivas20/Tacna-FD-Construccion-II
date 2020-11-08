package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarResena;
import com.example.tacnafdbusiness.modelo.Cliente_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarResena_Interactor implements ListarResena.Interactor {

    private ListarResena.onOperationListener mListener;

    private ArrayList<Resena_Modelo> Resenas = new ArrayList<>();

    private ValueEventListener valueEventListener;

    public ListarResena_Interactor(ListarResena.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Obteniendo las Reseñas registradas a un establecimiento*/

    @Override
    public void performGetReviews(DatabaseReference Database_Reference, String ID_Establecimiento) {
        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Resena = false;
                Resenas.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Existe_Resena = true;
                    Resena_Modelo Resena = postSnapshot.getValue(Resena_Modelo.class);
                    Resenas.add(Resena);
                }
                mListener.onSuccessGetReviews(Resenas, Existe_Resena);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetReviews();
            }
        });
    }

    /*Buscando el nombre del cliente por el id_Usuario_Cliente*/

    @Override
    public void performSearchClientName(DatabaseReference Database_Reference, final ArrayList<Resena_Modelo> Resenas) {

        /*Recorriendo todas la reseñas registradas al establecimiento*/

        for(int i = 0; i < Resenas.size(); i++)
        {
            final int Posicion = i;

            final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(Resenas.get(i).getID_Usuario_Cliente());

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Cliente_Modelo Cliente = postSnapshot.getValue(Cliente_Modelo.class);
                        Resenas.get(Posicion).setNombre_Cliente(Cliente.getNombre() + " " + Cliente.getApellido());
                    }
                    mListener.onSuccessSearchClientName(Resenas);
                    query.removeEventListener(valueEventListener);//Removiendo el evento de escucha
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchClientName();
                }
            };

            query.addValueEventListener(valueEventListener);

        }
    }

    /*Obteniendo el ID del establecimiento del SharedPreferences*/

    @Override
    public void performGetEstablishmentInfo(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(Id_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento);
        }
    }
}

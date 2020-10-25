package com.example.tacnafdbusiness.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.interfaces.ListarResena;
import com.example.tacnafdbusiness.modelo.Cliente_Modelo;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarResena_Interactor implements ListarResena.Interactor {

    private ListarResena.onOperationListener mListener;

    private ArrayList<Resena_Modelo> resena_modelos = new ArrayList<>();

    private ValueEventListener valueEventListener;

    public ListarResena_Interactor(ListarResena.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetReviews(DatabaseReference reference, String Id_Establecimiento) {
        Query query = reference.orderByChild("id_Establecimiento").equalTo(Id_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean Existe_Resena = false;
                resena_modelos.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Existe_Resena = true;
                    Resena_Modelo resena_modelo = postSnapshot.getValue(Resena_Modelo.class);
                    resena_modelos.add(resena_modelo);
                }
                mListener.onSuccessGetReviews(resena_modelos, Existe_Resena);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetReviews();
            }
        });
    }

    @Override
    public void performSearchClientName(DatabaseReference reference, final ArrayList<Resena_Modelo> resena_modelos) {

        for(int i = 0; i < resena_modelos.size(); i++){

            final int posicion = i;

            final Query query = reference.orderByChild("id_Usuario_Cliente").equalTo(resena_modelos.get(i).getID_Usuario_Cliente());

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren()){

                        Cliente_Modelo cliente_modelo = postSnapshot.getValue(Cliente_Modelo.class);
                        resena_modelos.get(posicion).setNombre_Cliente(cliente_modelo.getNombre() + " " + cliente_modelo.getApellido());
                    }
                    mListener.onSuccessSearchClientName(resena_modelos);
                    query.removeEventListener(valueEventListener);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchClientName();
                }
            };

            query.addValueEventListener(valueEventListener);

        }
    }

    @Override
    public void performGetEstablishmentInfo(Context context) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String Id_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(Id_Establecimiento.length() != 0){
            mListener.onSuccessGetEstablishmentInfo(Id_Establecimiento);
        }
    }
}

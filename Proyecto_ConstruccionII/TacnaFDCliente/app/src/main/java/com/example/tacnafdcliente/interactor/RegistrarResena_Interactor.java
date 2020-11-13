package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.RegistrarResena;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistrarResena_Interactor implements RegistrarResena.Interactor {

    private RegistrarResena.onOperationListener mListener;

    private ArrayList<Resena_Modelo> Resenas = new ArrayList<>();

    private ValueEventListener valueEventListener_Search_Client_Name;
    private ValueEventListener valueEventListener_Get_Current_Review;
    private ValueEventListener valueEventListener_Update_Establishment;

    public RegistrarResena_Interactor(RegistrarResena.onOperationListener mListener) {
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

            valueEventListener_Search_Client_Name = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        Usuario_Modelo Cliente = postSnapshot.getValue(Usuario_Modelo.class);
                        Resenas.get(Posicion).setNombre_Cliente(Cliente.getNombre() + " " + Cliente.getApellido());
                    }
                    mListener.onSuccessSearchClientName(Resenas);
                    query.removeEventListener(valueEventListener_Search_Client_Name);//Removiendo el evento de escucha
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    mListener.onFailureSearchClientName();
                }
            };

            query.addListenerForSingleValueEvent(valueEventListener_Search_Client_Name);

        }
    }

    @Override
    public void performSaveReview(DatabaseReference Database_Reference, Resena_Modelo Resena) {

        Database_Reference.child(Resena.getID_Resena()).setValue(Resena).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    mListener.onSuccessSaveReview();
                }
                else
                {
                    mListener.onFailureSaveReview();
                }

            }
        });
    }

    @Override
    public void performUpdateEstablishment(final DatabaseReference Database_Reference, final String ID_Establecimiento, final Double Calificacion, final int Total_Resenas) {

        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener_Update_Establishment = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                    Establecimiento.setPuntuacion(Calificacion);
                    Establecimiento.setTotalResenas(Total_Resenas);

                    Database_Reference.child(ID_Establecimiento).setValue(Establecimiento).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                mListener.onSuccessUpdateEstablishment();
                            }
                            else
                            {
                                mListener.onFailureUpdateEstablishment();
                            }
                        }
                    });
                    query.removeEventListener(valueEventListener_Update_Establishment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureUpdateEstablishment();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_Update_Establishment);


    }

    @Override
    public void performGetCurrentReviews(DatabaseReference Database_Reference, String ID_Establecimiento) {
        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener_Get_Current_Review = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Resenas.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Resena_Modelo Resena = postSnapshot.getValue(Resena_Modelo.class);
                    Resenas.add(Resena);
                }
                mListener.onSuccessGetCurrentReviews(Resenas);
                query.removeEventListener(valueEventListener_Get_Current_Review);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetCurrentReviews();
            }
        };

        query.addValueEventListener(valueEventListener_Get_Current_Review);

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

    @Override
    public void performGetSessionData(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("login_usuario", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");

        if(ID_Usuario.length() != 0)
        {
            mListener.onSuccessGetSessionData(ID_Usuario);
        }
    }
}
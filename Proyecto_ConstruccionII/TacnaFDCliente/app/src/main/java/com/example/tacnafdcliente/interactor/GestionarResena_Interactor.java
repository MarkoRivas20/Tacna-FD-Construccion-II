package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.GestionarResena;
import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GestionarResena_Interactor implements GestionarResena.Interactor {

    private GestionarResena.onOperationListener mListener;

    private ArrayList<Resena_Modelo> Resenas = new ArrayList<>();
    private ArrayList<CuponUsuario_Modelo> Cupones_Usuario = new ArrayList<>();

    private ValueEventListener valueEventListener;
    private ValueEventListener valueEventListener_Get_Reviews;
    private ValueEventListener valueEventListener_Get_Current_Review;
    private ValueEventListener valueEventListener_Update_Establishment;
    private ValueEventListener valueEventListener_Get_Coupon_User;

    public GestionarResena_Interactor(GestionarResena.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Obteniendo las Reseñas registradas a un establecimiento*/

    @Override
    public void performGetReviews(DatabaseReference Database_Reference, String ID_Establecimiento, final String ID_Usuario) {
        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener_Get_Reviews = new ValueEventListener() {
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
                query.removeEventListener(valueEventListener_Get_Reviews);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetReviews();
            }
        };
        query.addValueEventListener(valueEventListener_Get_Reviews);

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
                        Usuario_Modelo Cliente = postSnapshot.getValue(Usuario_Modelo.class);
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

            query.addListenerForSingleValueEvent(valueEventListener);

        }
    }

    @Override
    public void performGetUserReview(DatabaseReference Database_Reference, final String ID_Establecimiento, String ID_Usuario) {
        final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(ID_Usuario);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapShot : snapshot.getChildren()){

                    Resena_Modelo Resena = postSnapShot.getValue(Resena_Modelo.class);

                    if(Resena.getID_Establecimiento().equals(ID_Establecimiento)){

                        mListener.onSuccessGetUserReview(Resena);
                        query.removeEventListener(valueEventListener);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetUserReview();
            }
        };

        query.addValueEventListener(valueEventListener);

    }

    @Override
    public void performUpdateReview(DatabaseReference Database_Reference, Resena_Modelo Resena) {

        Database_Reference.child(Resena.getID_Resena()).setValue(Resena).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    mListener.onSuccessUpdateReview();
                }
                else
                {
                    mListener.onFailureUpdateReview();
                }

            }
        });
    }

    @Override
    public void performDeleteReview(DatabaseReference Database_Reference, String ID_Resena) {

        Database_Reference.child(ID_Resena).removeValue().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteReview();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mListener.onSuccessDeleteReview();
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

    @Override
    public void performSaveNumberOfCoupons(Context Contexto, int Numero_Cupones) {
        SharedPreferences sharedPref = Contexto.getSharedPreferences("numero_cupones", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("numero_cupones", Numero_Cupones);

        editor.apply();
    }

    @Override
    public void performGetNumberOfCoupons(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("numero_cupones", Context.MODE_PRIVATE);
        int Numero_Cupones = sharedPref.getInt("numero_cupones",0);

        mListener.onSuccessGetNumberOfCoupons(Numero_Cupones);
    }

    @Override
    public void performGetCouponUserFromUser(DatabaseReference Database_Reference, String ID_Usuario) {
        final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(ID_Usuario);

        valueEventListener_Get_Coupon_User = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Cupones_Usuario.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    CuponUsuario_Modelo Cupon_Usuario = postSnapshot.getValue(CuponUsuario_Modelo.class);
                    if(Cupon_Usuario.getEstado().equals("Activo"))
                    {
                        Cupones_Usuario.add(Cupon_Usuario);
                    }

                }
                mListener.onSuccessGetCouponUserFromUser(Cupones_Usuario);
                query.removeEventListener(valueEventListener_Get_Coupon_User);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetCouponUserFromUser();
            }
        };

        query.addValueEventListener(valueEventListener_Get_Coupon_User);
    }

    @Override
    public void performDeleteCouponUser(DatabaseReference Database_Reference, String ID_Cupon_Usuario) {
        Database_Reference.child(ID_Cupon_Usuario).removeValue().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mListener.onFailureDeleteCouponUser();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mListener.onSuccessDeleteCouponUser();
            }
        });
    }
}

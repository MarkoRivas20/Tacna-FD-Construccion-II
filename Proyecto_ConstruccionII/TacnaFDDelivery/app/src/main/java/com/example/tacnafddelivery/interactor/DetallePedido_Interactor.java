package com.example.tacnafddelivery.interactor;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.tacnafddelivery.interfaces.DetallePedido;
import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.modelo.SeguimientoPedido_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetallePedido_Interactor implements DetallePedido.Interactor {

    private DetallePedido.onOperationListener mListener;

    private ValueEventListener valueEventListener_Get_Order_Info;
    private ValueEventListener valueEventListener_Get_Establishment_Info;
    private ValueEventListener valueEventListener_Get_Client_Name;

    public DetallePedido_Interactor(DetallePedido.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetOrderInfo(DatabaseReference Database_Reference, String ID_Pedido) {

        final Query query = Database_Reference.orderByChild("id_Pedido").equalTo(ID_Pedido);

        valueEventListener_Get_Order_Info = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Pedido_Modelo Pedido = postSnapshot.getValue(Pedido_Modelo.class);
                    mListener.onSuccessGetOrderInfo(Pedido);
                }

                query.removeEventListener(valueEventListener_Get_Order_Info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetOrderInfo();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_Get_Order_Info);
    }

    @Override
    public void performGetEstablishmentInfo(DatabaseReference Database_Reference, String ID_Establecimiento) {

        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        valueEventListener_Get_Establishment_Info = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetEstablishmentInfo(Establecimiento);
                }
                query.removeEventListener(valueEventListener_Get_Establishment_Info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentInfo();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_Get_Establishment_Info);
    }

    @Override
    public void performGetClientName(DatabaseReference Database_Reference, String ID_Usuario_Cliente) {

        final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(ID_Usuario_Cliente);

        valueEventListener_Get_Client_Name = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Cliente_Modelo Cliente = postSnapshot.getValue(Cliente_Modelo.class);
                    mListener.onSuccessGetClientName(Cliente);
                }
                query.removeEventListener(valueEventListener_Get_Client_Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetClientName();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_Get_Client_Name);
    }

    @Override
    public void performUpdateOrderStatus(DatabaseReference Database_Reference, String ID_Pedido) {
        Database_Reference.child(ID_Pedido).child("estado").setValue("En Camino").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    mListener.onSuccessUpdateOrderStatus();
                }
                else
                {
                    mListener.onFailureUpdateOrderStatus();
                }
            }
        });
    }

    @Override
    public void performSaveTrackingOrder(DatabaseReference Database_Reference, SeguimientoPedido_Modelo Seguimiento_Pedido) {
        Database_Reference.child(Seguimiento_Pedido.getID_Pedido()).setValue(Seguimiento_Pedido).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    mListener.onSuccessSaveTrackingOrder();
                }
                else
                {
                    mListener.onFailureSaveTrackingOrder();
                }
            }
        });
    }

    @Override
    public void performGetIDEstablishment(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetIDEstablishment(ID_Establecimiento);
        }
    }

    @Override
    public void performGetIDOrder(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_pedido", Context.MODE_PRIVATE);
        String ID_Pedido = sharedPref.getString("id_pedido","");

        if(ID_Pedido.length() != 0)
        {
            mListener.onSuccessGetIDOrder(ID_Pedido);
        }
    }

    @Override
    public void performSetBackPressed(View view) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                    {
                        mListener.onSuccessSetBackPressed(true);
                        return true;
                    }
                }
                mListener.onSuccessSetBackPressed(false);
                return false;

            }
        });
    }

    @Override
    public void performSaveTrackingOrderSharedPreference(Context Contexto, String Seguimiento) {
        SharedPreferences sharedPref = Contexto.getSharedPreferences("seguimiento_pedido", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("seguimiento_pedido", Seguimiento);
        editor.apply();
    }
}

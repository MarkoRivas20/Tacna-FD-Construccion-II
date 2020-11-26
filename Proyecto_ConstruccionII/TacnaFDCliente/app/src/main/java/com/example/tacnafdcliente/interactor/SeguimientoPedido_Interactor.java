package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.SeguimientoPedido;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.SeguimientoPedido_Modelo;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SeguimientoPedido_Interactor implements SeguimientoPedido.Interactor {

    private SeguimientoPedido.onOperationListener mListener;

    private ValueEventListener valueEventListener_Get_Order_Info;
    private ValueEventListener valueEventListener_Get_Establishment_Info;
    private ValueEventListener valueEventListener_Get_Tracking_Order;

    public SeguimientoPedido_Interactor(SeguimientoPedido.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetTrackingOrder(DatabaseReference Database_Reference, String ID_Pedido) {

        Query query = Database_Reference.orderByChild("id_Pedido").equalTo(ID_Pedido);

        valueEventListener_Get_Tracking_Order = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    SeguimientoPedido_Modelo Seguimiento_Pedido = postSnapshot.getValue(SeguimientoPedido_Modelo.class);
                    mListener.onSuccessGetTrackingOrder(Seguimiento_Pedido);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetTrackingOrder();
            }
        };

        query.addValueEventListener(valueEventListener_Get_Tracking_Order);
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
    public void performDrawRoute(JSONObject JSON_Object) {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        try{
            jRoutes=JSON_Object.getJSONArray("routes");
            for(int i=0;i<jRoutes.length();i++){
                jLegs=((JSONObject)(jRoutes.get(i))).getJSONArray("legs");
                for(int j=0;j<jLegs.length();j++){
                    jSteps=((JSONObject)(jLegs.get(j))).getJSONArray("steps");
                    for(int k=0;k<jSteps.length();k++){
                        String polyline=""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list= PolyUtil.decode(polyline);
                        mListener.onSuccessDrawRoute(list);
                    }
                }
            }
        }catch (JSONException e){
            mListener.onFailureDrawRoute();
        }
    }

    @Override
    public void performGetIDEstablishmentAndIDOrder(Context Contexto) {

        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_seguimiento_pedido", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");
        String ID_Pedido = sharedPref.getString("id_pedido","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetIDEstablishmentAndIDOrder(ID_Establecimiento, ID_Pedido);
        }
    }
}

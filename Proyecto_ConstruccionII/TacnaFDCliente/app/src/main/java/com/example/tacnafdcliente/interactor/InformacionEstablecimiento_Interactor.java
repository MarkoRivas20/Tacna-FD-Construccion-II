package com.example.tacnafdcliente.interactor;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


import com.example.tacnafdcliente.interfaces.InformacionEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ImagenEstablecimiento_Modelo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class InformacionEstablecimiento_Interactor implements InformacionEstablecimiento.Interactor {

    private InformacionEstablecimiento.onOperationListener mListener;

    int Contador_Imagenes = 0;

    String[] Imagenes_Urls;

    public InformacionEstablecimiento_Interactor(InformacionEstablecimiento.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performGetEstablishmentData(DatabaseReference Database_Reference, String Id_Establecimiento) {

        Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(Id_Establecimiento);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postsnapshot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postsnapshot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetEstablishmentData(Establecimiento);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentData();
            }
        });
    }

    @Override
    public void performGetLatitudeLongitude(Context Contexto, FusedLocationProviderClient Fused_Location_Provider_Client) {

        if (ActivityCompat.checkSelfPermission(Contexto, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        else
        {


            Fused_Location_Provider_Client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null)
                {
                    mListener.onSuccessGetLatitudeLongitude(location.getLatitude(), location.getLongitude());
                }
            }
        });
    }
}

    @Override
    public void performGetEstablishmentInfo(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");

        if(ID_Establecimiento.length() != 0)
        {
            mListener.onSuccessGetEstablishmentInfo(ID_Establecimiento);
        }
        else
        {
            mListener.onFailureGetEstablishmentInfo();
        }
    }
}

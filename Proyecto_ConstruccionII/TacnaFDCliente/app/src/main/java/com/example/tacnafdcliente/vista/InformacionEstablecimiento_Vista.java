package com.example.tacnafdcliente.vista;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.CustomMapView;
import com.example.tacnafdcliente.DibujarRuta;
import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.InformacionEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.presentador.InformacionEstablecimiento_Presentador;
import com.example.tacnafdcliente.presentador.OpcionesEstablecimiento_Presentador;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class InformacionEstablecimiento_Vista extends Fragment implements InformacionEstablecimiento.View, OnMapReadyCallback {

    public InformacionEstablecimiento_Vista() {
        // Required empty public constructor
    }

    public InformacionEstablecimiento_Presentador mPresenter;
    public DatabaseReference mReference;

    private GoogleMap Mapa;
    private CustomMapView Map_View;

    String ID_Establecimiento = "";

    TextView LblDireccion_Establecimiento;
    TextView LblDistrito_Establecimiento;
    TextView LblTelefono_Establecimiento;
    TextView LblCategoria_Establecimiento;
    TextView LblDescripcion_Establecimiento;
    TextView LblNombre_Establecimiento;

    String Punto_Geografico = "";
    String Latitud = "";
    String Longitud = "";

    FusedLocationProviderClient Fused_Location_Provider_Client;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informacion_establecimiento__vista, container, false);

        mPresenter = new InformacionEstablecimiento_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        Fused_Location_Provider_Client = LocationServices.getFusedLocationProviderClient(getActivity());

        LblDireccion_Establecimiento = (TextView) view.findViewById(R.id.LblDireccion_Establecimiento);
        LblDistrito_Establecimiento = (TextView) view.findViewById(R.id.LblDistrito_Establecimiento);
        ;
        LblTelefono_Establecimiento = (TextView) view.findViewById(R.id.LblTelefono_Establecimiento);
        ;
        LblCategoria_Establecimiento = (TextView) view.findViewById(R.id.LblCategoria_Establecimiento);
        ;
        LblDescripcion_Establecimiento = (TextView) view.findViewById(R.id.LblDescripcion_Establecimiento);
        ;
        LblNombre_Establecimiento = (TextView) view.findViewById(R.id.LblNombre_Establecimiento);
        ;

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetEstablishmentData(mReference, ID_Establecimiento);
        mPresenter.GetLatitudeLongitude(getActivity(), Fused_Location_Provider_Client);


        Map_View = (CustomMapView) view.findViewById(R.id.customMapView);
        Map_View.onCreate(savedInstanceState);
        Map_View.onResume();
        Map_View.getMapAsync(this);


        return view;
    }

    @Override
    public void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento) {

        LblDireccion_Establecimiento.setText(Establecimiento.getDireccion());
        LblDistrito_Establecimiento.setText(Establecimiento.getDistrito());
        LblTelefono_Establecimiento.setText(Establecimiento.getTelefono());
        LblCategoria_Establecimiento.setText(Establecimiento.getCategoria());
        LblDescripcion_Establecimiento.setText(Establecimiento.getDescripcion());
        LblNombre_Establecimiento.setText(Establecimiento.getNombre());
        Punto_Geografico = Establecimiento.getPuntoGeografico();
    }

    @Override
    public void onGetEstablishmentDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetLatitudeLongitudeSuccessful(Double Latitud, Double Longitud) {
        this.Latitud = String.valueOf(Latitud);
        this.Longitud = String.valueOf(Longitud);
    }

    @Override
    public void onGetLatitudeLongitudeFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String Id_Establecimiento) {
        ID_Establecimiento = Id_Establecimiento;
    }

    @Override
    public void onGetEstablishmentInfoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mapa = googleMap;
        Mapa.clear();

        LatLng lugar = new LatLng(-18.0038755, -70.225904);

        Mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 13));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(),"No se ha definido los permisos necesarios", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Mapa.setMyLocationEnabled(true);

            String desde = Latitud + "," + Longitud;
            String[] hastapuntos = Punto_Geografico.split("/");
            String hasta = hastapuntos[0] + "," + hastapuntos[1];

            LatLng Latitude_Longitud_Establecimiento = new LatLng(Double.parseDouble(hastapuntos[0]), Double.parseDouble(hastapuntos[1]));

            Mapa.addMarker(new MarkerOptions().position(Latitude_Longitud_Establecimiento).title("Puntos").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            new DibujarRuta(getContext(), Mapa, desde, hasta).execute();
        }
    }
}
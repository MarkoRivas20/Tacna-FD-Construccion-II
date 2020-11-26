package com.example.tacnafddelivery.vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafddelivery.CustomMapView;
import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.interfaces.DetallePedido;
import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.modelo.SeguimientoPedido_Modelo;
import com.example.tacnafddelivery.presentador.DetallePedido_Presentador;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class DetallePedido_Vista extends Fragment implements DetallePedido.View, OnMapReadyCallback {


    public DetallePedido_Vista() {
        // Required empty public constructor
    }

    public DetallePedido_Presentador mPresenter;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Pedido;
    public DatabaseReference mReference_Cliente;
    public DatabaseReference mReference_Seguimiento_Pedido;

    private GoogleMap Mapa;
    private CustomMapView Map_View;

    TextView TxtNombre_Establecimiento;
    TextView TxtNombre_Cliente;
    TextView TxtDescripcion;
    TextView TxtFecha;
    TextView TxtDireccion;

    Button BtnRealizar_Seguimiento;

    ImageView ImgLogo_Establecimiento;

    SeguimientoPedido_Vista seguimientoPedido_vista;

    String ID_Establecimiento = "";
    String ID_Pedido = "";
    String Punto_Geografico_Destino = "";
    String Punto_Geografico_Establecimiento = "";

    double Latitude = 0.0;
    double Longitud = 0.0;

    LocationManager Location_Manager;

    Location Location_A = new Location("Punto A");
    Location Location_B = new Location("Punto B");

    float Distancia = (float) 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_pedido__vista, container, false);

        TxtNombre_Establecimiento = (TextView) view.findViewById(R.id.TxtNombre_Establecimiento);
        TxtNombre_Cliente = (TextView) view.findViewById(R.id.TxtNombre_Cliente);
        TxtDescripcion = (TextView) view.findViewById(R.id.TxtDescripcion);
        TxtFecha = (TextView) view.findViewById(R.id.TxtFecha);
        TxtDireccion = (TextView) view.findViewById(R.id.TxtDireccion);
        BtnRealizar_Seguimiento = (Button) view.findViewById(R.id.BtnRealizar_Seguimiento);
        ImgLogo_Establecimiento = (ImageView) view.findViewById(R.id.ImgLogo_Establecimiento);
        Location_Manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        seguimientoPedido_vista = new SeguimientoPedido_Vista();

        mPresenter = new DetallePedido_Presentador(this);
        mReference_Pedido = FirebaseDatabase.getInstance().getReference().child("Pedido");
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Cliente = FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");
        mReference_Seguimiento_Pedido = FirebaseDatabase.getInstance().getReference().child("Seguimiento_Pedido");

        mPresenter.SetBackPressed(view);
        mPresenter.GetIDEstablishment(getActivity());
        mPresenter.GetIDOrder(getActivity());
        mPresenter.GetOrderInfo(mReference_Pedido, ID_Pedido);
        mPresenter.GetEstablishmentInfo(mReference_Establecimiento, ID_Establecimiento);

        Map_View = (CustomMapView) view.findViewById(R.id.customMapView);
        Map_View.onCreate(savedInstanceState);

        //BtnRealizar_Seguimiento.setVisibility(View.VISIBLE);

        BtnRealizar_Seguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SeguimientoPedido_Modelo Seguimiento_Pedido = new SeguimientoPedido_Modelo(ID_Pedido, Punto_Geografico_Establecimiento);
                mPresenter.SaveTrackingOrder(mReference_Seguimiento_Pedido, Seguimiento_Pedido);
            }
        });

        return view;
    }

    @Override
    public void onGetOrderInfoSuccessful(Pedido_Modelo Pedido) {
        TxtDescripcion.setText(Pedido.getDescripcion());
        TxtFecha.setText(Pedido.getFecha());
        TxtDireccion.setText(Pedido.getDireccion_Destino());
        Punto_Geografico_Destino = Pedido.getPunto_Geografico_Destino();

        mPresenter.GetClientName(mReference_Cliente, Pedido.getID_Usuario_Cliente());
    }

    @Override
    public void onGetOrderInfoFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento) {
        TxtNombre_Establecimiento.setText(Establecimiento.getNombre());
        Picasso.with(getActivity()).load(Establecimiento.getUrl_Imagen_Logo()).into(ImgLogo_Establecimiento);

        Punto_Geografico_Establecimiento = Establecimiento.getPuntoGeografico();
        String [] Punto_Geografico = Punto_Geografico_Establecimiento.split("/");
        Location_B.setLatitude(Double.parseDouble(Punto_Geografico[0]));
        Location_B.setLongitude(Double.parseDouble(Punto_Geografico[1]));

        if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Location_Manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, locationListenerNetwork);
        }
        else
        {
            getActivity().getSupportFragmentManager().popBackStack();
        }

        Map_View.onResume();
        Map_View.getMapAsync(this);

    }

    @Override
    public void onGetEstablishmentInfoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetClientNameSuccessful(Cliente_Modelo Cliente) {
        TxtNombre_Cliente.setText(Cliente.getNombre() + " " + Cliente.getApellido());
    }

    @Override
    public void onGetClientNameFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateOrderStatusSuccess() {
        Location_Manager.removeUpdates(locationListenerNetwork);
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, seguimientoPedido_vista).addToBackStack(null).commit();
    }

    @Override
    public void onUpdateOrderStatusFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveTrackingOrderSuccessful() {
        mPresenter.UpdateOrderStatus(mReference_Pedido, ID_Pedido);
    }

    @Override
    public void onSaveTrackingOrderFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetIDEstablishmentSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

    @Override
    public void onGetIDOrderSuccessful(String ID_Pedido) {
        this.ID_Pedido = ID_Pedido;
    }

    @Override
    public void onSetBackPressedSuccessful(Boolean aBoolean) {
        if(aBoolean)
        {
            Location_Manager.removeUpdates(locationListenerNetwork);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mapa = googleMap;

        String[] ltdlng = Punto_Geografico_Destino.split("/");
        LatLng Lugar = new LatLng(Double.parseDouble(ltdlng[0]), Double.parseDouble(ltdlng[1]));

        Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Lugar, 17));

        MarkerOptions Marker_Options = new MarkerOptions();
        Marker_Options.position(Lugar);
        Mapa.addMarker(Marker_Options);

    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            Longitud = location.getLongitude();
            Latitude = location.getLatitude();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Location_A.setLatitude(Latitude);
                    Location_A.setLongitude(Longitud);

                    Distancia = Location_A.distanceTo(Location_B);

                    if(Distancia <= 50)
                    {
                        BtnRealizar_Seguimiento.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        BtnRealizar_Seguimiento.setVisibility(View.GONE);
                    }

                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
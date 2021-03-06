package com.example.tacnafddelivery.vista;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tacnafddelivery.CustomMapView;
import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.interfaces.SeguimientoPedido;
import com.example.tacnafddelivery.modelo.Cliente_Modelo;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.modelo.SeguimientoPedido_Modelo;
import com.example.tacnafddelivery.presentador.SeguimientoPedido_Presentador;
import com.example.tacnafddelivery.servicios.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;


public class SeguimientoPedido_Vista extends Fragment implements SeguimientoPedido.View, OnMapReadyCallback {


    public SeguimientoPedido_Vista() {
        // Required empty public constructor
    }

    public SeguimientoPedido_Presentador mPresenter;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Pedido;
    public DatabaseReference mReference_Cliente;
    public DatabaseReference mReference_Seguimiento_Pedido;

    private GoogleMap Mapa;
    private CustomMapView Map_View;

    TextView TxtDescripcion_Pedido;
    TextView TxtDireccion_Destino;
    TextView TxtNombre_Cliente;
    TextView TxtTiempo;
    TextView TxtDistancia;
    TextView TxtPrecio;

    ImageView ImgLogo_Establecimiento;
    ImageView ImgQR;

    Button BtnMostrar_QR;
    Button BtnTerminar_Seguimiento;

    String ID_Establecimiento = "";
    String ID_Pedido = "";
    String Punto_Geografico_Establecimiento = "";
    String Punto_Geografico_Pedido = "";
    
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    static final String ACTION_START_LOCATION_SERVICE = "startLocationService";
    static final String ACTION_STOP_LOCATION_SERVICE = "stopLocationService";

    JSONObject JSON_Object;

    AlertDialog.Builder mBuilder;
    View Qr_View;
    AlertDialog Alert_Dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seguimiento_pedido__vista, container, false);

        mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setCancelable(true);
        Qr_View = getLayoutInflater().inflate(R.layout.dialog_qr,null);
        mBuilder.setView(Qr_View);
        Alert_Dialog = mBuilder.create();

        TxtDescripcion_Pedido = (TextView) view.findViewById(R.id.TxtDescripcion_Pedido);
        TxtDireccion_Destino = (TextView) view.findViewById(R.id.TxtDireccion_Destino);
        TxtNombre_Cliente = (TextView) view.findViewById(R.id.TxtNombre_Cliente);
        TxtTiempo = (TextView) view.findViewById(R.id.TxtTiempo);
        TxtPrecio = (TextView) view.findViewById(R.id.TxtPrecio);
        TxtDistancia = (TextView) view.findViewById(R.id.TxtDistancia);
        ImgLogo_Establecimiento = (ImageView) view.findViewById(R.id.ImgLogo_Establecimiento);
        BtnMostrar_QR = (Button) view.findViewById(R.id.BtnMostrar_QR);
        BtnTerminar_Seguimiento = (Button) view.findViewById(R.id.BtnTerminar_Seguimiento);
        ImgQR = (ImageView) Qr_View.findViewById(R.id.ImgQR);

        mPresenter = new SeguimientoPedido_Presentador(this);
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

        BtnTerminar_Seguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.UpdateOrderStatus(mReference_Pedido, ID_Pedido);
            }
        });

        BtnMostrar_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alert_Dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onSaveTrackingOrderSuccessful() {


    }

    @Override
    public void onSaveTrackingOrderFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetOrderInfoSuccessful(Pedido_Modelo Pedido) {

        TxtDescripcion_Pedido.setText(Pedido.getDescripcion());
        TxtDireccion_Destino.setText(Pedido.getDireccion_Destino());
        Punto_Geografico_Pedido = Pedido.getPunto_Geografico_Destino();

        if (Pedido.getMetodo_Pago().equals("Contraentrega")) {
            BtnMostrar_QR.setVisibility(View.VISIBLE);
            TxtPrecio.setText("S/. " + Pedido.getPrecio_Total() + " - Pendiente");
        }
        else
        {
            BtnMostrar_QR.setVisibility(View.GONE);
            TxtPrecio.setText("S/. " + Pedido.getPrecio_Total() + " - Cancelado");
        }

        mPresenter.GetClientName(mReference_Cliente, Pedido.getID_Usuario_Cliente());


    }

    @Override
    public void onGetOrderInfoFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento) {

        Picasso.with(getActivity()).load(Establecimiento.getUrl_Imagen_Logo()).into(ImgLogo_Establecimiento);
        Punto_Geografico_Establecimiento = Establecimiento.getPuntoGeografico();

        if(Establecimiento.getUrl_Qr() != null)
        {
            Picasso.with(getActivity()).load(Establecimiento.getUrl_Qr()).into(ImgQR);
        }

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);

        }
        else {
            Map_View.onResume();
            Map_View.getMapAsync(this);
            startLocationService();
        }
    }

    @Override
    public void onGetEstablishmentInfoFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetClientNameSuccessful(Cliente_Modelo Cliente) {
        TxtNombre_Cliente.setText(Cliente.getNombre() + " " + Cliente.getApellido());
    }

    @Override
    public void onGetClientNameFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDrawRouteSuccessful(List<LatLng> list) {
        Mapa.addPolyline(new PolylineOptions().addAll(list).color(Color.BLUE).width(5));
    }

    @Override
    public void onDrawRouteFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateOrderStatusSuccess() {
        stopLocationService();

        mPresenter.UpdateTrackingOrderSharedPreference(getActivity(),"Inactivo");
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onUpdateOrderStatusFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
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
    public void onMapReady(GoogleMap googleMap) {
        Mapa = googleMap;
        Mapa.clear();

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            String[] PuntoGeografico = Punto_Geografico_Establecimiento.split("/");
            String Ltd_Origen = PuntoGeografico[0];
            String Lng_Origen = PuntoGeografico[1];
            LatLng lugar = new LatLng(Double.parseDouble(Ltd_Origen), Double.parseDouble(Lng_Origen));
            Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 15));

            try {

                String desde = Ltd_Origen + "," + Lng_Origen;
                String[] hastapuntos = Punto_Geografico_Pedido.split("/");
                String hasta = hastapuntos[0] + "," + hastapuntos[1];

                final LatLng Punto_Destino = new LatLng(Double.parseDouble(hastapuntos[0]), Double.parseDouble(hastapuntos[1]));
                Mapa.addMarker(new MarkerOptions().position(Punto_Destino).title("Destino").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + desde + "&destination=" + hasta + "&key=YOUR API KEY";

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSON_Object = new JSONObject(response);
                            mPresenter.DrawRoute(JSON_Object);

                        } catch (JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(stringRequest);

                Location locationA = new Location("punto A");
                locationA.setLatitude(Double.parseDouble(Ltd_Origen));
                locationA.setLongitude(Double.parseDouble(Lng_Origen));

                Location locationB = new Location("punto B");
                locationB.setLatitude(Double.parseDouble(hastapuntos[0]));
                locationB.setLongitude(Double.parseDouble(hastapuntos[1]));

                float distancia = locationA.distanceTo(locationB);
                float tiempo = Math.round(distancia / 500);
                distancia = distancia / 1000;
                DecimalFormat df = new DecimalFormat("#.0");
                TxtDistancia.setText(df.format(distancia) + " km");

                String str = String.valueOf(tiempo);
                int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
                TxtTiempo.setText(intNumber + " minutos");

                Mapa.setMyLocationEnabled(true);
                Mapa.getUiSettings().setMyLocationButtonEnabled(true);

            }catch (Exception ex){

            }

        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startLocationService();
            }else
            {
                Toast.makeText(getActivity(),"Permiso denegado",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);

        if(activityManager != null){
            for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent intent = new Intent(getActivity(),LocationService.class);
            intent.setAction(ACTION_START_LOCATION_SERVICE);
            intent.putExtra("ID_Pedido", ID_Pedido);
            getActivity().startService(intent);
            Toast.makeText(getActivity(),"Location service started",Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationService(){
        if(isLocationServiceRunning()){
            Intent intent = new Intent(getActivity(),LocationService.class);
            intent.setAction(ACTION_STOP_LOCATION_SERVICE);
            intent.putExtra("ID_Pedido", ID_Pedido);
            getActivity().startService(intent);
            Toast.makeText(getActivity(),"Location service stopped",Toast.LENGTH_SHORT).show();
        }
    }
}
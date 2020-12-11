package com.example.tacnafdcliente.vista;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tacnafdcliente.CustomMapView;
import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.SeguimientoPedido;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.SeguimientoPedido_Modelo;
import com.example.tacnafdcliente.presentador.RegistrarResena_Presentador;
import com.example.tacnafdcliente.presentador.SeguimientoPedido_Presentador;
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
    public DatabaseReference mReference_Seguimiento_Pedido;

    private GoogleMap Mapa;
    private CustomMapView Map_View;

    TextView TxtDescripcion_Pedido;
    TextView TxtPrecio;
    TextView TxtNombre_Establecimiento;
    TextView TxtTiempo;
    TextView TxtDistancia;

    ImageView ImgLogo_Establecimiento;

    String ID_Establecimiento = "";
    String ID_Pedido = "";
    String Punto_Geografico_Pedido = "";

    JSONObject JSON_Object;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seguimiento_pedido__vista, container, false);

        TxtDescripcion_Pedido = (TextView) view.findViewById(R.id.TxtDescripcion_Pedido);
        TxtPrecio = (TextView) view.findViewById(R.id.TxtPrecio);
        TxtNombre_Establecimiento = (TextView) view.findViewById(R.id.TxtNombre_Establecimiento);
        TxtTiempo = (TextView) view.findViewById(R.id.TxtTiempo);
        TxtDistancia = (TextView) view.findViewById(R.id.TxtDistancia);
        ImgLogo_Establecimiento = (ImageView) view.findViewById(R.id.ImgLogo_Establecimiento);

        Map_View = (CustomMapView) view.findViewById(R.id.customMapView);
        Map_View.onCreate(savedInstanceState);

        mPresenter=new SeguimientoPedido_Presentador(this);
        mReference_Seguimiento_Pedido = FirebaseDatabase.getInstance().getReference().child("Seguimiento_Pedido");
        mReference_Pedido = FirebaseDatabase.getInstance().getReference().child("Pedido");
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        mPresenter.GetIDEstablishmentAndIDOrder(getActivity());
        mPresenter.GetOrderInfo(mReference_Pedido, ID_Pedido);
        mPresenter.GetEstablishmentInfo(mReference_Establecimiento, ID_Establecimiento);

        return view;
    }

    @Override
    public void onGetTrackingOrderSuccessful(SeguimientoPedido_Modelo Seguimiento_Pedido) {
        Mapa.clear();

        String []Latlng_Repartidor = Seguimiento_Pedido.getPuntoGeografico().split("/");
        LatLng Ubicacion_Repartidor = new LatLng(Double.parseDouble(Latlng_Repartidor[0]),Double.parseDouble(Latlng_Repartidor[1]));

        Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Ubicacion_Repartidor, 17));

        Mapa.addMarker(new MarkerOptions().position(Ubicacion_Repartidor).title("Repartidor").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        String desde = Latlng_Repartidor[0] + "," + Latlng_Repartidor[1];
        String[] hastapuntos = Punto_Geografico_Pedido.split("/");
        String hasta = hastapuntos[0] + "," + hastapuntos[1];

        LatLng Ubicacion_Pedido = new LatLng(Double.parseDouble(hastapuntos[0]), Double.parseDouble(hastapuntos[1]));

        Mapa.addMarker(new MarkerOptions().position(Ubicacion_Pedido).title("Destino").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        String url="https://maps.googleapis.com/maps/api/directions/json?origin="+desde+"&destination="+hasta+"&key=YOUR API KEY";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSON_Object = new JSONObject(response);
                    mPresenter.DrawRoute(JSON_Object);

                }catch (JSONException e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

        Location locationA = new Location("punto A");
        locationA.setLatitude(Double.parseDouble(Latlng_Repartidor[0]));
        locationA.setLongitude(Double.parseDouble(Latlng_Repartidor[1]));

        Location locationB = new Location("punto B");
        locationB.setLatitude(Double.parseDouble(hastapuntos[0]));
        locationB.setLongitude(Double.parseDouble(hastapuntos[1]));

        float distancia = locationA.distanceTo(locationB);
        float tiempo = Math.round(distancia / 500);
        distancia = distancia / 1000;
        DecimalFormat df = new DecimalFormat("#.0");
        TxtDistancia.setText(df.format(distancia)+" km");

        String str = String.valueOf(tiempo);
        int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
        TxtTiempo.setText(intNumber+" minutos");
    }

    @Override
    public void onGetTrackingOrderFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetOrderInfoSuccessful(Pedido_Modelo Pedido) {
        TxtDescripcion_Pedido.setText(Pedido.getDescripcion());
        Punto_Geografico_Pedido = Pedido.getPunto_Geografico_Destino();

        if(Pedido.getMetodo_Pago().equals("Contraentrega"))
        {
            TxtPrecio.setText("S/. " + Pedido.getPrecio_Total() + " - Pendiente");
        }
        else
        {
            TxtPrecio.setText("S/. " + Pedido.getPrecio_Total() + " - Cancelado");
        }



        Map_View.onResume();
        Map_View.getMapAsync(this);
    }

    @Override
    public void onGetOrderInfoFailure() {
        Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento) {
        TxtNombre_Establecimiento.setText(Establecimiento.getNombre());
        Picasso.get().load(Establecimiento.getUrl_Imagen_Logo()).into(ImgLogo_Establecimiento);

    }

    @Override
    public void onGetEstablishmentInfoFailure() {
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
    public void onGetIDEstablishmentAndIDOrderSuccessful(String ID_Establecimiento, String ID_Pedido) {
        this.ID_Establecimiento = ID_Establecimiento;
        this.ID_Pedido = ID_Pedido;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mapa = googleMap;

        mPresenter.GetTrackingOrder(mReference_Seguimiento_Pedido, ID_Pedido);
    }
}
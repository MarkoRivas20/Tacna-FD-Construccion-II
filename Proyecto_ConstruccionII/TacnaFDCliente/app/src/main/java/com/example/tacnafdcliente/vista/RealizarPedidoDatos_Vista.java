package com.example.tacnafdcliente.vista;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacnafdcliente.CustomMapView;
import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.RealizarPedidoDatos;
import com.example.tacnafdcliente.presentador.RealizarPedidoDatos_Presentador;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class RealizarPedidoDatos_Vista extends Fragment implements OnMapReadyCallback, RealizarPedidoDatos.View {

    public RealizarPedidoDatos_Vista() {
        // Required empty public constructor
    }

    public RealizarPedidoDatos_Presentador mPresenter;

    private GoogleMap Mapa;
    private CustomMapView Map_View;

    StateProgressBar State_ProgressBar;

    EditText TxtNombre_Establecimiento;
    EditText TxtNombre_Cliente;
    EditText TxtDireccion_Destino;

    String[] descriptionData = {"Datos", "Pedido", "Pago"};
    String Punto_Geografico = "";
    String ID_Establecimiento = "";
    String ID_Usuario = "";

    List<Address> Direcciones;

    Button BtnSiguiente;

    RealizarPedidoDetalle_Vista realizarPedidoDetalle_vista;

    InputMethodManager Input_Method_Manager;

    Geocoder geocoder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_realizar_pedido_datos__vista, container, false);

        mPresenter = new RealizarPedidoDatos_Presentador(this);

        State_ProgressBar = (StateProgressBar) view.findViewById(R.id.State_ProgressBar);
        TxtNombre_Establecimiento = (EditText) view.findViewById(R.id.TxtNombre_Establecimiento);
        TxtNombre_Cliente = (EditText) view.findViewById(R.id.TxtNombre_Cliente);
        TxtDireccion_Destino = (EditText) view.findViewById(R.id.TxtDireccion_Destino);
        BtnSiguiente = (Button) view.findViewById(R.id.BtnSiguiente);
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        realizarPedidoDetalle_vista = new RealizarPedidoDetalle_Vista();

        TxtNombre_Cliente.setFocusable(false);
        TxtNombre_Establecimiento.setFocusable(false);

        State_ProgressBar.setStateDescriptionData(descriptionData);
        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetSessionData(getActivity());

        Map_View = (CustomMapView) view.findViewById(R.id.map);
        Map_View.onCreate(savedInstanceState);
        Map_View.onResume();
        Map_View.getMapAsync(this);

        BtnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TxtDireccion_Destino.getText().toString().equals("") && !Punto_Geografico.equals(""))
                {
                    mPresenter.SaveOrderDataSharedPreference(getActivity(),ID_Usuario, ID_Establecimiento, TxtDireccion_Destino.getText().toString(), Punto_Geografico);
                }
                else
                {
                    Toast.makeText(getActivity(),"Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Mapa = googleMap;

        LatLng Tacna = new LatLng(-18.012580, -70.246520);
        Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Tacna,14));

        Mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Mapa.clear();
                String latitud = String.valueOf(latLng.latitude);
                String longitud = String.valueOf(latLng.longitude);

                MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.valueOf(latitud), Double.valueOf(longitud))).title("Aqui");
                Mapa.addMarker(marker);

                Punto_Geografico = latitud + "/" + longitud;

                try {

                    Direcciones = geocoder.getFromLocation(Double.parseDouble(latitud), Double.parseDouble(longitud), 1);

                    String [] Direccion = Direcciones.get(0).getAddressLine(0).split(",");

                    TxtDireccion_Destino.setText(Direccion[0]);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onSaveOrderDataSharedPreferenceSuccessful() {
        Input_Method_Manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Input_Method_Manager.hideSoftInputFromWindow(BtnSiguiente.getWindowToken(), 0);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, realizarPedidoDetalle_vista).addToBackStack(null).commit();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento, String Nombre_Establecimiento) {

        this.ID_Establecimiento = ID_Establecimiento;
        TxtNombre_Establecimiento.setText(Nombre_Establecimiento);
    }

    @Override
    public void onGetSessionDataSuccessful(String ID_Usuario, String Nombre_Usuario) {

        this.ID_Usuario = ID_Usuario;
        TxtNombre_Cliente.setText(Nombre_Usuario);
    }
}
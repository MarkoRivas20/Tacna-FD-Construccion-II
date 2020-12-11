package com.example.tacnafdbusiness.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.CustomMapView;
import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.ModificarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.presentador.ModificarEstablecimiento_Presentador;
import com.example.tacnafdbusiness.presentador.RegistrarEstablecimiento_Presentador;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class ModificarEstablecimiento_Vista extends Fragment implements ModificarEstablecimiento.View, OnMapReadyCallback {


    public ModificarEstablecimiento_Vista() {
        // Required empty public constructor
    }

    private GoogleMap Mapa;
    private CustomMapView Map_View;

    public ModificarEstablecimiento_Presentador mPresenter;
    public DatabaseReference mReference;
    public StorageReference mStorageReference;

    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE_DOCUMENT = 200;

    Uri Image_Uri;
    Uri Documento_Uri;

    FloatingActionButton Fab;
    Button BtnModificar_Establecimiento;
    Button BtnModificar_Documento;

    String ID_Establecimiento = "";
    String Url_Logo_Actual = "";
    String Url_Documento_Actual = "";
    String Punto_Geografico = "";
    String Nombre_Establecimiento = "";
    String ID_Usuario = "";

    int Total_Resenas = 0;

    Double Puntuacion = 0.0;

    ImageView Logo_Establecimiento;

    EditText Txtnombre;
    EditText Txtdireccion;
    EditText Txttelefono;
    EditText Txtdescripcion;

    Spinner Spinner_Categoria;
    Spinner Spinner_Distrito;
    Spinner Spinner_Estado;

    TextView LblDocumento;

    String[] Categorias = {"Seleccione una Categoria", "Restaurante", "Cafeteria", "Panaderia", "Food Truck"};
    String[] Distritos = {"Seleccione un Distrito", "Tacna", "Alto del Alianza", "Calana", "Pachia", "Palca", "Pocollay", "Ciudad Nueva"};
    String[] Estados = {"Seleccione un estado", "Activo", "Inactivo"};

    OpcionesEstablecimiento_Vista opcionesestablecimiento_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_establecimiento__vista, container, false);

        opcionesestablecimiento_vista = new OpcionesEstablecimiento_Vista();

        mPresenter =new ModificarEstablecimiento_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        Logo_Establecimiento = (ImageView) view.findViewById(R.id.logo_establecimiento);
        Txtnombre = (EditText) view.findViewById(R.id.txtnombre);
        Txtdireccion = (EditText) view.findViewById(R.id.txtdireccion);
        Txttelefono = (EditText) view.findViewById(R.id.txttelefono);
        Txtdescripcion = (EditText) view.findViewById(R.id.txtdescripcion);
        Spinner_Categoria = (Spinner) view.findViewById(R.id.spinnercategoria);
        Spinner_Distrito = (Spinner) view.findViewById(R.id.spinnerdistrito);
        Spinner_Estado = (Spinner) view.findViewById(R.id.spinnerestado);
        LblDocumento = (TextView) view.findViewById(R.id.LblDocumento);
        Fab = (FloatingActionButton) view.findViewById(R.id.fab_establecimiento);
        BtnModificar_Establecimiento = (Button) view.findViewById(R.id.BtnModificar_Establecimiento);
        BtnModificar_Documento = (Button) view.findViewById(R.id.BtnModificar_Documento);


        Spinner_Categoria.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categorias));
        Spinner_Distrito.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Distritos));
        Spinner_Estado.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Estados));

        mPresenter.GetEstablishmentInfo(getActivity().getApplicationContext());
        mPresenter.GetEstablishmentData(mReference,ID_Establecimiento);
        mPresenter.GetSessionData(getActivity().getApplicationContext());

        Map_View = (CustomMapView) view.findViewById(R.id.customMapView);
        Map_View.onCreate(savedInstanceState);
        Map_View.onResume();
        Map_View.getMapAsync(this);

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }

            }
        });

        BtnModificar_Establecimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Txtnombre.getText().toString().equals("") && !Txtdireccion.getText().toString().equals("") && !Txttelefono.getText().toString().equals("") &&
                        !Txtdescripcion.getText().toString().equals("") && !Punto_Geografico.equals("") && Spinner_Categoria.getSelectedItemPosition() != 0
                        && Spinner_Distrito.getSelectedItemPosition() != 0)
                {
                    Establecimiento_Modelo Establecimiento = new Establecimiento_Modelo(ID_Establecimiento, ID_Usuario,Txtnombre.getText().toString(),
                            Spinner_Distrito.getSelectedItem().toString(),Spinner_Categoria.getSelectedItem().toString(),Txtdireccion.getText().toString(),Txttelefono.getText().toString(),
                            Txtdescripcion.getText().toString(),Total_Resenas,Puntuacion,Url_Logo_Actual, Url_Documento_Actual, Punto_Geografico, Spinner_Estado.getSelectedItem().toString());

                    mPresenter.UpdateEstablismentData(mReference, Establecimiento);
                }
                else
                {
                    Toast.makeText(getActivity(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                }


            }
        });

        BtnModificar_Documento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE_DOCUMENT);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            Image_Uri = data.getData();
            Logo_Establecimiento.setImageURI(Image_Uri);
            mPresenter.UpdateEstablismentLogo(mStorageReference, mReference, Url_Logo_Actual, ID_Establecimiento, Image_Uri);
        }

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_DOCUMENT)
        {
            Documento_Uri = data.getData();
            mPresenter.UpdateEstablismentDocument(mStorageReference, mReference, Url_Documento_Actual, ID_Establecimiento, Documento_Uri);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Mapa = googleMap;

        String[] ltdlng = Punto_Geografico.split("/");
        LatLng Lugar = new LatLng(Double.parseDouble(ltdlng[0]), Double.parseDouble(ltdlng[1]));

        Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Lugar, 17));

        MarkerOptions Marker_Options = new MarkerOptions();

        Marker_Options.position(Lugar);
        Mapa.clear();
        Mapa.addMarker(Marker_Options);

        Mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Mapa.clear();
                String Latitud = String.valueOf(latLng.latitude);
                String Longitud = String.valueOf(latLng.longitude);

                MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.valueOf(Latitud), Double.valueOf(Longitud))).title("Aqui");
                Mapa.addMarker(marker);

                Punto_Geografico = Latitud + "/" + Longitud;
            }
        });

    }

    @Override
    public void onUpdateEstablismentDataFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateEstablismentDataSuccessful() {
        mPresenter.UpdateEstablishmentInfo(getActivity().getApplicationContext(), Txtnombre.getText().toString(), Url_Logo_Actual, Url_Documento_Actual);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onUpdateEstablismentLogoFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateEstablismentLogoSuccessful(String Url_Logo) {

        Url_Logo_Actual = Url_Logo;
        mPresenter.UpdateEstablishmentInfo(getActivity().getApplicationContext(), Nombre_Establecimiento, Url_Logo_Actual, Url_Documento_Actual);

    }

    @Override
    public void onUpdateEstablismentDocumentFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateEstablismentDocumentSuccessful(String Url_Documento) {

        Url_Documento_Actual = Url_Documento;
        mPresenter.UpdateEstablishmentInfo(getActivity().getApplicationContext(), Nombre_Establecimiento, Url_Logo_Actual, Url_Documento_Actual);

    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento, String Url_Logo, String Url_Documento) {

       this.ID_Establecimiento = ID_Establecimiento;
       Url_Logo_Actual = Url_Logo;
       Url_Documento_Actual = Url_Documento;

       Picasso.with(getActivity().getApplicationContext()).load(Url_Logo_Actual).into(Logo_Establecimiento);
    }

    @Override
    public void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento) {
        Nombre_Establecimiento = Establecimiento.getNombre();
        Txtnombre.setText(Establecimiento.getNombre());
        Txtdescripcion.setText(Establecimiento.getDescripcion());
        Txtdireccion.setText(Establecimiento.getDireccion());
        Txttelefono.setText(Establecimiento.getTelefono());
        Punto_Geografico = Establecimiento.getPuntoGeografico();
        Total_Resenas = Establecimiento.getTotalResenas();
        Puntuacion = Establecimiento.getPuntuacion();
        LblDocumento.setText("Imagen: " + Url_Documento_Actual.substring(Url_Documento_Actual.indexOf("o%2F") + 4, Url_Documento_Actual.indexOf("?alt=")));

        for(int i=0; i<Categorias.length; i++)
        {
            if(Establecimiento.getCategoria().equals(Categorias[i]))
            {
                Spinner_Categoria.setSelection(i);
                break;
            }
        }

        for(int i=0; i<Distritos.length; i++)
        {
            if(Establecimiento.getDistrito().equals(Distritos[i]))
            {
                Spinner_Distrito.setSelection(i);
                break;
            }
        }

        for (int i=0; i<Estados.length; i++)
        {
            if (Establecimiento.getEstado().equals(Estados[i]))
            {
                Spinner_Estado.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onGetEstablishmentDataFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSessionDataSuccessful(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    @Override
    public void onUpdateEstablishmentInfoSuccessful() {
        LblDocumento.setText("Imagen: " + Url_Documento_Actual.substring(Url_Documento_Actual.indexOf("o%2F") + 4,Url_Documento_Actual.indexOf("?alt=")));
    }


}
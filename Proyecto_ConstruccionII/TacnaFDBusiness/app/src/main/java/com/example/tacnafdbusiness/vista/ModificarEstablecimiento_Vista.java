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

    FloatingActionButton fab;
    Button BtnModificar_Establecimiento;
    Button BtnModificar_Documento;

    String Id_Establecimiento = "";
    String Url_Logo_Actual = "";
    String Url_Documento_Actual = "";
    String Punto_Geografico = "";
    String Nombre_Establecimiento = "";
    String Id_Usuario = "";
    int Total_Resenas = 0;
    Double Puntuacion = 0.0;

    ImageView logo_establecimiento;

    EditText Txtnombre;
    EditText Txtdireccion;
    EditText Txttelefono;
    EditText Txtdescripcion;

    Spinner Spinner_Categoria;
    Spinner Spinner_Distrito;
    Spinner Spinner_Estado;

    TextView LblDocumento;

    String[] categorias = {"Seleccione una Categoria", "Restaurante", "Cafeteria", "Panaderia", "Cafeteria"};
    String[] distritos = {"Seleccione un Distrito", "Tacna", "Alto del Alianza", "Calana", "Pachia", "Palca", "Pocollay", "Ciudad Nueva"};
    String[] estados = {"Seleccione un estado", "Activo", "Inactivo"};

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

        logo_establecimiento = (ImageView) view.findViewById(R.id.logo_establecimiento);
        Txtnombre = (EditText) view.findViewById(R.id.txtnombre);
        Txtdireccion = (EditText) view.findViewById(R.id.txtdireccion);
        Txttelefono = (EditText) view.findViewById(R.id.txttelefono);
        Txtdescripcion = (EditText) view.findViewById(R.id.txtdescripcion);
        Spinner_Categoria = (Spinner) view.findViewById(R.id.spinnercategoria);
        Spinner_Distrito = (Spinner) view.findViewById(R.id.spinnerdistrito);
        Spinner_Estado = (Spinner) view.findViewById(R.id.spinnerestado);
        LblDocumento = (TextView) view.findViewById(R.id.LblDocumento);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_establecimiento);
        BtnModificar_Establecimiento = (Button) view.findViewById(R.id.BtnModificar_Establecimiento);
        BtnModificar_Documento = (Button) view.findViewById(R.id.BtnModificar_Documento);


        Spinner_Categoria.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categorias));
        Spinner_Distrito.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, distritos));
        Spinner_Estado.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, estados));

        mPresenter.GetEstablishmentInfo(getActivity().getApplicationContext());
        mPresenter.GetEstablishmentData(mReference,Id_Establecimiento);
        mPresenter.GetSessionData(getActivity().getApplicationContext());

        Map_View = (CustomMapView) view.findViewById(R.id.customMapView);
        Map_View.onCreate(savedInstanceState);
        Map_View.onResume();
        Map_View.getMapAsync(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

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
                Establecimiento_Modelo establecimiento_modelo = new Establecimiento_Modelo(Id_Establecimiento, Id_Usuario,Txtnombre.getText().toString(),
                        Spinner_Distrito.getSelectedItem().toString(),Spinner_Categoria.getSelectedItem().toString(),Txtdireccion.getText().toString(),Txttelefono.getText().toString(),
                        Txtdescripcion.getText().toString(),Total_Resenas,Puntuacion,Url_Logo_Actual, Url_Documento_Actual, Punto_Geografico, Spinner_Estado.getSelectedItem().toString());

                mPresenter.UpdateEstablismentData(mReference, establecimiento_modelo);
            }
        });

        BtnModificar_Documento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

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

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Image_Uri = data.getData();
            logo_establecimiento.setImageURI(Image_Uri);
            mPresenter.UpdateEstablismentLogo(mStorageReference, mReference, Url_Logo_Actual, Id_Establecimiento, Image_Uri);

        }
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_DOCUMENT){
            Documento_Uri = data.getData();
            mPresenter.UpdateEstablismentDocument(mStorageReference, mReference, Url_Documento_Actual, Id_Establecimiento, Documento_Uri);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Mapa = googleMap;

        String[] ltdlng = Punto_Geografico.split("/");
        LatLng lugar = new LatLng(Double.parseDouble(ltdlng[0]), Double.parseDouble(ltdlng[1]));

        Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 17));

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(lugar);
        Mapa.clear();
        Mapa.addMarker(markerOptions);

        Mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Mapa.clear();
                String latitud = String.valueOf(latLng.latitude);
                String longitud = String.valueOf(latLng.longitude);

                MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.valueOf(latitud), Double.valueOf(longitud))).title("Aqui");
                Mapa.addMarker(marker);

                Punto_Geografico = latitud + "/" + longitud;
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
    public void onGetEstablishmentInfoSuccessful(String Id_Establecimiento, String Url_Logo, String Url_Documento) {

       this.Id_Establecimiento = Id_Establecimiento;
       Url_Logo_Actual = Url_Logo;
       Url_Documento_Actual = Url_Documento;

       Picasso.with(getActivity().getApplicationContext()).load(Url_Logo_Actual).into(logo_establecimiento);
    }

    @Override
    public void onGetEstablishmentDataSuccessful(ArrayList<Establecimiento_Modelo> establecimiento) {
        Nombre_Establecimiento = establecimiento.get(0).getNombre();
        Txtnombre.setText(establecimiento.get(0).getNombre());
        Txtdescripcion.setText(establecimiento.get(0).getDescripcion());
        Txtdireccion.setText(establecimiento.get(0).getDireccion());
        Txttelefono.setText(establecimiento.get(0).getTelefono());
        Punto_Geografico = establecimiento.get(0).getPuntoGeografico();
        Total_Resenas = establecimiento.get(0).getTotalResenas();
        Puntuacion = establecimiento.get(0).getPuntuacion();
        LblDocumento.setText("Imagen: " + Url_Documento_Actual.substring(Url_Documento_Actual.indexOf("o%2F") + 4, Url_Documento_Actual.indexOf("?alt=")));

        for(int i=0; i<categorias.length; i++){
            if(establecimiento.get(0).getCategoria().equals(categorias[i])){
                Spinner_Categoria.setSelection(i);
                break;
            }
        }

        for(int i=0; i<distritos.length; i++){
            if(establecimiento.get(0).getDistrito().equals(distritos[i])){
                Spinner_Distrito.setSelection(i);
                break;
            }
        }

        for (int i=0; i<estados.length; i++)
        {
            if (establecimiento.get(0).getEstado().equals(estados[i]))
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
    public void onGetSessionDataSuccessful(String Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    @Override
    public void onUpdateEstablishmentInfoSuccessful() {
        LblDocumento.setText("Imagen: " + Url_Documento_Actual.substring(Url_Documento_Actual.indexOf("o%2F") + 4,Url_Documento_Actual.indexOf("?alt=")));
    }


}
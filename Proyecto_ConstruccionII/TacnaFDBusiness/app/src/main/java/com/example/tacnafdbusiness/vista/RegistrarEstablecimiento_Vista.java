package com.example.tacnafdbusiness.vista;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.tacnafdbusiness.interfaces.RegistrarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.presentador.RegistrarEstablecimiento_Presentador;
import com.example.tacnafdbusiness.presentador.RegistroUsuario_Presentador;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;


public class RegistrarEstablecimiento_Vista extends Fragment implements OnMapReadyCallback, RegistrarEstablecimiento.View {


    public RegistrarEstablecimiento_Vista() {
        // Required empty public constructor
    }

    public RegistrarEstablecimiento_Presentador mPresenter;
    public DatabaseReference mReference;
    public StorageReference mStorageReference;

    FloatingActionButton Fab;

    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE_DOCUMENT = 200;

    Uri Image_Uri;
    Uri Documento_Uri;

    ImageView Logo_Establecimiento;

    Boolean Imagen_Seleccionada = false;
    Boolean Documento_Seleccionado = false;

    Spinner Spinner_Categoria;
    Spinner Spinner_Distrito;

    private GoogleMap Mapa;
    private CustomMapView Map_View;

    Button BtnCargar_Documento;

    TextView LblDocumento;

    String Punto_Geografico = "";
    String ID_Establecimiento = "";
    String Url_Logo = "";
    String Url_Documento = "";
    String ID_Usuario = "";
    String[] Categorias = {"Seleccione una Categoria", "Restaurante", "Cafeteria", "Panaderia", "Cafeteria"};
    String[] Distritos = {"Seleccione un Distrito", "Tacna", "Alto del Alianza", "Calana", "Pachia", "Palca", "Pocollay", "Ciudad Nueva"};

    Button BtnRegistrar_Establecimiento;

    EditText Txtnombre;
    EditText Txtdireccion;
    EditText Txttelefono;
    EditText Txtdescripcion;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro_establecimiento__vista, container, false);

        mPresenter =new RegistrarEstablecimiento_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        mPresenter.GetSessionData(getActivity().getApplicationContext());

        Fab = (FloatingActionButton) view.findViewById(R.id.fab_establecimiento);
        Logo_Establecimiento = (ImageView) view.findViewById(R.id.logo_establecimiento);
        Spinner_Categoria = (Spinner) view.findViewById(R.id.spinnercategoria);
        Spinner_Distrito = (Spinner) view.findViewById(R.id.spinnerdistrito);
        BtnCargar_Documento = (Button) view.findViewById(R.id.BtnCargar_Documento);
        LblDocumento = (TextView) view.findViewById(R.id.LblDocumento);
        BtnRegistrar_Establecimiento = (Button) view.findViewById(R.id.BtnRegistrar_Establecimiento);
        Txtnombre = (EditText) view.findViewById(R.id.txtnombre);
        Txtdireccion = (EditText) view.findViewById(R.id.txtdireccion);
        Txttelefono = (EditText) view.findViewById(R.id.txttelefono);
        Txtdescripcion = (EditText) view.findViewById(R.id.txtdescripcion);

        Spinner_Categoria.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categorias));

        Spinner_Distrito.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Distritos));

        Map_View = (CustomMapView) view.findViewById(R.id.customMapView);
        Map_View.onCreate(savedInstanceState);
        Map_View.onResume();
        Map_View.getMapAsync(this);


        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
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

        BtnCargar_Documento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
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

        BtnRegistrar_Establecimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID_Establecimiento=mReference.push().getKey();
                mPresenter.UploadLogo(mStorageReference,ID_Establecimiento,Image_Uri);

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
            Imagen_Seleccionada = true;
        }

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_DOCUMENT)
        {
            Documento_Uri = data.getData();
            String filename = Documento_Uri.getLastPathSegment();
            String mimeType = getActivity().getContentResolver().getType(Documento_Uri);
            LblDocumento.setText("Imagen: " + filename + "." + mimeType.substring(6));
            Documento_Seleccionado = true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Mapa = googleMap;

        LatLng Tacna = new LatLng(-18.012580, -70.246520);
        Mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Tacna, 13));

        Mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Mapa.clear();
                String latitud = String.valueOf(latLng.latitude);
                String longitud = String.valueOf(latLng.longitude);

                MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.valueOf(latitud), Double.valueOf(longitud))).title("Marcador");
                Mapa.addMarker(marker);

                Punto_Geografico = latitud + "/" + longitud;
            }
        });

    }

    @Override
    public void onCreateEstablishmentSuccessful() {
        Toast.makeText(getActivity().getApplicationContext(),"Establecimiento Registrado",Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateEstablishmentFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadLogoSuccessful(String Url) {
        Url_Logo = Url;
        mPresenter.UploadDocument(mStorageReference,ID_Establecimiento,Documento_Uri);
    }

    @Override
    public void onUploadLogoFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadDocumentSuccessful(String Url) {
        Url_Documento = Url;
        Establecimiento_Modelo Establecimiento = new Establecimiento_Modelo(ID_Establecimiento,ID_Usuario,Txtnombre.getText().toString(),
                Spinner_Distrito.getSelectedItem().toString(),Spinner_Categoria.getSelectedItem().toString(),Txtdireccion.getText().toString(),Txttelefono.getText().toString(),
                Txtdescripcion.getText().toString(),0,0.0,Url_Logo,Url_Documento,Punto_Geografico,"Activo");
        mPresenter.CreateNewEstablishment(mReference,Establecimiento);
    }

    @Override
    public void onUploadDocumentFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionDataSuccessful(String ID_Usuario) {
        this.ID_Usuario=ID_Usuario;
    }
}
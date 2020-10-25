package com.example.tacnafdbusiness.vista;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.RegistrarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.presentador.RegistrarCupon_Presentador;
import com.example.tacnafdbusiness.presentador.RegistrarItemMenu_Presentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class RegistrarCupon_Vista extends Fragment implements RegistrarCupon.View {


    public RegistrarCupon_Vista() {
        // Required empty public constructor
    }

    public RegistrarCupon_Presentador mPresenter;
    public DatabaseReference mReference;
    public StorageReference mStorageReference;

    private static final String CERO = "0";
    private static final String BARRA = "/";

    public final Calendar Calendario = Calendar.getInstance();
    final int Mes = Calendario.get(Calendar.MONTH);
    final int Dia = Calendario.get(Calendar.DAY_OF_MONTH);
    final int Anio = Calendario.get(Calendar.YEAR);

    EditText TxtFecha_Inicio;
    EditText TxtFecha_Final;
    EditText TxtTitulo;
    EditText TxtDescripcion;
    EditText TxtPorcentaje_Descuento;

    FloatingActionButton fab;
    Button BtnRegistrar_Cupon;

    private static final int PICK_IMAGE = 100;

    Uri Image_Uri;

    ImageView Imagen_Cupon;

    Spinner Spinner_Estado;

    String[] estados = {"Seleccione un Estado", "Activo", "Inactivo"};

    String Id_Establecimiento = "";
    String Id_Cupoon = "";

    Boolean Imagen_Seleccionada = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_cupon__vista, container, false);

        mPresenter = new RegistrarCupon_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Cupon");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        TxtFecha_Inicio = (EditText) view.findViewById(R.id.txtfecha_inicio);
        TxtFecha_Final = (EditText) view.findViewById(R.id.txtfecha_final);
        Spinner_Estado = (Spinner) view.findViewById(R.id.spinnerestado);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_cupon);
        Imagen_Cupon = (ImageView) view.findViewById(R.id.imagen_cupon);
        BtnRegistrar_Cupon = (Button) view.findViewById(R.id.BtnRegistrar_Cupon);
        TxtDescripcion = (EditText) view.findViewById(R.id.txtdescripcion);
        TxtTitulo = (EditText) view.findViewById(R.id.txttitulo);
        TxtPorcentaje_Descuento = (EditText) view.findViewById(R.id.txtporcentaje_descuento);

        TxtFecha_Inicio.setFocusable(false);
        TxtFecha_Final.setFocusable(false);

        Spinner_Estado.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, estados));

        mPresenter.GetEstablishmentInfo(getActivity());

        TxtFecha_Inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog recogerFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final int mesActual = month + 1;

                        String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);

                        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                        TxtFecha_Inicio.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                    }
                }, Anio, Mes , Dia);
                recogerFecha.show();

            }
        });

        TxtFecha_Final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog recogerFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final int mesActual = month + 1;

                        String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);

                        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                        TxtFecha_Final.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                    }
                }, Anio, Mes , Dia);
                recogerFecha.show();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        });

        BtnRegistrar_Cupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.UploadCouponImage(mStorageReference, Id_Establecimiento, Image_Uri);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Image_Uri = data.getData();
            Imagen_Cupon.setImageURI(Image_Uri);
            Imagen_Seleccionada = true;

        }
    }

    @Override
    public void onSaveCouponSuccessful() {
        Toast.makeText(getActivity().getApplicationContext(),"Cupon Registrado Satisfactoriamente",Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSaveCouponFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadCouponImageSuccessful(String Url_Imagen) {

        Id_Cupoon = mReference.push().getKey();
        Cupon_Modelo cupon_modelo = new Cupon_Modelo(Id_Cupoon, Id_Establecimiento, TxtTitulo.getText().toString(), Url_Imagen, TxtDescripcion.getText().toString(),
                Integer.parseInt(TxtPorcentaje_Descuento.getText().toString()), TxtFecha_Inicio.getText().toString(), TxtFecha_Final.getText().toString(), Spinner_Estado.getSelectedItem().toString());

        mPresenter.SaveCoupon(mReference, cupon_modelo);
    }

    @Override
    public void onUploadCouponImageFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String Id_Establecimiento) {
        this.Id_Establecimiento = Id_Establecimiento;
    }
}
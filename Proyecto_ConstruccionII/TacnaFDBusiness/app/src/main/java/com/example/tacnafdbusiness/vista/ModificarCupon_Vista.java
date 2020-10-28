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
import com.example.tacnafdbusiness.interfaces.ModificarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.presentador.ModificarCupon_Presentador;
import com.example.tacnafdbusiness.presentador.ModificarItemMenu_Presentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class ModificarCupon_Vista extends Fragment implements ModificarCupon.View {


    public ModificarCupon_Vista() {
        // Required empty public constructor
    }

    public ModificarCupon_Presentador mPresenter;
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
    Button BtnModificar_Cupon;

    private static final int PICK_IMAGE = 100;

    Uri Image_Uri;

    ImageView Imagen_Cupon;

    Spinner Spinner_Estado;

    String[] estados = {"Seleccione un Estado", "Activo", "Inactivo"};

    String Id_Establecimiento = "";
    String Id_Cupon = "";
    String Url_Imagen = "";

    Bundle Cupon_Info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_cupon__vista, container, false);

        Cupon_Info = getArguments();
        Id_Cupon = Cupon_Info.getString("Id_Cupon");

        mPresenter =new ModificarCupon_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Cupon");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        TxtFecha_Inicio = (EditText) view.findViewById(R.id.txtfecha_inicio);
        TxtFecha_Final = (EditText) view.findViewById(R.id.txtfecha_final);
        Spinner_Estado = (Spinner) view.findViewById(R.id.spinnerestado);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_cupon);
        Imagen_Cupon = (ImageView) view.findViewById(R.id.imagen_cupon);
        BtnModificar_Cupon = (Button) view.findViewById(R.id.BtnModificar_Cupon);
        TxtDescripcion = (EditText) view.findViewById(R.id.txtdescripcion);
        TxtTitulo = (EditText) view.findViewById(R.id.txttitulo);
        TxtPorcentaje_Descuento = (EditText) view.findViewById(R.id.txtporcentaje_descuento);

        TxtFecha_Inicio.setFocusable(false);
        TxtFecha_Final.setFocusable(false);

        Spinner_Estado.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, estados));

        mPresenter.GetCouponData(mReference, Id_Cupon);

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

        BtnModificar_Cupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cupon_Modelo cupon_modelo = new Cupon_Modelo(Id_Cupon, Id_Establecimiento, TxtTitulo.getText().toString(), Url_Imagen, TxtDescripcion.getText().toString(),
                        Integer.parseInt(TxtPorcentaje_Descuento.getText().toString()) ,TxtFecha_Inicio.getText().toString(), TxtFecha_Final.getText().toString(), Spinner_Estado.getSelectedItem().toString());

                mPresenter.UpdateCouponData(mReference, cupon_modelo);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Image_Uri = data.getData();
            Imagen_Cupon.setImageURI(Image_Uri);
            mPresenter.UpdateCouponImage(mStorageReference, mReference, Url_Imagen, Id_Establecimiento, Id_Cupon, Image_Uri);
        }
    }

    @Override
    public void onUpdateCouponDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateCouponDataSuccessful() {
        Toast.makeText(getActivity(),"Cupon Modificado Satisfactoriamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateCouponImageFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateCouponImageSuccessful(String Url_Imagen) {
        this.Url_Imagen = Url_Imagen;
        Toast.makeText(getActivity(),"Imagen Actualizada Satisfactoriamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCouponDataSuccessful(Cupon_Modelo cupon_modelo) {

        Picasso.with(getActivity()).load(cupon_modelo.getUrl_Imagen()).into(Imagen_Cupon);
        Id_Establecimiento = cupon_modelo.getId_Establecimiento();
        Url_Imagen = cupon_modelo.getUrl_Imagen();
        TxtTitulo.setText(cupon_modelo.getTitulo());
        TxtFecha_Inicio.setText(cupon_modelo.getFecha_Inicio());
        TxtFecha_Final.setText(cupon_modelo.getFecha_Fin());
        TxtDescripcion.setText(cupon_modelo.getDescripcion());
        TxtPorcentaje_Descuento.setText(String.valueOf(cupon_modelo.getPorcentaje_Descuento()));

        for(int i=0; i<estados.length; i++){
            if(cupon_modelo.getEstado().equals(estados[i])){
                Spinner_Estado.setSelection(i);
                break;
            }
        }

    }

    @Override
    public void onGetCouponDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }
}
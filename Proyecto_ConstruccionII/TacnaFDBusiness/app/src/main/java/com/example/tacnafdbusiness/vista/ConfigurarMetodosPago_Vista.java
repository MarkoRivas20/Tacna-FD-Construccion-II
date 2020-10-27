package com.example.tacnafdbusiness.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.ConfigurarMetodosPago;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.example.tacnafdbusiness.presentador.ConfigurarMetodosPago_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class ConfigurarMetodosPago_Vista extends Fragment implements ConfigurarMetodosPago.View {

    public ConfigurarMetodosPago_Vista() {
        // Required empty public constructor
    }

    public ConfigurarMetodosPago_Presentador mPresenter;
    public DatabaseReference mReference;
    public StorageReference mStorageReference;

    private static final int PICK_IMAGE = 100;

    Uri Image_Uri;

    ImageView QR_Vendemas;

    RadioButton RB_Paypal;
    RadioButton RB_Tarjetas;
    RadioButton RB_Vendemas;

    Boolean Paypal = false;
    Boolean Tarjetas = false;
    Boolean Vendemas = false;

    EditText TxtCodigo_Paypal;
    EditText TxtPublica_culqi;
    EditText TxtSecreta_culqi;

    CardView Cardview_Vendemas;

    String Id_Establecimiento = "";

    Button BtnActualizar_Metodos_Pago;

    String Codigo_Paypal = null;
    String Codigo_Culqi = null;
    String Url_Qr = null;

    Boolean Imagen_Seleccionada = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configurar_metodos_pago__vista, container, false);

        mPresenter=new ConfigurarMetodosPago_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        RB_Paypal = (RadioButton) view.findViewById(R.id.RD_Paypal);
        RB_Tarjetas = (RadioButton) view.findViewById(R.id.RD_Tarjetas);
        RB_Vendemas = (RadioButton) view.findViewById(R.id.RD_Vendemas);
        TxtCodigo_Paypal = (EditText) view.findViewById(R.id.TxtCodigo_Paypal);
        TxtPublica_culqi = (EditText) view.findViewById(R.id.TxtPublica_culqi);
        TxtSecreta_culqi = (EditText) view.findViewById(R.id.TxtSecreta_culqi);
        QR_Vendemas = (ImageView) view.findViewById(R.id.QR_Vendemas);
        Cardview_Vendemas = (CardView) view.findViewById(R.id.Cardview_Vendemas);
        BtnActualizar_Metodos_Pago = (Button) view.findViewById(R.id.BtnActualizar_Metodos_Pago);

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetPaymentsMethods(mReference, Id_Establecimiento);


        RB_Paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Paypal)
                {
                    RB_Paypal.setChecked(true);
                    TxtCodigo_Paypal.setVisibility(View.VISIBLE);
                }
                else
                {
                    RB_Paypal.setChecked(false);
                    TxtCodigo_Paypal.setVisibility(View.GONE);
                }
                Paypal = RB_Paypal.isChecked();
            }
        });

        RB_Tarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tarjetas)
                {
                    RB_Tarjetas.setChecked(true);
                    TxtPublica_culqi.setVisibility(View.VISIBLE);
                    TxtSecreta_culqi.setVisibility(View.VISIBLE);
                }
                else
                {
                    RB_Tarjetas.setChecked(false);
                    TxtPublica_culqi.setVisibility(View.GONE);
                    TxtSecreta_culqi.setVisibility(View.GONE);
                }
                Tarjetas = RB_Tarjetas.isChecked();
            }
        });

        RB_Vendemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Vendemas)
                {
                    if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                        RB_Vendemas.setChecked(true);
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, PICK_IMAGE);
                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }
                }
                else
                {
                    RB_Vendemas.setChecked(false);
                    QR_Vendemas.setImageDrawable(null);
                    Cardview_Vendemas.setVisibility(View.GONE);
                }
                Vendemas = RB_Vendemas.isChecked();
            }
        });

        BtnActualizar_Metodos_Pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Paypal){
                    Codigo_Paypal = TxtCodigo_Paypal.getText().toString();
                }
                else
                {
                    Codigo_Paypal = null;
                }

                if(Tarjetas){
                    Codigo_Culqi = TxtPublica_culqi.getText().toString() + "/" + TxtSecreta_culqi.getText().toString();
                }
                else
                {
                    Codigo_Culqi = null;
                }


                if(Imagen_Seleccionada){

                    if(Url_Qr != null){
                        mPresenter.DeleteQRImage(Url_Qr);
                    }
                    mPresenter.UpdateQRImage(mStorageReference, Id_Establecimiento, Image_Uri);
                    Imagen_Seleccionada = false;
                }
                else
                {
                    if(Vendemas){
                        mPresenter.UpdatePaymentsMethods(mReference, Id_Establecimiento, Codigo_Paypal, Codigo_Culqi, Url_Qr);
                    }
                    else
                    {
                        
                        if(Url_Qr != null)
                        {
                            mPresenter.DeleteQRImage(Url_Qr);
                        }
                        mPresenter.UpdatePaymentsMethods(mReference, Id_Establecimiento, Codigo_Paypal, Codigo_Culqi, null);
                    }
                }



            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Image_Uri = data.getData();
            QR_Vendemas.setImageURI(Image_Uri);
            Cardview_Vendemas.setVisibility(View.VISIBLE);
            Imagen_Seleccionada = true;
        }
    }

    @Override
    public void onGetPaymentsMethodsSuccessful(Establecimiento_Modelo establecimiento_modelo) {
        if(establecimiento_modelo.getCodigo_Paypal() == null){
            RB_Paypal.setChecked(false);
            TxtCodigo_Paypal.setVisibility(View.GONE);
        }
        else
        {
            RB_Paypal.setChecked(true);
            TxtCodigo_Paypal.setVisibility(View.VISIBLE);
            TxtCodigo_Paypal.setText(establecimiento_modelo.getCodigo_Paypal());
            Codigo_Paypal = establecimiento_modelo.getCodigo_Paypal();
        }
        Paypal = RB_Paypal.isChecked();

        if(establecimiento_modelo.getCodigo_Culqi() == null){
            RB_Tarjetas.setChecked(false);
            TxtPublica_culqi.setVisibility(View.GONE);
            TxtSecreta_culqi.setVisibility(View.GONE);
        }
        else
        {
            RB_Tarjetas.setChecked(true);
            TxtPublica_culqi.setVisibility(View.VISIBLE);
            TxtSecreta_culqi.setVisibility(View.VISIBLE);
            Codigo_Culqi = establecimiento_modelo.getCodigo_Culqi();
            String[] Codigos_Culqi = establecimiento_modelo.getCodigo_Culqi().split("/");
            TxtPublica_culqi.setText(Codigos_Culqi[0]);
            TxtSecreta_culqi.setText(Codigos_Culqi[1]);
        }
        Tarjetas = RB_Tarjetas.isChecked();

        if(establecimiento_modelo.getUrl_Qr() == null){
            RB_Vendemas.setChecked(false);
            Cardview_Vendemas.setVisibility(View.GONE);
        }
        else
        {
            RB_Vendemas.setChecked(true);
            Cardview_Vendemas.setVisibility(View.VISIBLE);
            Url_Qr = establecimiento_modelo.getUrl_Qr();
            Picasso.with(getActivity()).load(establecimiento_modelo.getUrl_Qr()).into(QR_Vendemas);
        }
        Vendemas = RB_Vendemas.isChecked();
    }

    @Override
    public void onGetPaymentsMethodsFailure() {

    }

    @Override
    public void onUpdatePaymentsMethodsSuccessful() {

    }

    @Override
    public void onUpdatePaymentsMethodsFailure() {

    }

    @Override
    public void onUpdateQRImageSuccessful(String Url_Qr) {
        this.Url_Qr = Url_Qr;
        mPresenter.UpdatePaymentsMethods(mReference, Id_Establecimiento, Codigo_Paypal, Codigo_Culqi, Url_Qr);
    }

    @Override
    public void onUpdateQRImageFailure() {

    }

    @Override
    public void onDeleteQRImageSuccessful() {

    }

    @Override
    public void onDeleteQRImageFailure() {

    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String Id_Establecimiento) {
        this.Id_Establecimiento = Id_Establecimiento;
    }
}
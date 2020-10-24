package com.example.tacnafdbusiness.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.OpcionesEstablecimiento;
import com.example.tacnafdbusiness.presentador.OpcionesEstablecimiento_Presentador;
import com.squareup.picasso.Picasso;


public class OpcionesEstablecimiento_Vista extends Fragment implements OpcionesEstablecimiento.View {

    public OpcionesEstablecimiento_Vista() {
        // Required empty public constructor
    }

    public OpcionesEstablecimiento_Presentador mPresenter;

    TextView LblNombre_Establecimiento;

    ImageView Logo_Establecimiento;
    ImageView ImgDatos_Generales;
    ImageView ImgImagenes;
    ImageView ImgRepartidores;

    ModificarEstablecimiento_Vista modificarEstablecimiento_vista;
    CRUDImagenes_Vista crudImagenes_vista;
    CRUDRepartidor_Vista crudRepartidor_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opciones_establecimiento__vista, container, false);

        LblNombre_Establecimiento = (TextView) view.findViewById(R.id.LblNombre_Establecimiento);
        Logo_Establecimiento = (ImageView) view.findViewById(R.id.logo_establecimiento);
        ImgDatos_Generales = (ImageView) view.findViewById(R.id.ImgDatos_Generales);
        ImgImagenes = (ImageView) view.findViewById(R.id.ImgImagenes);
        ImgRepartidores = (ImageView) view.findViewById(R.id.ImgRepartidores);

        modificarEstablecimiento_vista = new ModificarEstablecimiento_Vista();
        crudImagenes_vista = new CRUDImagenes_Vista();
        crudRepartidor_vista = new CRUDRepartidor_Vista();

        mPresenter=new OpcionesEstablecimiento_Presentador(this);

        mPresenter.GetEstablishmentInfo(getActivity().getApplicationContext());

        ImgDatos_Generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, modificarEstablecimiento_vista).addToBackStack(null).commit();
            }
        });

        ImgImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, crudImagenes_vista).addToBackStack(null).commit();
            }
        });

        ImgRepartidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, crudRepartidor_vista).addToBackStack(null).commit();
            }
        });



        return view;
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String Nombre_Establecimiento, String Url_Logo) {

        LblNombre_Establecimiento.setText(Nombre_Establecimiento);
        Picasso.with(getActivity().getApplicationContext()).load(Url_Logo).into(Logo_Establecimiento);

    }

    @Override
    public void onGetEstablishmentInfoFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }
}
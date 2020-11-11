package com.example.tacnafdcliente.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.ViewPager_Adaptador;
import com.example.tacnafdcliente.interfaces.InformacionEstablecimiento;
import com.example.tacnafdcliente.interfaces.OpcionesEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.presentador.Login_Presentador;
import com.example.tacnafdcliente.presentador.OpcionesEstablecimiento_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class OpcionesEstablecimiento_Vista extends Fragment implements OpcionesEstablecimiento.View {


    public OpcionesEstablecimiento_Vista() {
        // Required empty public constructor
    }

    public OpcionesEstablecimiento_Presentador mPresenter;
    public DatabaseReference mReference_ImagenEstablecimiento;
    public DatabaseReference mReference_Establecimiento;

    String ID_Establecimiento = "";

    TextView LblNombre_Establecimiento;

    ImageView Logo_Establecimiento;
    ImageView ImgDatos_Generales;
    ImageView ImgResenas;
    ImageView ImgMenu;
    ImageView ImgCupon;

    ViewPager View_Pager;
    ViewPager_Adaptador Adaptador;

    ListarItemMenu_Vista listarItemMenu_vista;
    ListarCupon_Vista listarCupon_vista;
    InformacionEstablecimiento_Vista informacionEstablecimiento_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opciones_establecimiento__vista, container, false);


        mPresenter = new OpcionesEstablecimiento_Presentador(this);
        mReference_ImagenEstablecimiento = FirebaseDatabase.getInstance().getReference().child("Imagen_Establecimiento");
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        View_Pager = view.findViewById(R.id.View_Pager);
        LblNombre_Establecimiento = (TextView) view.findViewById(R.id.LblNombre_Establecimiento);
        Logo_Establecimiento = (ImageView) view.findViewById(R.id.logo_establecimiento);
        ImgDatos_Generales = (ImageView) view.findViewById(R.id.ImgDatos_Generales);
        ImgResenas = (ImageView) view.findViewById(R.id.ImgResenas);
        ImgMenu = (ImageView) view.findViewById(R.id.ImgMenu);
        ImgCupon = (ImageView) view.findViewById(R.id.ImgCupon);

        listarItemMenu_vista = new ListarItemMenu_Vista();
        listarCupon_vista = new ListarCupon_Vista();
        informacionEstablecimiento_vista = new InformacionEstablecimiento_Vista();

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetImagesEstablishment(mReference_ImagenEstablecimiento, ID_Establecimiento);
        mPresenter.GetEstablishmentData(mReference_Establecimiento, ID_Establecimiento);

        ImgDatos_Generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, informacionEstablecimiento_vista).addToBackStack(null).commit();
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
                }

            }
        });

        ImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, listarItemMenu_vista).addToBackStack(null).commit();
            }
        });

        ImgCupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, listarCupon_vista).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento) {

        LblNombre_Establecimiento.setText(Establecimiento.getNombre());
        Picasso.get().load(Establecimiento.getUrl_Imagen_Logo()).into(Logo_Establecimiento);

    }

    @Override
    public void onGetEstablishmentDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetImagesEstablishmentSuccessful(String[] Imagene_Urls) {
        Adaptador = new ViewPager_Adaptador(getActivity(),Imagene_Urls);
        View_Pager.setAdapter(Adaptador);


    }

    @Override
    public void onGetImagesEstablishmentFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String Id_Establecimiento) {
        ID_Establecimiento = Id_Establecimiento;
    }

    @Override
    public void onGetEstablishmentInfoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }
}
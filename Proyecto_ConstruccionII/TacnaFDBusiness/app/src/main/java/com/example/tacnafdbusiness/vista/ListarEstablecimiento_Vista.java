package com.example.tacnafdbusiness.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Establecimiento_Adaptador;
import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.presentador.ListarEstablecimiento_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ListarEstablecimiento_Vista extends Fragment implements ListarEstablecimiento.View {


    public ListarEstablecimiento_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private Establecimiento_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public ListarEstablecimiento_Presentador mPresenter;
    public DatabaseReference mReference;

    RegistrarEstablecimiento_Vista registrarEstablecimiento_vista;

    Button BtnRegistro_Establecimiento;

    PantallaPrincipal_Vista pantallaPrincipal_vista;

    String Id_Usuario = "";

    TextView LblNo_Establecimiento;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_establecimiento__vista, container, false);

        pantallaPrincipal_vista=new PantallaPrincipal_Vista();

        registrarEstablecimiento_vista = new RegistrarEstablecimiento_Vista();

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_ListaEstablecimiento);
        BtnRegistro_Establecimiento = (Button) view.findViewById(R.id.BtnRegistro_Establecimiento);
        LblNo_Establecimiento = (TextView) view.findViewById(R.id.LblNo_Establecimiento);

        mPresenter=new ListarEstablecimiento_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        mPresenter.GetSessionData(getActivity().getApplicationContext());

        mPresenter.SearchEstablishment(mReference,Id_Usuario);

        BtnRegistro_Establecimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, registrarEstablecimiento_vista).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onSearchEstablishmentSuccessful(ArrayList<Establecimiento_Modelo> establecimiento) {

        LblNo_Establecimiento.setVisibility(View.GONE);

        Adaptador = new Establecimiento_Adaptador(establecimiento, getActivity().getApplicationContext());
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);


    }

    @Override
    public void onSearchEstablishmentFailure() {
        LblNo_Establecimiento.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSessionDataSuccessful(String ID_Usuario) {
        Id_Usuario = ID_Usuario;
    }
}
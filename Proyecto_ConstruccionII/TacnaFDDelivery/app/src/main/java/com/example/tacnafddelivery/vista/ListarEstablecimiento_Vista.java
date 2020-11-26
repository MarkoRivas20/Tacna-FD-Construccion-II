package com.example.tacnafddelivery.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.adaptador.Establecimiento_Adaptador;
import com.example.tacnafddelivery.interfaces.ListarEstablecimiento;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafddelivery.presentador.ListarEstablecimiento_Presentador;
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
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Repartidor_Establecimiento;

    ListarPedido_Vista listarPedido_vista;

    String ID_Usuario = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_establecimiento__vista, container, false);

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Establecimiento);

        mPresenter = new ListarEstablecimiento_Presentador(this);
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Repartidor_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Repartidor_Establecimiento");

        listarPedido_vista = new ListarPedido_Vista();

        mPresenter.GetSessionData(getActivity());
        mPresenter.GetEstablishments(mReference_Repartidor_Establecimiento, ID_Usuario);

        return view;
    }

    @Override
    public void onGetEstablishmentsSuccessful(ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento) {
        mPresenter.GetEstablishmentInfo(mReference_Establecimiento, Repartidores_Establecimiento);
    }

    @Override
    public void onGetEstablishmentsFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(final ArrayList<Establecimiento_Modelo> Establecimientos) {

        Adaptador = new Establecimiento_Adaptador(Establecimientos, getActivity());
        Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.SaveIDEstablishment(getActivity(), Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getID_Establecimiento());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, listarPedido_vista).addToBackStack(null).commit();
            }
        });
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);
    }

    @Override
    public void onGetEstablishmentInfoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSessionDataSuccessful(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }
}
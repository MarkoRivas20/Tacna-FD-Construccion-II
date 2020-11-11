package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.Cupon_Adaptador;
import com.example.tacnafdcliente.interfaces.ListarCupon;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.example.tacnafdcliente.presentador.ListarCupon_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ListarCupon_Vista extends Fragment implements ListarCupon.View {


    public ListarCupon_Vista() {
        // Required empty public constructor
    }

    public ListarCupon_Presentador mPresenter;
    public DatabaseReference mReference;

    private RecyclerView Recycler_View;
    private Cupon_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    String ID_Establecimiento = "";

    TextView LblNo_Cupon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_cupon__vista, container, false);

        LblNo_Cupon = (TextView) view.findViewById(R.id.LblNo_Cupon);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Cupon);

        mPresenter =new ListarCupon_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Cupon");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.ListCoupon(mReference, ID_Establecimiento);


        return view;
    }

    @Override
    public void onListCouponSuccessful(ArrayList<Cupon_Modelo> Cupones, Boolean Existe_Cupon) {
        Adaptador = new Cupon_Adaptador(Cupones, getActivity());

        Layout_Manager = new GridLayoutManager(getActivity(), 2);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

        if(Existe_Cupon)
        {
            LblNo_Cupon.setVisibility(View.GONE);
        }
        else
        {
            LblNo_Cupon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListCouponFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}
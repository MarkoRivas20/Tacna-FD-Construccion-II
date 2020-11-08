package com.example.tacnafdbusiness.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Cupon_Adaptador;
import com.example.tacnafdbusiness.adaptador.ItemMenu_Adaptador;
import com.example.tacnafdbusiness.interfaces.ListarCupon;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.presentador.ListarCupon_Presentador;
import com.example.tacnafdbusiness.presentador.ListarItemMenu_Presentador;
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

    Button BtnAgregar_Cupon;

    TextView LblNo_Cupon;

    RegistrarCupon_Vista registrarCupon_vista;
    ModificarCupon_Vista modificarCupon_vista;

    Bundle Cupon_Info = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_cupon__vista, container, false);

        BtnAgregar_Cupon = (Button) view.findViewById(R.id.BtnAgregar_Cupon);
        LblNo_Cupon = (TextView) view.findViewById(R.id.LblNo_Cupon);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Cupones);

        registrarCupon_vista = new RegistrarCupon_Vista();
        modificarCupon_vista = new ModificarCupon_Vista();

        mPresenter =new ListarCupon_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Cupon");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.ListCoupon(mReference, ID_Establecimiento);

        BtnAgregar_Cupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, registrarCupon_vista).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onListCouponSuccessful(final ArrayList<Cupon_Modelo> Cupones, Boolean Existe_Cupon) {
        Adaptador = new Cupon_Adaptador(Cupones, getActivity());
        Adaptador.setOnItemClickListener(new Cupon_Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),"Mantenga presionado para ver las opciones", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdate(int position) {
                Cupon_Info.putString("Id_Cupon", Cupones.get(position).getId_Cupon());
                modificarCupon_vista.setArguments(Cupon_Info);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, modificarCupon_vista).addToBackStack(null).commit();
            }

            @Override
            public void onCancel(int position) {

            }
        });

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
package com.example.tacnafddelivery.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.adaptador.Pedido_Adaptador;
import com.example.tacnafddelivery.interfaces.ListarPedido;
import com.example.tacnafddelivery.modelo.Establecimiento_Modelo;
import com.example.tacnafddelivery.modelo.Pedido_Modelo;
import com.example.tacnafddelivery.presentador.ListarPedido_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ListarPedido_Vista extends Fragment implements ListarPedido.View {

    public ListarPedido_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private Pedido_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public ListarPedido_Presentador mPresenter;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Pedido;
    public DatabaseReference mReference_Cliente;

    ImageView ImgLogo_Establecimiento;
    TextView TxtNombre_Establecimiento;
    TextView TxtDireccion;
    TextView LblNo_Pedidos;

    String ID_Establecimiento = "";

    DetallePedido_Vista detallePedido_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_pedido__vista, container, false);

        TxtNombre_Establecimiento = (TextView) view.findViewById(R.id.TxtNombre_Establecimiento);
        TxtDireccion = (TextView) view.findViewById(R.id.TxtDireccion);
        ImgLogo_Establecimiento = (ImageView) view.findViewById(R.id.ImgLogo_Establecimiento);
        LblNo_Pedidos = (TextView) view.findViewById(R.id.LblNo_Pedidos);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Pedidos);

        mPresenter = new ListarPedido_Presentador(this);
        mReference_Pedido = FirebaseDatabase.getInstance().getReference().child("Pedido");
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Cliente = FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");

        detallePedido_vista = new DetallePedido_Vista();

        mPresenter.GetIDEstablishment(getActivity());
        mPresenter.GetEstablishmentInfo(mReference_Establecimiento, ID_Establecimiento);
        mPresenter.GetOrders(mReference_Pedido, ID_Establecimiento);

        return view;
    }

    @Override
    public void onGetOrdersSuccessful(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Resena) {

        if(Existe_Resena)
        {
            LblNo_Pedidos.setVisibility(View.GONE);
            mPresenter.SearchClientName(mReference_Cliente, Pedidos);
        }
        else
        {
            LblNo_Pedidos.setVisibility(View.VISIBLE);
            Pedidos.clear();
            Adaptador = new Pedido_Adaptador(Pedidos, getActivity());
            Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            Recycler_View.setLayoutManager(Layout_Manager);
            Recycler_View.setAdapter(Adaptador);
        }
    }

    @Override
    public void onGetOrdersFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchClientNameSuccessful(final ArrayList<Pedido_Modelo> Pedidos) {
        Adaptador = new Pedido_Adaptador(Pedidos, getActivity());
        Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    mPresenter.SaveIDOrder(getActivity(), Pedidos.get(Recycler_View.getChildAdapterPosition(v)).getID_Pedido());
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, detallePedido_vista).addToBackStack(null).commit();
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
                }


            }
        });
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);
    }

    @Override
    public void onSearchClientNameFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(Establecimiento_Modelo Establecimiento) {
        TxtNombre_Establecimiento.setText(Establecimiento.getNombre());
        TxtDireccion.setText(Establecimiento.getDireccion());
        Picasso.with(getActivity()).load(Establecimiento.getUrl_Imagen_Logo()).into(ImgLogo_Establecimiento);
    }

    @Override
    public void onGetEstablishmentInfoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetIDEstablishmentSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}
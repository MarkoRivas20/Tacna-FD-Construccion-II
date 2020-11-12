package com.example.tacnafdbusiness.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Pedido_Adaptador;
import com.example.tacnafdbusiness.interfaces.ListarPedido;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
import com.example.tacnafdbusiness.presentador.ListarPedido_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListarPedido_Vista extends Fragment implements ListarPedido.View {

    public ListarPedido_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private Pedido_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public ListarPedido_Presentador mPresenter;
    public DatabaseReference mReference;

    String ID_Establecimiento = "";

    TextView LblNo_Pedidos;

    ArrayList<Pedido_Modelo> Pedidos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_pedido__vista, container, false);

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Pedidos);
        LblNo_Pedidos = (TextView) view.findViewById(R.id.LblNo_Pedidos);

        mPresenter = new ListarPedido_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Pedido");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetReviews(mReference, ID_Establecimiento);

        return view;
    }

    @Override
    public void onGetReviewsSuccessful(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Pedido) {
        this.Pedidos = Pedidos;

        if (Existe_Pedido) {
            LblNo_Pedidos.setVisibility(View.GONE);
            mReference = FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");
            mPresenter.SearchClientName(mReference, Pedidos);
        } else {
            LblNo_Pedidos.setVisibility(View.VISIBLE);
            Pedidos.clear();
            Adaptador = new Pedido_Adaptador(Pedidos, getActivity());
            Layout_Manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            Recycler_View.setLayoutManager(Layout_Manager);
            Recycler_View.setAdapter(Adaptador);
        }
    }

    @Override
    public void onGetReviewsFailure() {
        Toast.makeText(getActivity(), "Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchClientNameSuccessful(ArrayList<Pedido_Modelo> Pedidos) {
        Adaptador = new Pedido_Adaptador(Pedidos, getActivity());
        Layout_Manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

    }

    @Override
    public void onSearchClientNameFailure() {
        Toast.makeText(getActivity(), "Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}
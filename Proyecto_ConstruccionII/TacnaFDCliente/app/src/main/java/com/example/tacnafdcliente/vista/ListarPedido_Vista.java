package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.Pedido_Adaptador;
import com.example.tacnafdcliente.interfaces.ListarPedido;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.presentador.ListarPedido_Presentador;
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
    public DatabaseReference mReference_Pedido;
    public DatabaseReference mReference_Establecimiento;

    TextView LblNo_Pedidos;

    String ID_Usuario = "";

    ArrayList<Pedido_Modelo> Pedidos = new ArrayList<>();

    SeguimientoPedido_Vista seguimientoPedido_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_pedido__vista, container, false);

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Pedidos);
        LblNo_Pedidos = (TextView) view.findViewById(R.id.LblNo_Pedidos);

        mPresenter = new ListarPedido_Presentador(this);
        mReference_Pedido = FirebaseDatabase.getInstance().getReference().child("Pedido");
        mReference_Establecimiento= FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        seguimientoPedido_vista = new SeguimientoPedido_Vista();

        mPresenter.GetSessionData(getActivity());
        mPresenter.GetOrders(mReference_Pedido, ID_Usuario);

        return view;
    }

    @Override
    public void onGetOrdersSuccessful(ArrayList<Pedido_Modelo> Pedidos, Boolean Existe_Resena) {
        this.Pedidos = Pedidos;

        if(Existe_Resena)
        {
            LblNo_Pedidos.setVisibility(View.GONE);
            mPresenter.SearchEstablishmentName(mReference_Establecimiento,Pedidos);
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
    public void onSearchEstablishmentNameSuccessful(final ArrayList<Pedido_Modelo> Pedidos) {
        Adaptador = new Pedido_Adaptador(Pedidos, getActivity());
        Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Pedidos.get(Recycler_View.getChildAdapterPosition(v)).getEstado().equals("En Camino"))
                {
                    mPresenter.SaveIDOrderAndIDEstablishment(getActivity(), Pedidos.get(Recycler_View.getChildAdapterPosition(v)).getID_Pedido(),
                            Pedidos.get(Recycler_View.getChildAdapterPosition(v)).getID_Establecimiento());

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, seguimientoPedido_vista).addToBackStack(null).commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"No se puede hacer seguimiento a este pedido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);
    }

    @Override
    public void onSearchEstablishmentNameFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSessionDataSuccessful(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }
}
package com.example.tacnafdcliente.vista;

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

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.ItemMenu_Adaptador;
import com.example.tacnafdcliente.interfaces.ListarItemMenu;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.example.tacnafdcliente.presentador.ListarItemMenu_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ListarItemMenu_Vista extends Fragment implements ListarItemMenu.View {



    public ListarItemMenu_Vista() {
        // Required empty public constructor
    }

    public ListarItemMenu_Presentador mPresenter;
    public DatabaseReference mReference;

    private RecyclerView Recycler_View;
    private ItemMenu_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    String ID_Establecimiento = "";

    TextView LblNo_Items_Menu;

    Button BtnRealizar_Pedido;

    RealizarPedidoDatos_Vista realizarPedidoDatos_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_item_menu__vista, container, false);

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Items_Menu);
        LblNo_Items_Menu = (TextView) view.findViewById(R.id.LblNo_Items_Menu);
        BtnRealizar_Pedido = (Button) view.findViewById(R.id.BtnRealizar_Pedido);

        realizarPedidoDatos_vista = new RealizarPedidoDatos_Vista();

        mPresenter =new ListarItemMenu_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("ItemMenu");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.ListItemMenu(mReference, ID_Establecimiento);

        BtnRealizar_Pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, realizarPedidoDatos_vista).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onListItemMenuSuccessful(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu) {
        Adaptador = new ItemMenu_Adaptador(Items_Menu, getActivity());
        Layout_Manager = new GridLayoutManager(getActivity(), 2);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

        if(Existe_Item_Menu)
        {
            LblNo_Items_Menu.setVisibility(View.GONE);
            BtnRealizar_Pedido.setVisibility(View.VISIBLE);
        }
        else
        {
            LblNo_Items_Menu.setVisibility(View.VISIBLE);
            BtnRealizar_Pedido.setVisibility(View.GONE);
        }
    }

    @Override
    public void onListItemMenuFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}
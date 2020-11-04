package com.example.tacnafdbusiness.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Imagen_Adaptador;
import com.example.tacnafdbusiness.adaptador.ItemMenu_Adaptador;
import com.example.tacnafdbusiness.interfaces.ListarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.example.tacnafdbusiness.presentador.CRUDImagenes_Presentador;
import com.example.tacnafdbusiness.presentador.ListarItemMenu_Presentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

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

    Button BtnAgregar_Item_Menu;

    RegistrarItemMenu_Vista registrarItemMenu_vista;
    ModificarItemMenu_Vista modificarItemMenu_vista;

    TextView LblNo_Items_Menu;

    Bundle Item_Menu_Info = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_item_menu__vista, container, false);

        BtnAgregar_Item_Menu = (Button) view.findViewById(R.id.BtnAgregar_Item_Menu);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Items_Menu);
        LblNo_Items_Menu = (TextView) view.findViewById(R.id.LblNo_Items_Menu);

        registrarItemMenu_vista = new RegistrarItemMenu_Vista();
        modificarItemMenu_vista = new ModificarItemMenu_Vista();

        mPresenter =new ListarItemMenu_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("ItemMenu");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.ListItemMenu(mReference, ID_Establecimiento);


        BtnAgregar_Item_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, registrarItemMenu_vista).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onListItemMenuSuccessful(final ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu) {

        Adaptador = new ItemMenu_Adaptador(Items_Menu, getActivity());
        Adaptador.setOnItemClickListener(new ItemMenu_Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onUpdate(int position) {

                Item_Menu_Info.putString("Id_Item_Menu", Items_Menu.get(position).getID_Item_Menu());
                modificarItemMenu_vista.setArguments(Item_Menu_Info);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, modificarItemMenu_vista).addToBackStack(null).commit();
            }

            @Override
            public void onDelete(int position) {
                mPresenter.DeleteItemMenu(mReference, Items_Menu.get(position).getID_Item_Menu(), Items_Menu.get(position).getUrl_Imagen());
            }

            @Override
            public void onCancel(int position) {

            }
        });
        Layout_Manager = new GridLayoutManager(getActivity(), 2);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

        if(Existe_Item_Menu)
        {
            LblNo_Items_Menu.setVisibility(View.GONE);
        }
        else
        {
            LblNo_Items_Menu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListItemMenuFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemMenuSuccessful() {
        Toast.makeText(getActivity().getApplicationContext(),"Item Eliminado Satisfactoriamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemMenuFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}
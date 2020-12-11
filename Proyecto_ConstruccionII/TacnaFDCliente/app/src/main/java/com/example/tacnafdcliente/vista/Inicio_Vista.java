package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.CuponUsuario_Adaptador;
import com.example.tacnafdcliente.adaptador.EstablecimientoInicio_Adaptador;
import com.example.tacnafdcliente.adaptador.ItemMenuInicio_Adaptador;
import com.example.tacnafdcliente.interfaces.Inicio;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.example.tacnafdcliente.presentador.Inicio_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Inicio_Vista extends Fragment implements Inicio.View {

    public Inicio_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private EstablecimientoInicio_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    private RecyclerView Recycler_View_Item_Menu;
    private ItemMenuInicio_Adaptador Adaptador_Item_Menu;
    private RecyclerView.LayoutManager Layout_Manager_Item_Menu;

    public Inicio_Presentador mPresenter;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Items_Menu;

    ImageView ImgRestaurant;
    ImageView ImgCoffee;
    ImageView ImgBakery;
    ImageView ImgFoodTruck;
    CardView ImgRestaurant_Barra;
    CardView ImgCoffee_Barra;
    CardView ImgBakery_Barra;
    CardView ImgFoodTruck_Barra;

    TextView LblNo_Establecimiento;

    ArrayList<Establecimiento_Modelo> Establecimientos_Populares = new ArrayList<>();
    ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();

    OpcionesEstablecimiento_Vista opcionesEstablecimiento_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio__vista, container, false);

        mPresenter = new Inicio_Presentador(this);
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Items_Menu = FirebaseDatabase.getInstance().getReference().child("ItemMenu");

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Establecimiento_Inicio);
        Recycler_View_Item_Menu = (RecyclerView) view.findViewById(R.id.Recycler_Items_Menu_Inicio);

        opcionesEstablecimiento_vista = new OpcionesEstablecimiento_Vista();

        ImgRestaurant = (ImageView) view.findViewById(R.id.ImgRestaurant);
        ImgCoffee = (ImageView) view.findViewById(R.id.ImgCoffee);
        ImgBakery = (ImageView) view.findViewById(R.id.ImgBakery);
        ImgFoodTruck = (ImageView) view.findViewById(R.id.ImgFoodTruck);
        ImgRestaurant_Barra = (CardView) view.findViewById(R.id.ImgRestaurant_Barra);
        ImgCoffee_Barra = (CardView) view.findViewById(R.id.ImgCoffee_Barra);
        ImgBakery_Barra = (CardView) view.findViewById(R.id.ImgBakery_Barra);
        ImgFoodTruck_Barra = (CardView) view.findViewById(R.id.ImgFoodTruck_Barra);
        LblNo_Establecimiento = (TextView) view.findViewById(R.id.LblNo_Establecimiento);

        mPresenter.SearchEstablishmentByCategory(mReference_Establecimiento,"Restaurante");

        ImgRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.SearchEstablishmentByCategory(mReference_Establecimiento,"Restaurante");
                ImgRestaurant_Barra.setVisibility(View.VISIBLE);
                ImgCoffee_Barra.setVisibility(View.GONE);
                ImgBakery_Barra.setVisibility(View.GONE);
                ImgFoodTruck_Barra.setVisibility(View.GONE);
            }
        });

        ImgCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.SearchEstablishmentByCategory(mReference_Establecimiento,"Cafeteria");
                ImgRestaurant_Barra.setVisibility(View.GONE);
                ImgCoffee_Barra.setVisibility(View.VISIBLE);
                ImgBakery_Barra.setVisibility(View.GONE);
                ImgFoodTruck_Barra.setVisibility(View.GONE);
            }
        });

        ImgBakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.SearchEstablishmentByCategory(mReference_Establecimiento,"Panaderia");
                ImgRestaurant_Barra.setVisibility(View.GONE);
                ImgCoffee_Barra.setVisibility(View.GONE);
                ImgBakery_Barra.setVisibility(View.VISIBLE);
                ImgFoodTruck_Barra.setVisibility(View.GONE);
            }
        });

        ImgFoodTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.SearchEstablishmentByCategory(mReference_Establecimiento,"Food Truck");
                ImgRestaurant_Barra.setVisibility(View.GONE);
                ImgCoffee_Barra.setVisibility(View.GONE);
                ImgBakery_Barra.setVisibility(View.GONE);
                ImgFoodTruck_Barra.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onSearchEstablishmentByCategorySuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento) {

        this.Establecimientos = Establecimientos;

        if(Existe_Establecimiento)
        {
            LblNo_Establecimiento.setVisibility(View.GONE);
            mPresenter.GetFourBestEstablishment(Establecimientos);
            mPresenter.ListItemMenu(mReference_Items_Menu);
        }
        else
        {
            LblNo_Establecimiento.setVisibility(View.VISIBLE);
            Establecimientos_Populares.clear();
            Adaptador = new EstablecimientoInicio_Adaptador(Establecimientos_Populares, getActivity());
            Layout_Manager = new GridLayoutManager(getActivity(), 2);
            Recycler_View.setLayoutManager(Layout_Manager);
            Recycler_View.setAdapter(Adaptador);
        }

    }

    @Override
    public void onSearchEstablishmentByCategoryFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFourBestEstablishmentSuccessful(final ArrayList<Establecimiento_Modelo> Establecimientos) {

        Establecimientos_Populares = Establecimientos;
        Adaptador = new EstablecimientoInicio_Adaptador(Establecimientos, getActivity());
        Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.SaveEstablishmentInfo(getActivity(),Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getID_Establecimiento(),
                        Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getNombre());

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, opcionesEstablecimiento_vista).addToBackStack(null).commit();
            }
        });
        Layout_Manager = new GridLayoutManager(getActivity(), 2);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);
    }

    @Override
    public void onListItemMenuSuccessful(ArrayList<ItemMenu_Modelo> Items_Menu, Boolean Existe_Item_Menu) {
        if(Existe_Item_Menu)
        {
            mPresenter.GetItemsMenuByCategory(Establecimientos, Items_Menu);
        }
        else
        {

        }
    }

    @Override
    public void onListItemMenuFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetItemsMenuByCategorySuccessful(final ArrayList<ItemMenu_Modelo> Items_Menu) {

        Adaptador_Item_Menu = new ItemMenuInicio_Adaptador(Items_Menu, getActivity());
        Adaptador_Item_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.GetEstablishmentName(Establecimientos, Items_Menu.get(Recycler_View_Item_Menu.getChildAdapterPosition(v)).getID_Establecimiento());
            }
        });
        Layout_Manager_Item_Menu = new GridLayoutManager(getActivity(), 3);
        Recycler_View_Item_Menu.setLayoutManager(Layout_Manager_Item_Menu);
        Recycler_View_Item_Menu.setAdapter(Adaptador_Item_Menu);
    }

    @Override
    public void onGetEstablishmentNameSuccessful(String ID_Establecimiento, String Nombre_Establecimiento) {

        mPresenter.SaveEstablishmentInfo(getActivity(), ID_Establecimiento, Nombre_Establecimiento);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, opcionesEstablecimiento_vista).addToBackStack(null).commit();
    }
}
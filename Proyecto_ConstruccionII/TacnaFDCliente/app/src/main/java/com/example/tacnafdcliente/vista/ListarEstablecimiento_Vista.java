package com.example.tacnafdcliente.vista;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.Establecimiento_Adaptador;
import com.example.tacnafdcliente.interfaces.ListarEstablecimiento;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.presentador.ListarEstablecimiento_Presentador;
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

    EditText TxtBuscar;

    Button BtnBuscar;

    Spinner Spinner_Categoria;
    Spinner Spinner_Distrito;

    ConstraintLayout Opciones_Busqueda;

    TextView LblNo_Establecimiento;

    String[] Categorias = {"Seleccione una Categoria", "Restaurante", "Cafeteria", "Panaderia", "Cafeteria"};
    String[] Distritos = {"Seleccione un Distrito", "Tacna", "Alto del Alianza", "Calana", "Pachia", "Palca", "Pocollay", "Ciudad Nueva"};

    ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();
    ArrayList<Establecimiento_Modelo> Filtrar_Establecimientos = new ArrayList<>();

    Boolean Existe_Establecimiento = false;
    Boolean Buscar_Establecimiento = false;

    OpcionesEstablecimiento_Vista opcionesEstablecimiento_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_establecimiento_vista, container, false);

        mPresenter=new ListarEstablecimiento_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_ListaEstablecimiento);
        TxtBuscar = (EditText) view.findViewById(R.id.TxtBuscar);
        Spinner_Categoria = (Spinner) view.findViewById(R.id.spinnercategoria);
        Spinner_Distrito = (Spinner) view.findViewById(R.id.spinnerdistrito);
        Opciones_Busqueda = (ConstraintLayout) view.findViewById(R.id.Opciones_Busqueda);
        BtnBuscar = (Button) view.findViewById(R.id.BtnBuscar);
        LblNo_Establecimiento = (TextView) view.findViewById(R.id.LblNo_Establecimiento);

        Spinner_Categoria.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categorias));
        Spinner_Distrito.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Distritos));

        mPresenter.GetAllEstablishment(mReference);

        opcionesEstablecimiento_vista = new OpcionesEstablecimiento_Vista();


        TxtBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opciones_Busqueda.setVisibility(View.VISIBLE);
            }
        });


        BtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opciones_Busqueda.setVisibility(View.GONE);

                /*ocultar teclado*/
                
                InputMethodManager Input_Method_Manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                Input_Method_Manager.hideSoftInputFromWindow(BtnBuscar.getWindowToken(), 0);

                mPresenter.FilterEstablishment(Establecimientos, TxtBuscar.getText().toString(), Spinner_Categoria.getSelectedItem().toString(),
                        Spinner_Distrito.getSelectedItem().toString());
            }
        });

        return view;
    }

    @Override
    public void onGetAllEstablishmentSuccessful(final ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento) {
        this.Establecimientos=Establecimientos;
        Adaptador = new Establecimiento_Adaptador(Establecimientos, getActivity());
        Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Buscar_Establecimiento)
                {
                    mPresenter.SaveEstablishmentInfo(getActivity(),Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getID_Establecimiento(),
                            Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getNombre());
                }
                else
                {
                    mPresenter.SaveEstablishmentInfo(getActivity(),Filtrar_Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getID_Establecimiento(),
                            Filtrar_Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getNombre());
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, opcionesEstablecimiento_vista).addToBackStack(null).commit();
                TxtBuscar.setText("");
            }
        });
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

        if(Existe_Establecimiento)
        {
            LblNo_Establecimiento.setVisibility(View.GONE);
            this.Existe_Establecimiento = true;
        }
        else
        {
            LblNo_Establecimiento.setVisibility(View.VISIBLE);
            this.Existe_Establecimiento = false;
        }
    }

    @Override
    public void onGetAllEstablishmentFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFilterSuccessful(ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Buscar_Establecimiento) {
        this.Filtrar_Establecimientos = Establecimientos;
        Adaptador.filterlist(Filtrar_Establecimientos);
        this.Buscar_Establecimiento = Buscar_Establecimiento;

        if(Establecimientos.size() == 0)
        {
            LblNo_Establecimiento.setVisibility(View.VISIBLE);
            this.Existe_Establecimiento = false;
        }
        else
        {
            LblNo_Establecimiento.setVisibility(View.GONE);
            this.Existe_Establecimiento = true;
        }
    }
}
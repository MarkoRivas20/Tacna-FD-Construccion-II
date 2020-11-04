package com.example.tacnafdbusiness.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Establecimiento_Adaptador;
import com.example.tacnafdbusiness.interfaces.ListarEstablecimiento;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.presentador.ListarEstablecimiento_Presentador;
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

    RegistrarEstablecimiento_Vista registrarEstablecimiento_vista;
    PantallaPrincipal_Vista pantallaPrincipal_vista;
    OpcionesEstablecimiento_Vista opcionesEstablecimiento_vista;

    Button BtnRegistro_Establecimiento;

    String ID_Usuario = "";

    TextView LblNo_Establecimiento;

    EditText TxtBuscar;

    ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();
    ArrayList<Establecimiento_Modelo> Filtrar_Establecimientos = new ArrayList<>();

    Boolean Buscar_Establecimiento;
    Boolean Existe_Establecimiento = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_establecimiento__vista, container, false);

        Buscar_Establecimiento = false;

        pantallaPrincipal_vista=new PantallaPrincipal_Vista();
        registrarEstablecimiento_vista = new RegistrarEstablecimiento_Vista();
        opcionesEstablecimiento_vista = new OpcionesEstablecimiento_Vista();

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_ListaEstablecimiento);
        BtnRegistro_Establecimiento = (Button) view.findViewById(R.id.BtnRegistro_Establecimiento);
        LblNo_Establecimiento = (TextView) view.findViewById(R.id.LblNo_Establecimiento);
        TxtBuscar = (EditText) view.findViewById(R.id.TxtBuscar);

        mPresenter=new ListarEstablecimiento_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        mPresenter.GetSessionData(getActivity().getApplicationContext());

        mPresenter.SearchEstablishment(mReference,ID_Usuario);

        BtnRegistro_Establecimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TxtBuscar.setText("");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, registrarEstablecimiento_vista).addToBackStack(null).commit();
            }
        });

        TxtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Existe_Establecimiento)
                {
                    mPresenter.FilterEstablishment(Establecimientos,s.toString());
                }

            }
        });

        return view;
    }

    @Override
    public void onSearchEstablishmentSuccessful(final ArrayList<Establecimiento_Modelo> Establecimientos, Boolean Existe_Establecimiento) {

        this.Establecimientos=Establecimientos;
        Adaptador = new Establecimiento_Adaptador(Establecimientos, getActivity());
        Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Buscar_Establecimiento)
                {
                    mPresenter.SaveEstablishmentInfo(getActivity(),Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getID_Establecimiento(),
                            Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getNombre(), Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getUrl_Imagen_Logo(),
                            Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getUrl_Imagen_Documento());
                }
                else
                {
                    mPresenter.SaveEstablishmentInfo(getActivity(),Filtrar_Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getID_Establecimiento(),
                            Filtrar_Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getNombre(), Filtrar_Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getUrl_Imagen_Logo(),
                            Filtrar_Establecimientos.get(Recycler_View.getChildAdapterPosition(v)).getUrl_Imagen_Documento());
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
    public void onSearchEstablishmentFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo paso...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSessionDataSuccessful(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    @Override
    public void onFilterSuccessful(ArrayList<Establecimiento_Modelo> Filtrar_Establecimientos, Boolean Buscar_Establecimiento) {
        this.Filtrar_Establecimientos = Filtrar_Establecimientos;
        Adaptador.filterlist(Filtrar_Establecimientos);
        this.Buscar_Establecimiento = Buscar_Establecimiento;
    }


}
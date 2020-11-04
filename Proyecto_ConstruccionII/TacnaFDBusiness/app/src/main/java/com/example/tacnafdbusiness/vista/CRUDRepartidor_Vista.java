package com.example.tacnafdbusiness.vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Imagen_Adaptador;
import com.example.tacnafdbusiness.adaptador.Repartidor_Adaptador;
import com.example.tacnafdbusiness.interfaces.CRUDRepartidores;
import com.example.tacnafdbusiness.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.example.tacnafdbusiness.presentador.CRUDImagenes_Presentador;
import com.example.tacnafdbusiness.presentador.CRUDRepartidor_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class CRUDRepartidor_Vista extends Fragment implements CRUDRepartidores.View {


    public CRUDRepartidor_Vista() {
        // Required empty public constructor
    }

    public CRUDRepartidor_Presentador mPresenter;
    public DatabaseReference mReference;

    private RecyclerView Recycler_View;
    private Repartidor_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    String ID_Establecimiento = "";
    String ID_Repartidor_Establecimiento = "";
    String ID_Usuario_Repartidor = "";

    EditText TxtCorreo_Electronico_Repartidor;

    TextView LblNo_Repartidores;

    Button BtnBuscar_Repartidor;

    AlertDialog.Builder Mensaje;

    ArrayList <Repartidor_Modelo> Repartidores = new ArrayList<>();

    Boolean aBoolean = false;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crud_repartidor_vista, container, false);


        TxtCorreo_Electronico_Repartidor = (EditText) view.findViewById(R.id.TxtCorreo_Electronico_Repartidor);
        BtnBuscar_Repartidor = (Button) view.findViewById(R.id.BtnBuscar_Repartidor);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_RepartidoresEstablecimiento);
        LblNo_Repartidores = (TextView) view.findViewById(R.id.LblNo_Repartidores);

        mPresenter =new CRUDRepartidor_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Repartidor_Establecimiento");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.ListDeliveryMen(mReference, ID_Establecimiento);

        BtnBuscar_Repartidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aBoolean = false;
                if(Repartidores.size() == 0)
                {
                    mReference = FirebaseDatabase.getInstance().getReference().child("Usuario_Repartidor");
                    mPresenter.SearchDeliveryMan(mReference, TxtCorreo_Electronico_Repartidor.getText().toString());
                }
                else
                {
                    for(int i = 0; i < Repartidores.size(); i++)
                    {
                        if(TxtCorreo_Electronico_Repartidor.getText().toString().equals(Repartidores.get(i).getEmail()))
                        {
                            aBoolean = true;
                        }
                    }

                    if(!aBoolean)
                    {
                        mReference = FirebaseDatabase.getInstance().getReference().child("Usuario_Repartidor");
                        mPresenter.SearchDeliveryMan(mReference, TxtCorreo_Electronico_Repartidor.getText().toString());
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Ya se encuentra agregado", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        Mensaje = new AlertDialog.Builder(getActivity());
        Mensaje.setTitle("Agregar Repartidor");
        Mensaje.setCancelable(false);
        Mensaje.setPositiveButton("Si, estoy seguro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mReference = FirebaseDatabase.getInstance().getReference().child("Repartidor_Establecimiento");
                ID_Repartidor_Establecimiento = mReference.push().getKey();

                RepartidorEstablecimiento_Modelo Repartidor_Establecimiento = new RepartidorEstablecimiento_Modelo(ID_Repartidor_Establecimiento, ID_Usuario_Repartidor, ID_Establecimiento);
                mPresenter.SaveDeliveryMan(mReference, Repartidor_Establecimiento);

            }
        });
        Mensaje.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        return view;
    }

    @Override
    public void onSaveDeliveryManSuccessful() {
        TxtCorreo_Electronico_Repartidor.setText("");
        Toast.makeText(getActivity(),"Repartidor Agregado Satisfactoriamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveDeliveryManFailure() {
        Toast.makeText(getActivity(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchDeliveryManSuccessful(Repartidor_Modelo Repartidor, Boolean Existe_Repartidor) {
        if(Existe_Repartidor)
        {
            ID_Usuario_Repartidor = Repartidor.getID_Usuario_Repartidor();
            Mensaje.setMessage("¿Esta seguro que desea añadir a " + Repartidor.getNombre() + " " + Repartidor.getApellido() + "?");
            Mensaje.show();
        }
        else
        {
            Toast.makeText(getActivity(),"Correo electronico invalido", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSearchDeliveryManFailure() {
        Toast.makeText(getActivity(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTakeOutDeliveryManSuccessful() {
        Toast.makeText(getActivity(),"Repartidor Quitado Satisfactoriamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTakeOutDeliveryManFailure() {
        Toast.makeText(getActivity(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListDeliveryMenSuccessful(final ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento, Boolean Existe_Repartidor_Establecimiento) {

        if(Existe_Repartidor_Establecimiento)
        {
            LblNo_Repartidores.setVisibility(View.GONE);
            mReference = FirebaseDatabase.getInstance().getReference().child("Usuario_Repartidor");
            mPresenter.SearchDeliveryManInfo(mReference, Repartidores_Establecimiento);
        }
        else
        {
            LblNo_Repartidores.setVisibility(View.VISIBLE);
            Repartidores.clear();
            Adaptador = new Repartidor_Adaptador(Repartidores, getActivity());
            Layout_Manager = new GridLayoutManager(getActivity(), 2);
            Recycler_View.setLayoutManager(Layout_Manager);
            Recycler_View.setAdapter(Adaptador);
        }

    }

    @Override
    public void onListDeliveryMenFailure() {
        Toast.makeText(getActivity(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchDeliveryManInfoSuccessful(final ArrayList<Repartidor_Modelo> Repartidores) {

        this.Repartidores = Repartidores;
        Adaptador = new Repartidor_Adaptador(Repartidores, getActivity());
        Adaptador.setOnItemClickListener(new Repartidor_Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),"Mantenga Presionado...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTakeOut(int position) {
                mReference = FirebaseDatabase.getInstance().getReference().child("Repartidor_Establecimiento");
                mPresenter.TakeOutDeliveryMan(mReference, Repartidores.get(position).getID_Usuario_Repartidor(), ID_Establecimiento);
            }

            @Override
            public void onCancel(int position) {

            }
        });
        Layout_Manager = new GridLayoutManager(getActivity(), 2);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);
    }

    @Override
    public void onSearchDeliveryManInfoFailure() {
        Toast.makeText(getActivity(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento=ID_Establecimiento;
    }
}
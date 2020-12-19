package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.CuponUsuario_Adaptador;
import com.example.tacnafdcliente.adaptador.Pedido_Adaptador;
import com.example.tacnafdcliente.interfaces.ListarMiCupon;
import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.presentador.ListarMiCupon_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marcinmoskala.arcseekbar.ArcSeekBar;

import java.util.ArrayList;


public class ListarMiCupon_Vista extends Fragment implements ListarMiCupon.View {

    public ListarMiCupon_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private CuponUsuario_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public ListarMiCupon_Presentador mPresenter;
    public DatabaseReference mReference_Cupon_Usuario;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Cupon;

    ArcSeekBar SeekBar;

    TextView TxtContador;
    TextView LblNo_Cupon_Usuario;

    String ID_Usuario;

    ArrayList <CuponUsuario_Modelo> Cupones_Usuario = new ArrayList<>();

    Bundle MiCupon_Info = new Bundle();

    UtilizarCupon_Vista utilizarCupon_vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_mi_cupon__vista, container, false);

        SeekBar = (ArcSeekBar) view.findViewById(R.id.Seekbar);
        TxtContador = (TextView) view.findViewById(R.id.TxtContador);
        LblNo_Cupon_Usuario = (TextView) view.findViewById(R.id.LblNo_Cupon_Usuario);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Mis_Cupones);

        utilizarCupon_vista = new UtilizarCupon_Vista();

        mPresenter=new ListarMiCupon_Presentador(this);
        mReference_Cupon_Usuario = FirebaseDatabase.getInstance().getReference().child("Cupon_Usuario");
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Cupon = FirebaseDatabase.getInstance().getReference().child("Cupon");

        mPresenter.GetNumberOfCoupons(getActivity());
        mPresenter.GetSessionData(getActivity());
        mPresenter.GetCouponUser(mReference_Cupon_Usuario, ID_Usuario);

        return view;
    }

    @Override
    public void onGetNumberOfCouponsSuccessful(int Numero_Cupones) {
        SeekBar.setProgress(Numero_Cupones);
        TxtContador.setText(String.valueOf(Numero_Cupones));
    }

    @Override
    public void onGetCouponUserSuccessful(ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {

        if(Cupones_Usuario.size() != 0)
        {
            LblNo_Cupon_Usuario.setVisibility(View.GONE);
            mPresenter.SearchCouponInfo(mReference_Cupon, Cupones_Usuario);
        }
        else
        {
            LblNo_Cupon_Usuario.setVisibility(View.VISIBLE);
            Adaptador = new CuponUsuario_Adaptador(Cupones_Usuario, getActivity());
            Layout_Manager = new GridLayoutManager(getActivity(), 2);
            Recycler_View.setLayoutManager(Layout_Manager);
            Recycler_View.setAdapter(Adaptador);
        }
    }

    @Override
    public void onGetCouponUserFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchEstablishmentNameSuccessful(final ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {

        Adaptador = new CuponUsuario_Adaptador(Cupones_Usuario, getActivity());
        Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiCupon_Info.putString("nombre_establecimiento",Cupones_Usuario.get(Recycler_View.getChildAdapterPosition(v)).getNombre_Establecimiento());
                MiCupon_Info.putString("id_cupon",Cupones_Usuario.get(Recycler_View.getChildAdapterPosition(v)).getID_Cupon());
                MiCupon_Info.putString("fecha_obtencion",Cupones_Usuario.get(Recycler_View.getChildAdapterPosition(v)).getFecha());
                MiCupon_Info.putString("id_cupon_usuario",Cupones_Usuario.get(Recycler_View.getChildAdapterPosition(v)).getID_Cupon_Usuario());

                utilizarCupon_vista.setArguments(MiCupon_Info);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, utilizarCupon_vista).addToBackStack(null).commit();
            }
        });
        Layout_Manager = new GridLayoutManager(getActivity(), 2);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);
    }

    @Override
    public void onSearchEstablishmentNameFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchCouponInfoSuccessful(ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {
        mPresenter.SearchEstablishmentName(mReference_Establecimiento, Cupones_Usuario);
    }

    @Override
    public void onSearchCouponInfoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSessionDataSuccessful(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }
}
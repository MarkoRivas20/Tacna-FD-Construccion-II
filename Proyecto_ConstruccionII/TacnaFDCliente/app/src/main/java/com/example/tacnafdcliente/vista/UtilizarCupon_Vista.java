package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.UtilizarCupon;
import com.example.tacnafdcliente.modelo.Cupon_Modelo;
import com.example.tacnafdcliente.presentador.UtilizarCupon_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class UtilizarCupon_Vista extends Fragment implements UtilizarCupon.View {

    public UtilizarCupon_Vista() {
        // Required empty public constructor
    }

    public UtilizarCupon_Presentador mPresenter;
    public DatabaseReference mReference;

    ImageView ImgCupon;
    TextView TxtDescripcion;
    TextView TxtFecha_Final;
    TextView TxtFecha_Inicio;
    TextView TxtPorcentaje_Descuento;
    TextView TxtNombre_Establecimiento;
    TextView TxtFecha_Obtenido;
    Button BtnUtilizar_Cupon;

    String ID_Cupon = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_utilizar_cupon__vista, container, false);

        mPresenter = new UtilizarCupon_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Cupon");

        ImgCupon = (ImageView) view.findViewById(R.id.ImgCupon);
        TxtDescripcion = (TextView) view.findViewById(R.id.TxtDescripcion);
        TxtFecha_Final = (TextView) view.findViewById(R.id.TxtFecha_Final);
        TxtFecha_Inicio = (TextView) view.findViewById(R.id.TxtFecha_Inicio);
        TxtPorcentaje_Descuento = (TextView) view.findViewById(R.id.TxtPorcentaje_Descuento);
        TxtNombre_Establecimiento = (TextView) view.findViewById(R.id.TxtNombre_Establecimiento);
        TxtFecha_Obtenido = (TextView) view.findViewById(R.id.TxtFecha_Obtenido);
        BtnUtilizar_Cupon = (Button) view.findViewById(R.id.BtnUtilizar_Cupon);

        Bundle MiCupon_Info = getArguments();

        TxtFecha_Obtenido.setText(MiCupon_Info.getString("fecha_obtencion"));
        TxtNombre_Establecimiento.setText(MiCupon_Info.getString("nombre_establecimiento"));
        ID_Cupon = MiCupon_Info.getString("id_cupon");

        mPresenter.GetCouponInfo(mReference, ID_Cupon);

        return view;
    }

    @Override
    public void onGetCouponInfoSuccessful(Cupon_Modelo Cupon) {
        Picasso.get().load(Cupon.getUrl_Imagen()).into(ImgCupon);
        TxtDescripcion.setText(Cupon.getDescripcion());
        TxtFecha_Final.setText(Cupon.getFecha_Fin());
        TxtFecha_Inicio.setText(Cupon.getFecha_Inicio());
        TxtPorcentaje_Descuento.setText(Cupon.getPorcentaje_Descuento() + " %");
    }

    @Override
    public void onGetCouponInfoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }
}
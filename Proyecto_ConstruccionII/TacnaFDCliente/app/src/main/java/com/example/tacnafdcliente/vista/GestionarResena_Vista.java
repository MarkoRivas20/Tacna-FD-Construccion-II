package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.Resena_Adaptador;
import com.example.tacnafdcliente.interfaces.GestionarResena;
import com.example.tacnafdcliente.modelo.CuponUsuario_Modelo;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.example.tacnafdcliente.presentador.GestionarResena_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GestionarResena_Vista extends Fragment implements GestionarResena.View {


    public GestionarResena_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private Resena_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public GestionarResena_Presentador mPresenter;
    public DatabaseReference mReference_Resena;
    public DatabaseReference mReference_Cliente;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Cupon_Usuario;

    String ID_Establecimiento = "";
    String ID_Usuario = "";
    String ID_Resena = "";
    String Patron_Fecha = "dd/MM/yyyy";
    String Fecha_Actual = "";

    TextView LblPuntuacion;
    TextView LblTotal;
    TextView LblNo_Resenas;

    EditText TxtComentario_Resena;

    Button BtnModificar_Resena;
    Button BtnEliminar_Resena;

    ProgressBar ProgressBar_Numero_Uno;
    ProgressBar ProgressBar_Numero_Dos;
    ProgressBar ProgressBar_Numero_Tres;
    ProgressBar ProgressBar_Numero_Cuatro;
    ProgressBar ProgressBar_Numero_Cinco;
    RatingBar RatingBar_Lista_Resenas;
    RatingBar Ratingbar_Calificacion;

    int Contador_Numero_Uno = 0;
    int Contador_Numero_Dos = 0;
    int Contador_Numero_Tres = 0;
    int Contador_Numero_Cuatro = 0;
    int Contador_Numero_Cinco = 0;
    int Total_Resenas = 0;
    int Numero_Cupones = 0;

    Double Puntuacion_Establecimiento = 0.0;
    Double total_puntuacion = 0.0;
    Double Puntuacion_Resena = 0.0;

    Float Calificacion = (float) 0.0;

    ArrayList<Resena_Modelo> Resenas = new ArrayList<>();

    SimpleDateFormat simpleDateFormat;

    Boolean aBoolean = false;

    RegistrarResena_Vista registrarResena_vista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gestionar_resena__vista, container, false);

        simpleDateFormat = new SimpleDateFormat(Patron_Fecha);
        Fecha_Actual = simpleDateFormat.format(new Date());

        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_Resenas);
        LblPuntuacion = (TextView) view.findViewById(R.id.LblPuntuacion);
        LblTotal = (TextView) view.findViewById(R.id.LblTotal);
        LblNo_Resenas = (TextView) view.findViewById(R.id.LblNo_Resenas);
        ProgressBar_Numero_Uno = (ProgressBar) view.findViewById(R.id.progressbar_numero_uno);
        ProgressBar_Numero_Dos = (ProgressBar) view.findViewById(R.id.progressbar_numero_dos);
        ProgressBar_Numero_Tres = (ProgressBar) view.findViewById(R.id.progressbar_numero_tres);
        ProgressBar_Numero_Cuatro = (ProgressBar) view.findViewById(R.id.progressbar_numero_cuatro);
        ProgressBar_Numero_Cinco = (ProgressBar) view.findViewById(R.id.progressbar_numero_cinco);
        RatingBar_Lista_Resenas = (RatingBar) view.findViewById(R.id.ratingbarlistaresenas);
        Ratingbar_Calificacion = (RatingBar) view.findViewById(R.id.Ratingbar_Calificacion);
        TxtComentario_Resena = (EditText) view.findViewById(R.id.TxtComentario_Resena);
        BtnModificar_Resena = (Button) view.findViewById(R.id.BtnModificar_Resena);
        BtnEliminar_Resena = (Button) view.findViewById(R.id.BtnEliminar_Resena);

        registrarResena_vista = new RegistrarResena_Vista();

        mPresenter = new GestionarResena_Presentador(this);
        mReference_Resena = FirebaseDatabase.getInstance().getReference().child("Resena");
        mReference_Cliente = FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Cupon_Usuario = FirebaseDatabase.getInstance().getReference().child("Cupon_Usuario");

        mPresenter.GetNumberOfCoupons(getActivity());
        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetSessionData(getActivity());
        mPresenter.GetUserReview(mReference_Resena, ID_Establecimiento, ID_Usuario);
        mPresenter.GetReviews(mReference_Resena,ID_Establecimiento, ID_Usuario);

        BtnModificar_Resena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TxtComentario_Resena.getText().toString().equals(""))
                {
                    Puntuacion_Resena = (double) Ratingbar_Calificacion.getRating();
                    Resena_Modelo Resena = new Resena_Modelo(ID_Resena, ID_Usuario, ID_Establecimiento, TxtComentario_Resena.getText().toString(), Puntuacion_Resena, Fecha_Actual);
                    mPresenter.UpdateReview(mReference_Resena, Resena);
                }
                else
                {
                    Toast.makeText(getActivity(),"Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        BtnEliminar_Resena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.DeleteReview(mReference_Resena, ID_Resena);
            }
        });


        return view;
    }


    @Override
    public void onGetReviewsSuccessful(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena) {
        this.Resenas = Resenas;

        if(Existe_Resena)
        {
            LblNo_Resenas.setVisibility(View.GONE);
            mPresenter.SearchClientName(mReference_Cliente,Resenas);
        }
        else
        {
            LblNo_Resenas.setVisibility(View.VISIBLE);
            Resenas.clear();
            Adaptador = new Resena_Adaptador(Resenas, getActivity());
            Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            Recycler_View.setLayoutManager(Layout_Manager);
            Recycler_View.setAdapter(Adaptador);
            LblPuntuacion.setText("0");
            RatingBar_Lista_Resenas.setRating(0);
            LblTotal.setText("0");
            ProgressBar_Numero_Uno.setProgress(0);
            ProgressBar_Numero_Dos.setProgress(0);
            ProgressBar_Numero_Tres.setProgress(0);
            ProgressBar_Numero_Cuatro.setProgress(0);
            ProgressBar_Numero_Cinco.setProgress(0);
        }
    }

    @Override
    public void onGetReviewsFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchClientNameSuccessful(ArrayList<Resena_Modelo> Resenas) {

        total_puntuacion = 0.0;
        Contador_Numero_Uno = 0;
        Contador_Numero_Dos = 0;
        Contador_Numero_Tres = 0;
        Contador_Numero_Cuatro = 0;
        Contador_Numero_Cinco = 0;

        for(int i = 0; i < Resenas.size(); i++)
        {

            if (Resenas.get(i).getCalificacion() <= 1.0)
            {
                Contador_Numero_Uno++;
            }
            else if (Resenas.get(i).getCalificacion() <= 2.0)
            {
                Contador_Numero_Dos++;
            }
            else if (Resenas.get(i).getCalificacion() <= 3.0)
            {
                Contador_Numero_Tres++;
            }
            else if (Resenas.get(i).getCalificacion() <= 4.0)
            {
                Contador_Numero_Cuatro++;
            }
            else if (Resenas.get(i).getCalificacion() <= 5.0)
            {
                Contador_Numero_Cinco++;
            }
            total_puntuacion = total_puntuacion + Resenas.get(i).getCalificacion();
        }

        ProgressBar_Numero_Uno.setMax(Resenas.size());
        ProgressBar_Numero_Dos.setMax(Resenas.size());
        ProgressBar_Numero_Tres.setMax(Resenas.size());
        ProgressBar_Numero_Cuatro.setMax(Resenas.size());
        ProgressBar_Numero_Cinco.setMax(Resenas.size());

        ProgressBar_Numero_Uno.setProgress(Contador_Numero_Uno);
        ProgressBar_Numero_Dos.setProgress(Contador_Numero_Dos);
        ProgressBar_Numero_Tres.setProgress(Contador_Numero_Tres);
        ProgressBar_Numero_Cuatro.setProgress(Contador_Numero_Cuatro);
        ProgressBar_Numero_Cinco.setProgress(Contador_Numero_Cinco);

        LblTotal.setText(String.valueOf(Resenas.size()));
        total_puntuacion = total_puntuacion/Resenas.size();
        LblPuntuacion.setText(String.valueOf(total_puntuacion));
        RatingBar_Lista_Resenas.setRating(total_puntuacion.floatValue());

        //Total_Resenas = Resenas.size();

/*
        for(int i = 0; i<Total_Resenas; i++){
            if(ID_Usuario.equals(Resenas.get(i).getID_Usuario_Cliente())){
                Resenas.remove(i);
                break;
            }
        }*/

        Adaptador = new Resena_Adaptador(Resenas, getActivity());
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

    }

    @Override
    public void onSearchClientNameFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUserReviewSuccessful(Resena_Modelo Resena) {
        ID_Resena = Resena.getID_Resena();
        TxtComentario_Resena.setText(Resena.getDescripcion());
        Calificacion = Resena.getCalificacion().floatValue();
        Ratingbar_Calificacion.setRating(Calificacion);

    }

    @Override
    public void onGetUserReviewFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateReviewSuccessful() {
        aBoolean = true;
        mPresenter.GetCurrentReviews(mReference_Resena, ID_Establecimiento);
    }

    @Override
    public void onUpdateReviewFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteReviewSuccessful() {
        aBoolean = false;
        mPresenter.GetCurrentReviews(mReference_Resena, ID_Establecimiento);
    }

    @Override
    public void onDeleteReviewFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateEstablishmentSuccessful() {

        if(aBoolean)
        {
            Toast.makeText(getActivity(),"Reseña modificada satisfactoriamente", Toast.LENGTH_SHORT).show();
            mPresenter.GetReviews(mReference_Resena,ID_Establecimiento, ID_Usuario);
        }
        else
        {
            if(Numero_Cupones == 0)
            {
                mPresenter.GetCouponUserFromUser(mReference_Cupon_Usuario, ID_Usuario);
            }
            else
            {
                Numero_Cupones--;
                mPresenter.SaveNumberOfCoupons(getActivity(), Numero_Cupones);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, registrarResena_vista).addToBackStack(null).commit();
            }

        }

    }

    @Override
    public void onUpdateEstablishmentFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCurrentReviewsSuccessful(ArrayList<Resena_Modelo> Resenas) {
        Puntuacion_Establecimiento = 0.0;
        for(int i=0; i<Resenas.size(); i++)
        {
            Puntuacion_Establecimiento = Puntuacion_Establecimiento + Resenas.get(i).getCalificacion();
        }

        if(Resenas.size() != 0)
        {
            Puntuacion_Establecimiento = Puntuacion_Establecimiento/Resenas.size();
        }
        else
        {
            Puntuacion_Establecimiento = 0.0;
        }

        mPresenter.UpdateEstablishment(mReference_Establecimiento, ID_Establecimiento, Puntuacion_Establecimiento, Resenas.size());
    }

    @Override
    public void onGetCurrentReviewsFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

    @Override
    public void onGetSessionDataSuccessful(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    @Override
    public void onGetNumberOfCouponsSuccessful(int Numero_Cupones) {
        this.Numero_Cupones = Numero_Cupones;
    }

    @Override
    public void onGetCouponUserFromUserSuccessful(ArrayList<CuponUsuario_Modelo> Cupones_Usuario) {

        if(Cupones_Usuario.size() == 0)
        {
            Numero_Cupones--;
            mPresenter.SaveNumberOfCoupons(getActivity(), Numero_Cupones);
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, registrarResena_vista).addToBackStack(null).commit();
        }
        else
        {
            Numero_Cupones = 4;
            mPresenter.SaveNumberOfCoupons(getActivity(), Numero_Cupones);
            mPresenter.DeleteCouponUser(mReference_Cupon_Usuario, Cupones_Usuario.get(Cupones_Usuario.size()-1).getID_Cupon_Usuario());
        }
    }

    @Override
    public void onGetCouponUserFromUserFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCouponUserSuccessful() {
        Toast.makeText(getActivity(),"Usted ha perdido un cupon", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, registrarResena_vista).addToBackStack(null).commit();
    }

    @Override
    public void onDeleteCouponUserFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }
}
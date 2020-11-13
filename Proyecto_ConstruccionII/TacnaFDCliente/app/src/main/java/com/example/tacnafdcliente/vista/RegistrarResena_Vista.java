package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.tacnafdcliente.interfaces.RegistrarResena;
import com.example.tacnafdcliente.modelo.Resena_Modelo;
import com.example.tacnafdcliente.presentador.RegistrarResena_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RegistrarResena_Vista extends Fragment implements RegistrarResena.View {


    public RegistrarResena_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private Resena_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public RegistrarResena_Presentador mPresenter;
    public DatabaseReference mReference_Resena;
    public DatabaseReference mReference_Cliente;
    public DatabaseReference mReference_Establecimiento;

    String ID_Establecimiento = "";
    String ID_Resena = "";
    String ID_Usuario = "";
    String Patron_Fecha = "dd/MM/yyyy";
    String Fecha_Actual = "";

    TextView LblPuntuacion;
    TextView LblTotal;
    TextView LblNo_Resenas;

    EditText TxtComentario;

    Button BtnPublicar;

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

    Double total_puntuacion = 0.0;
    Double Puntuacion_Resena = 0.0;
    Double Puntuacion_Establecimiento = 0.0;

    ArrayList <Resena_Modelo> Resenas = new ArrayList<>();

    SimpleDateFormat simpleDateFormat;

    GestionarResena_Vista gestionarResena_vista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_resena__vista, container, false);

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
        BtnPublicar = (Button) view.findViewById(R.id.btnpublicar);
        TxtComentario = (EditText) view.findViewById(R.id.txtcomentario);
        Ratingbar_Calificacion = (RatingBar) view.findViewById(R.id.Ratingbar_Calificacion);

        gestionarResena_vista = new GestionarResena_Vista();

        mPresenter=new RegistrarResena_Presentador(this);
        mReference_Resena= FirebaseDatabase.getInstance().getReference().child("Resena");
        mReference_Cliente= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");
        mReference_Establecimiento= FirebaseDatabase.getInstance().getReference().child("Establecimiento");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetSessionData(getActivity());
        mPresenter.GetReviews(mReference_Resena,ID_Establecimiento);

        BtnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ID_Resena = mReference_Resena.push().getKey();
                 Puntuacion_Resena = (double) Ratingbar_Calificacion.getRating();
                 Resena_Modelo Resena = new Resena_Modelo(ID_Resena, ID_Usuario, ID_Establecimiento, TxtComentario.getText().toString(), Puntuacion_Resena, Fecha_Actual);
                 mPresenter.SaveReview(mReference_Resena, Resena);
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
        Adaptador = new Resena_Adaptador(Resenas, getActivity());
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

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
    }

    @Override
    public void onSearchClientNameFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveReviewSuccessful() {
        mPresenter.GetCurrentReviews(mReference_Resena, ID_Establecimiento);

    }

    @Override
    public void onSaveReviewFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateEstablishmentSuccessful() {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, gestionarResena_vista).addToBackStack(null).commit();
    }

    @Override
    public void onUpdateEstablishmentFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCurrentReviewsSuccessful(ArrayList<Resena_Modelo> Resenas) {

        for(int i=0; i<Resenas.size(); i++)
        {
            Puntuacion_Establecimiento = Puntuacion_Establecimiento + Resenas.get(i).getCalificacion();
        }
        Puntuacion_Establecimiento = Puntuacion_Establecimiento/Resenas.size();

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
}
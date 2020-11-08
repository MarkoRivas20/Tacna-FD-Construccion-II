package com.example.tacnafdbusiness.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Establecimiento_Adaptador;
import com.example.tacnafdbusiness.adaptador.Repartidor_Adaptador;
import com.example.tacnafdbusiness.adaptador.Resena_Adaptador;
import com.example.tacnafdbusiness.interfaces.ListarResena;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.example.tacnafdbusiness.presentador.ListarEstablecimiento_Presentador;
import com.example.tacnafdbusiness.presentador.ListarResena_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ListarResena_Vista extends Fragment implements ListarResena.View {

    public ListarResena_Vista() {
        // Required empty public constructor
    }

    private RecyclerView Recycler_View;
    private Resena_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public ListarResena_Presentador mPresenter;
    public DatabaseReference mReference;

    String ID_Establecimiento = "";

    TextView LblPuntuacion;
    TextView LblTotal;
    TextView LblNo_Resenas;

    ProgressBar ProgressBar_Numero_Uno;
    ProgressBar ProgressBar_Numero_Dos;
    ProgressBar ProgressBar_Numero_Tres;
    ProgressBar ProgressBar_Numero_Cuatro;
    ProgressBar ProgressBar_Numero_Cinco;
    RatingBar RatingBar_Lista_Resenas;

    int Contador_Numero_Uno = 0;
    int Contador_Numero_Dos = 0;
    int Contador_Numero_Tres = 0;
    int Contador_Numero_Cuatro = 0;
    int Contador_Numero_Cinco = 0;

    Double total_puntuacion = 0.0;

    ArrayList <Resena_Modelo> Resenas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_resena__vista, container, false);

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

        mPresenter=new ListarResena_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Resena");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetReviews(mReference,ID_Establecimiento);

        return view;
    }

    @Override
    public void onGetReviewsSuccessful(ArrayList<Resena_Modelo> Resenas, Boolean Existe_Resena) {
        this.Resenas = Resenas;

        if(Existe_Resena)
        {
            LblNo_Resenas.setVisibility(View.GONE);
            mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");
            mPresenter.SearchClientName(mReference,Resenas);
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
            total_puntuacion =+ Resenas.get(i).getCalificacion();
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
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}
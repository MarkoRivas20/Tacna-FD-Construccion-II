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

    String Id_Establecimiento = "";

    TextView LblPuntuacion;
    TextView LblTotal;
    TextView LblNo_Resenas;

    ProgressBar ProgressBar_Numero_Uno;
    ProgressBar ProgressBar_Numero_Dos;
    ProgressBar ProgressBar_Numero_Tres;
    ProgressBar ProgressBar_Numero_Cuatro;
    ProgressBar ProgressBar_Numero_Cinco;
    RatingBar ratingbarlistaresenas;

    int Contador_Numero_Uno = 0;
    int Contador_Numero_Dos = 0;
    int Contador_Numero_Tres = 0;
    int Contador_Numero_Cuatro = 0;
    int Contador_Numero_Cinco = 0;

    Double total_puntuacion = 0.0;

    ArrayList <Resena_Modelo> resenas = new ArrayList<>();

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
        ratingbarlistaresenas = (RatingBar) view.findViewById(R.id.ratingbarlistaresenas);

        mPresenter=new ListarResena_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Resena");

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetReviews(mReference,Id_Establecimiento);

        return view;
    }

    @Override
    public void onGetReviewsSuccessful(ArrayList<Resena_Modelo> resena_modelos, Boolean Existe_Resena) {
        resenas = resena_modelos;

        if(Existe_Resena){
            LblNo_Resenas.setVisibility(View.GONE);
            mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");
            mPresenter.SearchClientName(mReference,resena_modelos);
        }
        else
        {
            LblNo_Resenas.setVisibility(View.VISIBLE);
            resenas.clear();
            Adaptador = new Resena_Adaptador(resenas, getActivity());
            Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            Recycler_View.setLayoutManager(Layout_Manager);
            Recycler_View.setAdapter(Adaptador);
            LblPuntuacion.setText("0");
            ratingbarlistaresenas.setRating(0);
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
        Toast.makeText(getActivity(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchClientNameSuccessful(ArrayList<Resena_Modelo> resena_modelos) {

        Adaptador = new Resena_Adaptador(resena_modelos, getActivity());
        Layout_Manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

        Contador_Numero_Uno = 0;
        Contador_Numero_Dos = 0;
        Contador_Numero_Tres = 0;
        Contador_Numero_Cuatro = 0;
        Contador_Numero_Cinco = 0;

        for(int i = 0; i < resena_modelos.size(); i++){

            if (resena_modelos.get(i).getCalificacion() <= 1.0)
            {
                Contador_Numero_Uno++;
            }
            else if (resena_modelos.get(i).getCalificacion() <= 2.0)
            {
                Contador_Numero_Dos++;
            }
            else if (resena_modelos.get(i).getCalificacion() <= 3.0)
            {
                Contador_Numero_Tres++;
            }
            else if (resena_modelos.get(i).getCalificacion() <= 4.0)
            {
                Contador_Numero_Cuatro++;
            }
            else if (resena_modelos.get(i).getCalificacion() <= 5.0)
            {
                Contador_Numero_Cinco++;
            }
            total_puntuacion =+ resena_modelos.get(i).getCalificacion();
        }

        ProgressBar_Numero_Uno.setMax(resena_modelos.size());
        ProgressBar_Numero_Dos.setMax(resena_modelos.size());
        ProgressBar_Numero_Tres.setMax(resena_modelos.size());
        ProgressBar_Numero_Cuatro.setMax(resena_modelos.size());
        ProgressBar_Numero_Cinco.setMax(resena_modelos.size());

        ProgressBar_Numero_Uno.setProgress(Contador_Numero_Uno);
        ProgressBar_Numero_Dos.setProgress(Contador_Numero_Dos);
        ProgressBar_Numero_Tres.setProgress(Contador_Numero_Tres);
        ProgressBar_Numero_Cuatro.setProgress(Contador_Numero_Cuatro);
        ProgressBar_Numero_Cinco.setProgress(Contador_Numero_Cinco);

        LblTotal.setText(String.valueOf(resena_modelos.size()));
        total_puntuacion = total_puntuacion/resena_modelos.size();
        LblPuntuacion.setText(String.valueOf(total_puntuacion));
        ratingbarlistaresenas.setRating(total_puntuacion.floatValue());

    }

    @Override
    public void onSearchClientNameFailure() {
        Toast.makeText(getActivity(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String Id_Establecimiento) {
        this.Id_Establecimiento = Id_Establecimiento;
    }
}
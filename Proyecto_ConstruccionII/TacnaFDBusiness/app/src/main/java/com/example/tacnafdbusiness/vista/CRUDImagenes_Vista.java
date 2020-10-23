package com.example.tacnafdbusiness.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.adaptador.Establecimiento_Adaptador;
import com.example.tacnafdbusiness.adaptador.Imagen_Adaptador;
import com.example.tacnafdbusiness.interfaces.CRUDImagenes;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
import com.example.tacnafdbusiness.presentador.CRUDImagenes_Presentador;
import com.example.tacnafdbusiness.presentador.ModificarEstablecimiento_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class CRUDImagenes_Vista extends Fragment implements CRUDImagenes.View {


    public CRUDImagenes_Vista() {
        // Required empty public constructor
    }

    public CRUDImagenes_Presentador mPresenter;
    public DatabaseReference mReference;
    public StorageReference mStorageReference;

    private RecyclerView Recycler_View;
    private Imagen_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    String Id_Establecimiento = "";
    String Id_Imagen_Establecimiento = "";

    private static final int PICK_IMAGE = 100;

    Uri Image_Uri;

    Button BtnSubir_Imagen;

    TextView LblNo_Imagenes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crud_imagenes__vista, container, false);

        BtnSubir_Imagen = (Button) view.findViewById(R.id.BtnSubir_Imagen);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_ImagenesEstablecimiento);
        LblNo_Imagenes = (TextView) view.findViewById(R.id.LblNo_Imagenes);

        mPresenter =new CRUDImagenes_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Imagen_Establecimiento");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetAllImages(mReference, Id_Establecimiento);


        BtnSubir_Imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Image_Uri = data.getData();
            Id_Imagen_Establecimiento = mReference.push().getKey();
            ImagenEstablecimiento_Modelo imagenEstablecimiento_modelo = new ImagenEstablecimiento_Modelo(Id_Imagen_Establecimiento,Id_Establecimiento,"");
            mPresenter.UploadImage(mStorageReference, mReference, imagenEstablecimiento_modelo, Image_Uri);

        }
    }

    @Override
    public void onUploadImageSuccessful() {
        Toast.makeText(getActivity().getApplicationContext(),"Imagen Subida Satisfactoriamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadImageFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteImageSuccessful() {
        Toast.makeText(getActivity().getApplicationContext(),"Imagen Borrada Satisfactoriamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteImageFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetAllImagesSuccessful(final ArrayList<ImagenEstablecimiento_Modelo> imagenEstablecimiento_modelos, Boolean Existe_Imagen) {

        Adaptador = new Imagen_Adaptador(imagenEstablecimiento_modelos, getActivity());
        Adaptador.setOnItemClickListener(new Imagen_Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDelete(int position) {
                mPresenter.DeleteImage(mReference, imagenEstablecimiento_modelos.get(position).getID_Imagen_Establecimiento(), imagenEstablecimiento_modelos.get(position).getUrl_Imagen());
            }

            @Override
            public void onCancel(int position) {

            }
        });
        Layout_Manager = new GridLayoutManager(getActivity(), 2);
        Recycler_View.setLayoutManager(Layout_Manager);
        Recycler_View.setAdapter(Adaptador);

        if(Existe_Imagen){

            LblNo_Imagenes.setVisibility(View.GONE);
        }
        else
        {
            LblNo_Imagenes.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onGetAllImagesFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo paso...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String Id_Establecimiento) {
        this.Id_Establecimiento=Id_Establecimiento;

    }
}
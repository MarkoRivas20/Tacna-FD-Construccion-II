package com.example.tacnafdbusiness.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.RegistrarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.example.tacnafdbusiness.presentador.RegistrarEstablecimiento_Presentador;
import com.example.tacnafdbusiness.presentador.RegistrarItemMenu_Presentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;


public class RegistrarItemMenu_Vista extends Fragment implements RegistrarItemMenu.View {

    public RegistrarItemMenu_Vista() {
        // Required empty public constructor
    }
    public RegistrarItemMenu_Presentador mPresenter;
    public DatabaseReference mReference;
    public StorageReference mStorageReference;

    FloatingActionButton Fab;
    Button BtnRegistrar_Item_Menu;

    EditText TxtNombre;
    EditText TxtPrecio;
    EditText TxtDescripcion;

    private static final int PICK_IMAGE = 100;

    Uri Image_Uri;

    ImageView Imagen_Item_Menu;

    Spinner Spinner_Estado;

    Boolean Imagen_Seleccionada = false;

    String[] Estados = {"Seleccione un Estado", "Activo", "Inactivo"};

    String ID_Establecimiento = "";
    String ID_Item_Menu = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_item_menu__vista, container, false);

        mPresenter =new RegistrarItemMenu_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("ItemMenu");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        Spinner_Estado = (Spinner) view.findViewById(R.id.spinnerestado);
        Fab = (FloatingActionButton) view.findViewById(R.id.fab_item_menu);
        Imagen_Item_Menu = (ImageView) view.findViewById(R.id.imagen_item_menu);
        BtnRegistrar_Item_Menu = (Button) view.findViewById(R.id.BtnRegistrar_Item_Menu);
        TxtNombre = (EditText) view.findViewById(R.id.txtnombre);
        TxtPrecio = (EditText) view.findViewById(R.id.txtprecio);
        TxtDescripcion = (EditText) view.findViewById(R.id.txtdescripcion);

        Spinner_Estado.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Estados));

        mPresenter.GetEstablishmentInfo(getActivity());

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }

            }
        });

        BtnRegistrar_Item_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TxtNombre.getText().toString().length() != 0 && TxtPrecio.getText().toString().length() != 0 && TxtDescripcion.getText().toString().length() != 0 &&
                        Spinner_Estado.getSelectedItemPosition() != 0 && Imagen_Seleccionada)
                {
                    mPresenter.UploadItemMenuImage(mStorageReference, ID_Establecimiento, Image_Uri);
                }
                else
                {
                    Toast.makeText(getActivity(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            Image_Uri = data.getData();
            Imagen_Item_Menu.setImageURI(Image_Uri);
            Imagen_Seleccionada = true;
        }
    }

    @Override
    public void onSaveItemMenuSuccessful() {
        Toast.makeText(getActivity().getApplicationContext(),"Item Registrado Satisfactoriamente",Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSaveItemMenuFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadItemMenuImageSuccessful(String Url_Imagen) {

        ID_Item_Menu = mReference.push().getKey();
        ItemMenu_Modelo Item_Menu = new ItemMenu_Modelo(ID_Item_Menu, ID_Establecimiento, TxtNombre.getText().toString(),
                TxtPrecio.getText().toString(), TxtDescripcion.getText().toString(), Url_Imagen, Spinner_Estado.getSelectedItem().toString());

        mPresenter.SaveItemMenu(mReference, Item_Menu);
    }

    @Override
    public void onUploadItemMenuImageFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}
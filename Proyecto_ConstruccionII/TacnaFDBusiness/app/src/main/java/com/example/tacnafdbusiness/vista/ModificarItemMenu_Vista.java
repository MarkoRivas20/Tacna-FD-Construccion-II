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
import com.example.tacnafdbusiness.interfaces.ModificarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.example.tacnafdbusiness.presentador.ModificarEstablecimiento_Presentador;
import com.example.tacnafdbusiness.presentador.ModificarItemMenu_Presentador;
import com.example.tacnafdbusiness.presentador.RegistrarItemMenu_Presentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class ModificarItemMenu_Vista extends Fragment implements ModificarItemMenu.View {

    public ModificarItemMenu_Vista() {
        // Required empty public constructor
    }

    public ModificarItemMenu_Presentador mPresenter;
    public DatabaseReference mReference;
    public StorageReference mStorageReference;

    FloatingActionButton fab;
    Button BtnModificar_Item_Menu;

    EditText TxtNombre;
    EditText TxtPrecio;
    EditText TxtDescripcion;

    private static final int PICK_IMAGE = 100;

    Uri Image_Uri;

    ImageView Imagen_Item_Menu;

    Spinner Spinner_Estado;

    String[] estados = {"Seleccione un Estado", "Activo", "Inactivo"};

    String Id_Establecimiento = "";
    String Id_Item_Menu = "";
    String Url_Imagen = "";

    Bundle Item_Menu_Info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_item_menu__vista, container, false);

        Item_Menu_Info = getArguments();
        Id_Item_Menu = Item_Menu_Info.getString("Id_Item_Menu");

        mPresenter =new ModificarItemMenu_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("ItemMenu");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        Spinner_Estado = (Spinner) view.findViewById(R.id.spinnerestado);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_item_menu);
        Imagen_Item_Menu = (ImageView) view.findViewById(R.id.imagen_item_menu);
        BtnModificar_Item_Menu = (Button) view.findViewById(R.id.BtnModificar_Item_Menu);
        TxtNombre = (EditText) view.findViewById(R.id.txtnombre);
        TxtPrecio = (EditText) view.findViewById(R.id.txtprecio);
        TxtDescripcion = (EditText) view.findViewById(R.id.txtdescripcion);

        Spinner_Estado.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, estados));

        mPresenter.GetItemMenuData(mReference, Id_Item_Menu);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }

            }
        });

        BtnModificar_Item_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemMenu_Modelo itemMenu_modelo = new ItemMenu_Modelo(Id_Item_Menu,Id_Establecimiento, TxtNombre.getText().toString(),
                        TxtPrecio.getText().toString(), TxtDescripcion.getText().toString(), Url_Imagen, Spinner_Estado.getSelectedItem().toString());
                mPresenter.UpdateItemMenuData(mReference,itemMenu_modelo);
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Image_Uri = data.getData();
            Imagen_Item_Menu.setImageURI(Image_Uri);
            mPresenter.UpdateItemMenuImage(mStorageReference, mReference, Url_Imagen, Id_Establecimiento,Id_Item_Menu, Image_Uri);
        }
    }

    @Override
    public void onUpdateItemMenuDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateItemMenuDataSuccessful() {
        Toast.makeText(getActivity(),"Item Modificado Satisfactoriamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateItemMenuImageFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateItemMenuImageSuccessful(String Url_Imagen) {
        this.Url_Imagen = Url_Imagen;
        Toast.makeText(getActivity(),"Imagen Actualizada Satisfactoriamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetItemMenuDataSuccessful(ItemMenu_Modelo itemMenu_modelo) {

        Picasso.with(getActivity()).load(itemMenu_modelo.getUrl_Imagen()).into(Imagen_Item_Menu);
        Id_Establecimiento = itemMenu_modelo.getID_Establecimiento();
        Url_Imagen = itemMenu_modelo.getUrl_Imagen();
        TxtNombre.setText(itemMenu_modelo.getNombre());
        TxtPrecio.setText(itemMenu_modelo.getPrecio());
        TxtDescripcion.setText(itemMenu_modelo.getDescripcion());

        for(int i=0; i<estados.length; i++){
            if(itemMenu_modelo.getEstado().equals(estados[i])){
                Spinner_Estado.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onGetItemMenuDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }
}
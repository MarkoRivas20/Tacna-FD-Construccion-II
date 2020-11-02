package com.example.tacnafddelivery.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.interfaces.ModificarUsuario;
import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.example.tacnafddelivery.presentador.ModificarUsuario_Presentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class ModificarUsuario_Vista extends Fragment implements ModificarUsuario.View {


    public ModificarUsuario_Vista() {
        // Required empty public constructor
    }

    public ModificarUsuario_Presentador mPresenter;
    public DatabaseReference mReference;
    private StorageReference mStorageReference;

    private static final int PICK_IMAGE = 100;

    Uri Imagen_Uri;

    EditText TxtEmail;
    EditText TxtClave;
    EditText TxtNombre;
    EditText TxtApellido;

    ImageView ImgFoto_Usuario;
    ImageView foto_repartidor;

    Button BtnModificar;
    FloatingActionButton fab;

    String Correo_Electronico = "";
    String Id_Usuario = "";
    String Url_Foto = "";
    String Nombre_Usuario = "";

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    TextView LblNombre_Nav;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_usuario__vista, container, false);

        mPresenter=new ModificarUsuario_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Repartidor");
        mStorageReference = FirebaseStorage.getInstance().getReference().child("Repartidores");

        mPresenter.GetSessionData(getActivity());

        TxtEmail = (EditText) view.findViewById(R.id.txtemail);
        TxtClave = (EditText) view.findViewById(R.id.txtclave);
        TxtNombre = (EditText) view.findViewById(R.id.txtnombre);
        TxtApellido = (EditText) view.findViewById(R.id.txtapellido);
        foto_repartidor = (ImageView) view.findViewById(R.id.foto_repartidor);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_repartidor);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);

        View View_Navigation = navigationView.getHeaderView(0);
        LblNombre_Nav = View_Navigation.findViewById(R.id.lblnombre_nav);
        ImgFoto_Usuario = View_Navigation.findViewById(R.id.ImgFoto_Usuario);

        BtnModificar = (Button) view.findViewById(R.id.btnmodificar);
        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario_Modelo usuario_modelo = new Usuario_Modelo(Id_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                        TxtEmail.getText().toString(),TxtClave.getText().toString(), Url_Foto);
                mPresenter.UpdateUserData(mReference,usuario_modelo);
            }
        });

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

        drawerLayout = view.findViewById(R.id.drawer_layout);
        mPresenter.ShowUserData(mReference, Correo_Electronico);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Imagen_Uri = data.getData();
            foto_repartidor.setImageURI(Imagen_Uri);
            mPresenter.UpdateUserPhoto(mStorageReference, mReference, Url_Foto, Id_Usuario, Imagen_Uri);

        }
    }

    @Override
    public void onUpdateUserDataSuccessful() {

        Nombre_Usuario = TxtNombre.getText().toString() +" "+ TxtApellido.getText().toString();
        mPresenter.SaveSession(getActivity(),Nombre_Usuario, Url_Foto);
        Toast.makeText(getActivity().getApplicationContext(),"Datos Actualizados Satisfactoriamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserPhotoSuccessful(String Url_Foto) {
        this.Url_Foto = Url_Foto;
        mPresenter.SaveSession(getActivity(),Nombre_Usuario, Url_Foto);
    }

    @Override
    public void onUpdateUserPhotoFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowUserDataSuccessful(Usuario_Modelo usuario_modelo) {
        Id_Usuario = usuario_modelo.getID_Usuario_Repartidor();
        TxtEmail.setText(usuario_modelo.getCorreo_Electronico());
        TxtClave.setText(usuario_modelo.getContrasena());
        TxtNombre.setText(usuario_modelo.getNombre());
        TxtApellido.setText(usuario_modelo.getApellido());
        Url_Foto = usuario_modelo.getUrl_Foto();
        Nombre_Usuario = usuario_modelo.getNombre() + " " + usuario_modelo.getApellido();
        Picasso.with(getActivity()).load(usuario_modelo.getUrl_Foto()).into(foto_repartidor);

    }

    @Override
    public void onShowUserDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionDataSuccessful(String correo_electronico) {
        Correo_Electronico = correo_electronico;
    }

    @Override
    public void onSaveSessionSuccessful() {
        LblNombre_Nav.setText(Nombre_Usuario);
        Picasso.with(getActivity()).load(Url_Foto).into(ImgFoto_Usuario);
    }
}
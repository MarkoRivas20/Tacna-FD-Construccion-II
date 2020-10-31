package com.example.tacnafddelivery.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.interfaces.RegistrarUsuario;
import com.example.tacnafddelivery.modelo.Usuario_Modelo;
import com.example.tacnafddelivery.presentador.RegistroUsuario_Presentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RegistroUsuario_Vista extends AppCompatActivity implements RegistrarUsuario.View {

    public RegistroUsuario_Presentador mPresenter;
    public DatabaseReference mReference;
    private StorageReference mStorageReference;

    EditText TxtEmail;
    EditText TxtClave;
    EditText TxtNombre;
    EditText TxtApellido;

    FloatingActionButton fab;

    private static final int PICK_IMAGE = 100;

    Uri Image_Uri;

    ImageView Foto_Repartidor;

    Boolean Imagen_Seleccionada = false;

    Button BtnCrear;

    Window window;

    String ID_Usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#003152"));

        mPresenter=new RegistroUsuario_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Repartidor");
        mStorageReference = FirebaseStorage.getInstance().getReference().child("Repartidores");

        TxtEmail = (EditText) findViewById(R.id.txtemail);
        TxtClave = (EditText) findViewById(R.id.txtclave);
        TxtNombre = (EditText) findViewById(R.id.txtnombre);
        TxtApellido = (EditText) findViewById(R.id.txtapellido);
        fab = (FloatingActionButton) findViewById(R.id.fab_repartidor);
        Foto_Repartidor = (ImageView) findViewById(R.id.foto_repartidor);
        BtnCrear = (Button) findViewById(R.id.btncrear);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }


            }
        });



        BtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_Usuario = mReference.push().getKey();
                mPresenter.UploadPhoto(mStorageReference, ID_Usuario, Image_Uri);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Image_Uri = data.getData();
            Foto_Repartidor.setImageURI(Image_Uri);
            Imagen_Seleccionada = true;

        }
    }

    @Override
    public void onCreateUserSuccessful() {
        Toast.makeText(getApplicationContext(),"Nuevo Usuario creado",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateUserFailure() {
        Toast.makeText(getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateUserFailureUsedMail() {
        TxtEmail.setError("Este correo ya esta en uso");
    }

    @Override
    public void onUploadPhotoSuccessful(String Url_Foto) {

        Usuario_Modelo usuario_modelo=new Usuario_Modelo(ID_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                TxtEmail.getText().toString(),TxtClave.getText().toString(), Url_Foto);
        mPresenter.CreateNewUser(mReference,usuario_modelo);

    }

    @Override
    public void onUploadPhotoFailure() {
        Toast.makeText(getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }
}
package com.example.tacnafdcliente.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.RegistrarUsuario;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.example.tacnafdcliente.presentador.RegistroUsuario_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroUsuario_Vista extends AppCompatActivity implements RegistrarUsuario.View {

    public RegistroUsuario_Presentador mPresenter;
    public DatabaseReference mReference;

    EditText TxtEmail;
    EditText TxtClave;
    EditText TxtNombre;
    EditText TxtApellido;

    Button BtnCrear;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#003152"));

        mPresenter=new RegistroUsuario_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");

        TxtEmail = (EditText) findViewById(R.id.txtemail);
        TxtClave = (EditText) findViewById(R.id.txtclave);
        TxtNombre = (EditText) findViewById(R.id.txtnombre);
        TxtApellido = (EditText) findViewById(R.id.txtapellido);

        BtnCrear = (Button) findViewById(R.id.btncrear);

        BtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TxtEmail.getText().toString().equals("") && !TxtNombre.getText().toString().equals("") && !TxtApellido.getText().toString().equals("") &&
                        !TxtClave.getText().toString().equals(""))
                {
                    String ID_Usuario=mReference.push().getKey();
                    Usuario_Modelo usuario_modelo=new Usuario_Modelo(ID_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                            TxtEmail.getText().toString(),TxtClave.getText().toString());
                    mPresenter.CreateNewUser(mReference,usuario_modelo);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });

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
}
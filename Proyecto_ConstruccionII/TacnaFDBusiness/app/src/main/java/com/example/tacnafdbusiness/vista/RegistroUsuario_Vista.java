package com.example.tacnafdbusiness.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.Registro;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.example.tacnafdbusiness.presentador.RegistroUsuario_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroUsuario_Vista extends AppCompatActivity implements Registro.View {

    public RegistroUsuario_Presentador mPresenter;
    public DatabaseReference mReference;

    EditText TxtEmail;
    EditText TxtClave;
    EditText TxtNombre;
    EditText TxtApellido;
    EditText TxtCelular;
    EditText TxtRuc;
    EditText TxtCodigo_Paypal;

    Button BtnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario__vista);

        mPresenter=new RegistroUsuario_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Propietario");

        TxtEmail = (EditText) findViewById(R.id.txtemail);
        TxtClave = (EditText) findViewById(R.id.txtclave);
        TxtNombre = (EditText) findViewById(R.id.txtnombre);
        TxtApellido = (EditText) findViewById(R.id.txtapellido);
        TxtCelular = (EditText) findViewById(R.id.txtcelular);
        TxtRuc = (EditText) findViewById(R.id.txtruc);
        TxtCodigo_Paypal = (EditText) findViewById(R.id.txtcodigopaypal);

        BtnCrear = (Button) findViewById(R.id.btncrear);

        BtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID_Usuario=mReference.push().getKey();
                Usuario_Modelo usuario_modelo=new Usuario_Modelo(ID_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                        TxtEmail.getText().toString(),TxtClave.getText().toString(),TxtCelular.getText().toString(),
                        TxtRuc.getText().toString(),TxtCodigo_Paypal.getText().toString());
                mPresenter.CreateNewUser(mReference,usuario_modelo);
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
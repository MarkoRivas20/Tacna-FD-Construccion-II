package com.example.tacnafdcliente.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.Login;
import com.example.tacnafdcliente.presentador.Login_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Vista extends AppCompatActivity  implements Login.View {

    public Login_Presentador mPresenter;
    public DatabaseReference mReference;

    EditText TxtEmail;
    EditText TxtClave;

    Button BtnLogIn;
    Button BtnRegistro;

    TextView LblRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__vista);

        mPresenter=new Login_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");

        mPresenter.CheckSession(getApplicationContext());

        TxtEmail = (EditText) findViewById(R.id.txtemail);
        TxtClave = (EditText) findViewById(R.id.txtclave);

        BtnRegistro = (Button) findViewById(R.id.btnregistro);
        BtnLogIn = (Button) findViewById(R.id.btnlogin);

        LblRecuperar = (TextView) findViewById(R.id.lblrecover);

        BtnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistroUsuario_Vista.class);
                startActivity(intent);
            }
        });

        BtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.LogIn(mReference,TxtEmail.getText().toString(),TxtClave.getText().toString());

            }
        });

        LblRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuscarEmail_Vista.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLogInSuccessful(String nombre_usuario, String id_usuario) {
       /* mPresenter.SaveSession(getApplicationContext(), TxtEmail.getText().toString(), nombre_usuario, id_usuario);

        Intent intent = new Intent(getApplicationContext(), PantallaPrincipal_Vista.class);
        startActivity(intent);
        finish();*/
    }

    @Override
    public void onLogInFailure() {
        TxtEmail.setError("Credenciales invalidas");
        TxtClave.setError("Credenciales invalidas");
    }

    @Override
    public void onSuccessfulCheck() {
        /*
        Intent intent = new Intent(getApplicationContext(), PantallaPrincipal_Vista.class);
        startActivity(intent);
        finish();*/
    }
}
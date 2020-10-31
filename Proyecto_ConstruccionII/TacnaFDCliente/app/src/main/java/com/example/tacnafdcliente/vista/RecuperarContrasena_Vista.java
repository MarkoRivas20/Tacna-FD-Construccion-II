package com.example.tacnafdcliente.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.RecuperarContrasena;
import com.example.tacnafdcliente.presentador.RecuperarContrasena_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecuperarContrasena_Vista extends AppCompatActivity implements RecuperarContrasena.View {

    public RecuperarContrasena_Presentador mPresenter;
    public DatabaseReference mReference;

    EditText TxtNueva_Clave;
    EditText TxtNueva_Clave_Repite;

    Button BtnActualizar;

    String Correo_Electronico;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#003152"));

        Bundle datos = this.getIntent().getExtras();
        Correo_Electronico = datos.getString("correo_electronico");

        mPresenter = new RecuperarContrasena_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");

        TxtNueva_Clave = (EditText) findViewById(R.id.txtnuevaclave);
        TxtNueva_Clave_Repite = (EditText) findViewById(R.id.txtnuevaclave2);

        BtnActualizar = (Button) findViewById(R.id.btnactualizar);

        BtnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TxtNueva_Clave.getText().toString().equals(TxtNueva_Clave_Repite.getText().toString())){

                    mPresenter.RestorePassword(mReference,Correo_Electronico,TxtNueva_Clave.getText().toString());

                }else {

                    TxtNueva_Clave_Repite.setError("Las contraseñas deben ser iguales");

                }

            }
        });
    }

    @Override
    public void onRestorePasswordSuccessful() {
        Toast.makeText(getApplicationContext(),"Contraseña Actualizada",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Login_Vista.class);
        startActivity(intent);
    }

    @Override
    public void onRestorePasswordFailure() {
        Toast.makeText(getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }
}
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
import com.example.tacnafdcliente.interfaces.BuscarEmail;
import com.example.tacnafdcliente.presentador.BuscarEmail_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuscarEmail_Vista extends AppCompatActivity implements BuscarEmail.View {

    public BuscarEmail_Presentador mPresenter;
    public DatabaseReference mReference;

    EditText TxtEmail;

    Button BtnEnviar;

    int Codigo = 0;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_email__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#003152"));

        mPresenter=new BuscarEmail_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");

        TxtEmail = (EditText) findViewById(R.id.txtemailrecover);

        BtnEnviar = (Button) findViewById(R.id.btnenviar);

        BtnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Codigo = (int) (Math.random() * (100000-10000)) + 10000;

                mPresenter.SearchEmail(mReference, TxtEmail.getText().toString(), Codigo);
            }
        });

    }

    @Override
    public void onSearchEmailSuccessful() {
        Intent intent = new Intent(getApplicationContext(), IngresarCodigo_Vista.class);
        intent.putExtra("codigo", Codigo);
        intent.putExtra("correo_electronico", TxtEmail.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onSearchEmailFailure() {
        Toast.makeText(getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotFoundEmailFailure() {
        TxtEmail.setError("No se encontro el correo electronico");
    }
}
package com.example.tacnafdbusiness.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.tacnafdbusiness.R;

public class IngresarCodigo_Vista extends AppCompatActivity {

    int Codigo = 0;
    int Contador = 0;

    String Correo_Electronico = "";

    PinView pinView;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_codigo__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#003152"));

        Bundle datos = this.getIntent().getExtras();
        Codigo = datos.getInt("codigo");
        Correo_Electronico = datos.getString("correo_electronico");


        pinView = (PinView) findViewById(R.id.PinView);
        pinView.setFocusable(true);
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Contador=start+1;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(Contador == 5){

                    if(s.toString().equals(String.valueOf(Codigo))){

                        Intent intent = new Intent(getApplicationContext(), RecuperarContrasena_Vista.class);
                        intent.putExtra("correo_electronico",Correo_Electronico);
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"El codigo es incorrecto",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}
package com.example.tacnafdbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.tacnafdbusiness.vista.Login_Vista;

public class MainActivity extends AppCompatActivity {


    ImageView Imagen_Tacna;
    ImageView Imagen_Business;

    Animation From_Bottom;
    Animation From_Top;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        From_Bottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        From_Top = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        Imagen_Business = (ImageView) findViewById(R.id.logobusiness);
        Imagen_Tacna = (ImageView) findViewById(R.id.logotacna);

        Imagen_Tacna.startAnimation(From_Top);
        Imagen_Business.startAnimation(From_Bottom);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#003152"));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login_Vista.class);
                startActivity(intent);
                finish();
            }
        },5000);
        

    }
}
package com.example.tacnafddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.tacnafddelivery.vista.Login_Vista;

public class MainActivity extends AppCompatActivity {

    ImageView Imagen_Tacna;
    ImageView Imagen_Delivery;

    Animation frombottom;
    Animation fromtop;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        Imagen_Delivery = (ImageView) findViewById(R.id.logobusiness);
        Imagen_Tacna = (ImageView) findViewById(R.id.logotacna);

        Imagen_Tacna.startAnimation(fromtop);
        Imagen_Delivery.startAnimation(frombottom);

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
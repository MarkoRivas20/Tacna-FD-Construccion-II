package com.example.tacnafddelivery.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafddelivery.R;
import com.example.tacnafddelivery.interfaces.PantallaPrincipal;
import com.example.tacnafddelivery.presentador.PantallaPrincipal_Presentador;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class PantallaPrincipal_Vista extends AppCompatActivity implements PantallaPrincipal.View, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Window window;

    TextView LblNombre_Nav;
    TextView LblEmail_Nav;

    ImageView ImgFoto_Usuario;

    public PantallaPrincipal_Presentador mPresenter;

    ModificarUsuario_Vista modificarUsuario_vista;
    ListarEstablecimiento_Vista listarEstablecimiento_vista;
    SeguimientoPedido_Vista seguimientoPedido_vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#0031A8"));

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_toolbar);
        toolbar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_establecimiento);

        modificarUsuario_vista = new ModificarUsuario_Vista();
        listarEstablecimiento_vista = new ListarEstablecimiento_Vista();
        seguimientoPedido_vista = new SeguimientoPedido_Vista();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmento, listarEstablecimiento_vista).commit();

        View View_Navigation = navigationView.getHeaderView(0);
        LblNombre_Nav = View_Navigation.findViewById(R.id.lblnombre_nav);
        LblEmail_Nav = View_Navigation.findViewById(R.id.lblemail_nav);
        ImgFoto_Usuario = View_Navigation.findViewById(R.id.ImgFoto_Usuario);

        mPresenter=new PantallaPrincipal_Presentador(this);

        mPresenter.CheckTrackingOrderSharedPreference(getApplicationContext());

        mPresenter.GetSessionData(getApplicationContext());


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

            super.onBackPressed();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){

            case R.id.nav_establecimiento:
                toolbar.setNavigationIcon(R.drawable.icon_toolbar);
                toolbar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
                navigationView.setCheckedItem(R.id.nav_establecimiento);
                transaction.replace(R.id.fragmento,listarEstablecimiento_vista);
                break;

            case R.id.nav_datos:
                toolbar.setNavigationIcon(R.drawable.icon_toolbar);
                toolbar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
                navigationView.setCheckedItem(R.id.nav_datos);
                transaction.replace(R.id.fragmento,modificarUsuario_vista);
                break;

            case R.id.nav_sesion:
                mPresenter.CloseSession(getApplicationContext());
                break;

        }

        transaction.commit();

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    public void onSessionDataSuccessful(String correo_electronico, String nombre_usuario, String url_foto) {
        LblNombre_Nav.setText(nombre_usuario);
        LblEmail_Nav.setText(correo_electronico);
        Picasso.with(getApplicationContext()).load(url_foto).into(ImgFoto_Usuario);
    }

    @Override
    public void onSessionDataFailure() {
        Toast.makeText(getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCloseSessionSuccessful() {
        Intent intent = new Intent(getApplicationContext(), Login_Vista.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onCheckTrackingOrderSharedPreferenceSuccessful(String Seguimiento) {

        if(Seguimiento.equals("Activo"))
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, seguimientoPedido_vista).addToBackStack(null).commit();
        }
    }

}
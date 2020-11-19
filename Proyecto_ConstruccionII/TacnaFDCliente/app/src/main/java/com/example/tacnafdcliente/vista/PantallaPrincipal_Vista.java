package com.example.tacnafdcliente.vista;

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
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.PantallaPrincipal;
import com.example.tacnafdcliente.presentador.PantallaPrincipal_Presentador;
import com.google.android.material.navigation.NavigationView;

public class PantallaPrincipal_Vista extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PantallaPrincipal.View{

    DrawerLayout Drawer_Layout;
    NavigationView Navigation_View;
    Toolbar Tool_bar;
    Window window;

    ModificarUsuario_Vista modificarUsuario_vista;
    Inicio_Vista inicio_vista;
    ListarEstablecimiento_Vista listarEstablecimiento_vista;
    ListarPedido_Vista listarPedido_vista;
    ListarMiCupon_Vista listarMiCupon_vista;

    TextView LblNombre_Nav;
    TextView LblEmail_Nav;

    public PantallaPrincipal_Presentador mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#0031A8"));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Drawer_Layout = findViewById(R.id.drawer_layout);
        Navigation_View = findViewById(R.id.nav_view);
        Tool_bar = findViewById(R.id.toolbar);

        setSupportActionBar(Tool_bar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Tool_bar.setNavigationIcon(R.drawable.icon_toolbar);
        Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));

        Navigation_View.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,Drawer_Layout,Tool_bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        Drawer_Layout.addDrawerListener(toggle);

        toggle.syncState();

        Navigation_View.setNavigationItemSelectedListener(this);
        Navigation_View.setCheckedItem(R.id.nav_inicio);


        modificarUsuario_vista = new ModificarUsuario_Vista();
        inicio_vista = new Inicio_Vista();
        listarEstablecimiento_vista = new ListarEstablecimiento_Vista();
        listarPedido_vista = new ListarPedido_Vista();
        listarMiCupon_vista = new ListarMiCupon_Vista();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmento,inicio_vista).commit();


        View View_Navigation = Navigation_View.getHeaderView(0);
        LblNombre_Nav = View_Navigation.findViewById(R.id.lblnombre_nav);
        LblEmail_Nav = View_Navigation.findViewById(R.id.lblemail_nav);

        mPresenter=new PantallaPrincipal_Presentador(this);
        mPresenter.GetSessionData(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        if(Drawer_Layout.isDrawerOpen(GravityCompat.START))
        {
            Drawer_Layout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){

            case R.id.nav_inicio:
                Tool_bar.setNavigationIcon(R.drawable.icon_toolbar);
                Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
                Navigation_View.setCheckedItem(R.id.nav_inicio);
                transaction.replace(R.id.fragmento,inicio_vista);
                break;

            case R.id.nav_establecimiento:
                Tool_bar.setNavigationIcon(R.drawable.icon_toolbar_black);
                Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#E3E3E3")));
                Navigation_View.setCheckedItem(R.id.nav_establecimiento);
                transaction.replace(R.id.fragmento,listarEstablecimiento_vista);
                break;

            case R.id.nav_pedido:
                Tool_bar.setNavigationIcon(R.drawable.icon_toolbar);
                Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
                Navigation_View.setCheckedItem(R.id.nav_pedido);
                transaction.replace(R.id.fragmento,listarPedido_vista);
                break;

            case R.id.nav_cupones:
                Tool_bar.setNavigationIcon(R.drawable.icon_toolbar);
                Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
                Navigation_View.setCheckedItem(R.id.nav_cupones);
                transaction.replace(R.id.fragmento,listarMiCupon_vista);
                break;

            case R.id.nav_datos:
                Tool_bar.setNavigationIcon(R.drawable.icon_toolbar);
                Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
                Navigation_View.setCheckedItem(R.id.nav_datos);
                transaction.replace(R.id.fragmento,modificarUsuario_vista);
                break;

            case R.id.nav_sesion:
                mPresenter.CloseSession(getApplicationContext());
                break;

        }

        transaction.commit();

        Drawer_Layout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onSessionDataSuccessful(String correo_electronico, String nombre_usuario) {
        LblNombre_Nav.setText(nombre_usuario);
        LblEmail_Nav.setText(correo_electronico);
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
}
package com.example.tacnafdbusiness.vista;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.PantallaPrincipal;
import com.example.tacnafdbusiness.presentador.Login_Presentador;
import com.example.tacnafdbusiness.presentador.PantallaPrincipal_Presentador;
import com.google.android.material.navigation.NavigationView;

public class PantallaPrincipal_Vista extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PantallaPrincipal.View {

    DrawerLayout Drawer_Layout;
    NavigationView Navigation_View;
    Toolbar Tool_bar;
    Window window;

    Dashboard_Vista dashboard_vista;
    ModificarUsuario_Vista modificarUsuario_vista;
    ListarEstablecimiento_Vista listarEstablecimiento_vista;

    TextView LblNombre_Nav;
    TextView LblEmail_Nav;

    public PantallaPrincipal_Presentador mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal__vista);

        this.window=getWindow();
        window.setStatusBarColor(Color.parseColor("#0031A8"));

        Drawer_Layout = findViewById(R.id.drawer_layout);
        Navigation_View = findViewById(R.id.nav_view);
        Tool_bar = findViewById(R.id.toolbar);

        setSupportActionBar(Tool_bar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Tool_bar.setNavigationIcon(R.drawable.icon_toolbar_black);

        Navigation_View.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,Drawer_Layout,Tool_bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        Drawer_Layout.addDrawerListener(toggle);

        toggle.syncState();

        Navigation_View.setNavigationItemSelectedListener(this);
        Navigation_View.setCheckedItem(R.id.nav_dashboard);


        dashboard_vista = new Dashboard_Vista();
        modificarUsuario_vista = new ModificarUsuario_Vista();
        listarEstablecimiento_vista = new ListarEstablecimiento_Vista();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmento,dashboard_vista).commit();


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

            case R.id.nav_dashboard:
                Tool_bar.setNavigationIcon(R.drawable.icon_toolbar_black);
                Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#FFFFFF")));
                Navigation_View.setCheckedItem(R.id.nav_dashboard);
                transaction.replace(R.id.fragmento,dashboard_vista);
                break;

            case R.id.nav_establecimiento:
                Tool_bar.setNavigationIcon(R.drawable.icon_toolbar);
                Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
                Navigation_View.setCheckedItem(R.id.nav_establecimiento);
                transaction.replace(R.id.fragmento,listarEstablecimiento_vista);
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
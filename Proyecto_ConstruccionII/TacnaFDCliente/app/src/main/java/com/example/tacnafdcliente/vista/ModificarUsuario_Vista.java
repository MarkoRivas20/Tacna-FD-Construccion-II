package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.ModificarUsuario;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.example.tacnafdcliente.presentador.ModificarUsuario_Presentador;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ModificarUsuario_Vista extends Fragment implements ModificarUsuario.View {


    public ModificarUsuario_Vista() {
        // Required empty public constructor
    }

    public ModificarUsuario_Presentador mPresenter;
    public DatabaseReference mReference;


    EditText TxtEmail;
    EditText TxtClave;
    EditText TxtNombre;
    EditText TxtApellido;


    Button BtnModificar;

    String Correo_Electronico = "";
    String Id_Usuario = "";
    String Nombre_Usuario = "";

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    TextView LblNombre_Nav;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_usuario__vista, container, false);

        mPresenter=new ModificarUsuario_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");

        mPresenter.GetSessionData(getActivity());

        TxtEmail = (EditText) view.findViewById(R.id.txtemail);
        TxtClave = (EditText) view.findViewById(R.id.txtclave);
        TxtNombre = (EditText) view.findViewById(R.id.txtnombre);
        TxtApellido = (EditText) view.findViewById(R.id.txtapellido);

        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);

        View View_Navigation = navigationView.getHeaderView(0);
        LblNombre_Nav = View_Navigation.findViewById(R.id.lblnombre_nav);

        BtnModificar = (Button) view.findViewById(R.id.btnmodificar);
        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario_Modelo usuario_modelo = new Usuario_Modelo(Id_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                        TxtEmail.getText().toString(),TxtClave.getText().toString());
                mPresenter.UpdateUserData(mReference,usuario_modelo);
            }
        });

        drawerLayout = view.findViewById(R.id.drawer_layout);
        mPresenter.ShowUserData(mReference, Correo_Electronico);

        return view;
    }

    @Override
    public void onUpdateUserDataSuccessful() {

        Nombre_Usuario = TxtNombre.getText().toString() +" "+ TxtApellido.getText().toString();
        mPresenter.SaveSession(getActivity(),Nombre_Usuario);
        Toast.makeText(getActivity().getApplicationContext(),"Datos Actualizados Satisfactoriamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onShowUserDataSuccessful(Usuario_Modelo usuario_modelo) {
        Id_Usuario = usuario_modelo.getID_Usuario_Cliente();
        TxtEmail.setText(usuario_modelo.getCorreo_Electronico());
        TxtClave.setText(usuario_modelo.getContrasena());
        TxtNombre.setText(usuario_modelo.getNombre());
        TxtApellido.setText(usuario_modelo.getApellido());
        Nombre_Usuario = usuario_modelo.getNombre() + " " + usuario_modelo.getApellido();

    }

    @Override
    public void onShowUserDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionDataSuccessful(String correo_electronico) {
        Correo_Electronico = correo_electronico;
    }

    @Override
    public void onSaveSessionSuccessful() {
        LblNombre_Nav.setText(Nombre_Usuario);
    }
}
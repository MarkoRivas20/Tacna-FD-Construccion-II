package com.example.tacnafdbusiness.vista;

import android.graphics.Color;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacnafdbusiness.R;
import com.example.tacnafdbusiness.interfaces.ModificarUsuario;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.example.tacnafdbusiness.presentador.Login_Presentador;
import com.example.tacnafdbusiness.presentador.ModificarUsuario_Presentador;
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
    EditText TxtCelular;
    EditText TxtRuc;

    Button BtnModificar;

    String Correo_Electronico = "";

    String ID_Usuario = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_modificar_usuario__vista, container, false);

        mPresenter=new ModificarUsuario_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Propietario");

        mPresenter.GetSessionData(getActivity().getApplicationContext());

        TxtEmail = (EditText) v.findViewById(R.id.txtemail);
        TxtClave = (EditText) v.findViewById(R.id.txtclave);
        TxtNombre = (EditText) v.findViewById(R.id.txtnombre);
        TxtApellido = (EditText) v.findViewById(R.id.txtapellido);
        TxtCelular = (EditText) v.findViewById(R.id.txtcelular);
        TxtRuc = (EditText) v.findViewById(R.id.txtruc);


        mPresenter.ShowUserData(mReference, Correo_Electronico);


        BtnModificar = (Button) v.findViewById(R.id.btnmodificar);
        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario_Modelo Usuario=new Usuario_Modelo(ID_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                        TxtEmail.getText().toString(),TxtClave.getText().toString(),TxtCelular.getText().toString(),
                        TxtRuc.getText().toString());
                mPresenter.UpdateUser(mReference, Usuario);
            }
        });

        return v;
    }

    @Override
    public void onUpdateUserSuccessful() {

    }

    @Override
    public void onUpdateUserFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowUserDataSuccessful(Usuario_Modelo Usuario) {

        ID_Usuario = Usuario.getID_Usuario();
        TxtEmail.setText(Usuario.getCorreo_Electronico());
        TxtClave.setText(Usuario.getContrasena());
        TxtNombre.setText(Usuario.getNombre());
        TxtApellido.setText(Usuario.getApellido());
        TxtCelular.setText(Usuario.getCelular());
        TxtRuc.setText(Usuario.getRuc());

    }

    @Override
    public void onShowUserDataFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionDataSuccessful(String Correo_Electronico) {
        this.Correo_Electronico = Correo_Electronico;

    }
}
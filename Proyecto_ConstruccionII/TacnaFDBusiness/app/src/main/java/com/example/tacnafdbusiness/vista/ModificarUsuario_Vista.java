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
    EditText TxtCodigo_Paypal;

    Button BtnModificar;

    String Correo_Electronico = "";

    String Id_Usuario = "";

    DrawerLayout drawerLayout;


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
        TxtCodigo_Paypal = (EditText) v.findViewById(R.id.txtcodigopaypal);



        mPresenter.ShowUserData(mReference, Correo_Electronico);


        BtnModificar = (Button) v.findViewById(R.id.btnmodificar);
        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario_Modelo usuario_modelo=new Usuario_Modelo(Id_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                        TxtEmail.getText().toString(),TxtClave.getText().toString(),TxtCelular.getText().toString(),
                        TxtRuc.getText().toString(),TxtCodigo_Paypal.getText().toString());
                mPresenter.UpdateUser(mReference,usuario_modelo);
            }
        });

        drawerLayout = v.findViewById(R.id.drawer_layout);



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
    public void onShowUserDataSuccessful(Usuario_Modelo usuario_modelo) {

        Id_Usuario = usuario_modelo.getID_Usuario();
        TxtEmail.setText(usuario_modelo.getCorreo_Electronico());
        TxtClave.setText(usuario_modelo.getContrasena());
        TxtNombre.setText(usuario_modelo.getNombre());
        TxtApellido.setText(usuario_modelo.getApellido());
        TxtCelular.setText(usuario_modelo.getCelular());
        TxtRuc.setText(usuario_modelo.getRuc());
        TxtCodigo_Paypal.setText(usuario_modelo.getCodigo_Paypal());

    }

    @Override
    public void onShowUserDataFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionDataSuccessful(String correo_electronico) {
        Correo_Electronico = correo_electronico;

    }
}
package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.ModificarUsuario;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.example.tacnafdcliente.presentador.ModificarUsuario_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ModificarUsuario_Vista extends Fragment implements ModificarUsuario.View{


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

    String ID_Usuario = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_usuario__vista, container, false);

        mPresenter=new ModificarUsuario_Presentador(this);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");

        mPresenter.GetSessionData(getActivity().getApplicationContext());

        TxtEmail = (EditText) view.findViewById(R.id.txtemail);
        TxtClave = (EditText) view.findViewById(R.id.txtclave);
        TxtNombre = (EditText) view.findViewById(R.id.txtnombre);
        TxtApellido = (EditText) view.findViewById(R.id.txtapellido);


        mPresenter.ShowUserData(mReference, Correo_Electronico);


        BtnModificar = (Button) view.findViewById(R.id.btnmodificar);
        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario_Modelo Usuario = new Usuario_Modelo(ID_Usuario,TxtNombre.getText().toString(),TxtApellido.getText().toString(),
                        TxtEmail.getText().toString(),TxtClave.getText().toString());
                mPresenter.UpdateUser(mReference, Usuario);
            }
        });

        return view;
    }


    @Override
    public void onUpdateUserSuccessful() {
        Toast.makeText(getActivity().getApplicationContext(),"Datos Actualizados Satisfactoriamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowUserDataSuccessful(Usuario_Modelo Usuario) {

        ID_Usuario = Usuario.getID_Usuario_Cliente();
        TxtEmail.setText(Usuario.getCorreo_Electronico());
        TxtClave.setText(Usuario.getContrasena());
        TxtNombre.setText(Usuario.getNombre());
        TxtApellido.setText(Usuario.getApellido());

    }

    @Override
    public void onShowUserDataFailure() {
        Toast.makeText(getActivity().getApplicationContext(),"Algo salio mal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSessionDataSuccessful(String Correo_Electronico) {
        this.Correo_Electronico = Correo_Electronico;

    }
}
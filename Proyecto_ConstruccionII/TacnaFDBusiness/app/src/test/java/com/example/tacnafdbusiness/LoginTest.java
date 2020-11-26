package com.example.tacnafdbusiness;

import android.util.Log;

import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.presentador.Login_Presentador;
import com.example.tacnafdbusiness.vista.Login_Vista;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    private Login_Presentador login_presentador;

    private DatabaseReference mReference;

    private FirebaseDatabase Firebase_Database;

    @Mock
    private Login.View login_vista;

    @Mock
    private Login.Interactor login_interactor;


    @Before
    public void setUp() throws Exception{



        login_presentador = new Login_Presentador(login_vista);
        mReference= FirebaseDatabase.getInstance().getReference().child("Usuario_Propietario");

    }

    @Test
    public void login(){

        login_presentador.LogIn(mReference,"marko.rivas98@gmail.com","123456789");

    }
}

package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Cliente_Modelo;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClienteTest {

    Faker faker = new Faker(new Locale("es"));

    Cliente_Modelo Cliente_Correcto;

    List<String> Cliente_List = Arrays.asList(faker.idNumber().valid(),faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), faker.name().lastName());

    @Before
    public void Create_Client_Correctly(){

        Cliente_Correcto = new Cliente_Modelo(Cliente_List.get(0), Cliente_List.get(1), Cliente_List.get(2), Cliente_List.get(3), Cliente_List.get(4));
    }

    @Test
    public void Create_Client(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Cliente_List);

            Cliente_Modelo Cliente = new Cliente_Modelo(Cliente_List.get(0), Cliente_List.get(1), Cliente_List.get(2), Cliente_List.get(3), Cliente_List.get(4));

            if(Cliente_Correcto.getID_Usuario_Cliente().equals(Cliente.getID_Usuario_Cliente()) && Cliente_Correcto.getEmail().equals(Cliente.getEmail()) &&
                    Cliente_Correcto.getContrasena().equals(Cliente.getContrasena()) && Cliente_Correcto.getNombre().equals(Cliente.getNombre()) &&
                    Cliente_Correcto.getApellido().equals(Cliente.getApellido()))
            {
                assertTrue(true);
            }
            else
            {
                assertFalse(false);
            }

        }
    }
}

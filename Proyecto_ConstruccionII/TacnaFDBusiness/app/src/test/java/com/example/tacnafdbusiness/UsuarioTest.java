package com.example.tacnafdbusiness;

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

public class UsuarioTest {

    Faker faker = new Faker(new Locale("es"));
    Usuario_Modelo Usuario_Correcto;

    List<String> Usuario_list = Arrays.asList(faker.idNumber().valid(),faker.name().firstName(),faker.name().lastName(),faker.internet().emailAddress(),faker.internet().password(),
            faker.phoneNumber().cellPhone(),faker.code().ean8());

    @Before
    public void Create_User_Correctly(){

        Usuario_Correcto = new Usuario_Modelo(Usuario_list.get(0), Usuario_list.get(1), Usuario_list.get(2), Usuario_list.get(3), Usuario_list.get(4), Usuario_list.get(5), Usuario_list.get(6));
    }

    @Test
    public void Create_User(){
        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Usuario_list);

            Usuario_Modelo Usuario = new Usuario_Modelo(Usuario_list.get(0), Usuario_list.get(1), Usuario_list.get(2), Usuario_list.get(3), Usuario_list.get(4), Usuario_list.get(5), Usuario_list.get(6));

            if(Usuario_Correcto.getID_Usuario().equals(Usuario.getID_Usuario()) && Usuario_Correcto.getNombre().equals(Usuario.getNombre()) &&
                    Usuario_Correcto.getApellido().equals(Usuario.getApellido()) && Usuario_Correcto.getCorreo_Electronico().equals(Usuario.getCorreo_Electronico()) &&
                    Usuario_Correcto.getContrasena().equals(Usuario.getContrasena()) && Usuario_Correcto.getCelular().equals(Usuario.getCelular()) &&
                    Usuario_Correcto.getRuc().equals(Usuario.getRuc()))
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

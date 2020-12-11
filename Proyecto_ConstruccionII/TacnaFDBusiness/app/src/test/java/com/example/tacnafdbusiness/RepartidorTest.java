package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
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

public class RepartidorTest {

    Faker faker = new Faker(new Locale("es"));
    Repartidor_Modelo Repartidor_Correcto;

    List<String> Repartidor_list = Arrays.asList(faker.idNumber().valid(), faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), faker.name().lastName(),
            faker.internet().url());

    @Before
    public void Create_DeliveryMan_Correctly(){

        Repartidor_Correcto = new Repartidor_Modelo(Repartidor_list.get(0),Repartidor_list.get(1),Repartidor_list.get(2),Repartidor_list.get(3),Repartidor_list.get(4), Repartidor_list.get(5));
    }

    @Test
    public void Create_DeliveryMan(){
        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Repartidor_list);

            Repartidor_Modelo Repartidor = new Repartidor_Modelo(Repartidor_list.get(0),Repartidor_list.get(1),Repartidor_list.get(2),Repartidor_list.get(3),Repartidor_list.get(4), Repartidor_list.get(5));

            if(Repartidor_Correcto.getID_Usuario_Repartidor().equals(Repartidor.getID_Usuario_Repartidor()) && Repartidor_Correcto.getEmail().equals(Repartidor.getEmail()) &&
                    Repartidor_Correcto.getContrasena().equals(Repartidor.getContrasena()) && Repartidor_Correcto.getNombre().equals(Repartidor.getNombre()) &&
                    Repartidor_Correcto.getApellido().equals(Repartidor.getApellido()) && Repartidor_Correcto.getUrl_Foto().equals(Repartidor.getUrl_Foto()))
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

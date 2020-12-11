package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResenaTest {
    Faker faker = new Faker(new Locale("es"));

    Resena_Modelo Resena_Correcto;

    List<String> Resena_List = Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid(), faker.idNumber().valid(), faker.name().firstName() + " " + faker.name().lastName(),
            faker.dune().quote(), String.valueOf(faker.random().nextDouble()), faker.backToTheFuture().date());


    @Before
    public void Create_Review_Correctly(){

        Resena_Correcto = new Resena_Modelo(Resena_List.get(0),Resena_List.get(1),Resena_List.get(2),Resena_List.get(3), Resena_List.get(4), Double.parseDouble(Resena_List.get(5)), Resena_List.get(6));
    }

    @Test
    public void Create_Review(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Resena_List);
            try {

                Resena_Modelo Resena = new Resena_Modelo(Resena_List.get(0),Resena_List.get(1),Resena_List.get(2),Resena_List.get(3), Resena_List.get(4), Double.parseDouble(Resena_List.get(5)), Resena_List.get(6));

                if(Resena_Correcto.getID_Resena().equals(Resena.getID_Resena()) && Resena_Correcto.getID_Usuario_Cliente().equals(Resena.getID_Usuario_Cliente()) &&
                        Resena_Correcto.getID_Establecimiento().equals(Resena.getID_Establecimiento()) && Resena_Correcto.getNombre_Cliente().equals(Resena.getNombre_Cliente()) &&
                        Resena_Correcto.getDescripcion().equals(Resena.getDescripcion()) && Resena_Correcto.getCalificacion() == Resena.getCalificacion() &&
                        Resena_Correcto.getFecha().equals(Resena.getFecha()))
                {
                    assertTrue(true);
                }
                else
                {
                    assertFalse(false);
                }


            }catch (Exception e){
                assertFalse(false);
            }
        }
    }


}

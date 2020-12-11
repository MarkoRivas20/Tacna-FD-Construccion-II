package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.modelo.RepartidorEstablecimiento_Modelo;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RepartidorEstablecimientoTest {

    Faker faker = new Faker(new Locale("es"));

    RepartidorEstablecimiento_Modelo Repartidor_Establecimiento_Correcto;

    List<String> Repartidor_Establecimiento_List = Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid(), faker.idNumber().valid());

    @Before
    public void Create_DeliveryMan_Establishment_Correctly(){

        Repartidor_Establecimiento_Correcto = new RepartidorEstablecimiento_Modelo(Repartidor_Establecimiento_List.get(0), Repartidor_Establecimiento_List.get(1), Repartidor_Establecimiento_List.get(2));
    }

    @Test
    public void Create_DeliveryMan_Establishment(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Repartidor_Establecimiento_List);
            try {

                RepartidorEstablecimiento_Modelo Repartidor_Establecimiento = new RepartidorEstablecimiento_Modelo(Repartidor_Establecimiento_List.get(0),
                        Repartidor_Establecimiento_List.get(1), Repartidor_Establecimiento_List.get(2));

                if(Repartidor_Establecimiento_Correcto.getID_Repartidor_Establecimiento().equals(Repartidor_Establecimiento.getID_Repartidor_Establecimiento())
                        && Repartidor_Establecimiento_Correcto.getID_Usuario_Repartidor().equals(Repartidor_Establecimiento.getID_Usuario_Repartidor()) &&
                        Repartidor_Establecimiento_Correcto.getID_Establecimiento().equals(Repartidor_Establecimiento.getID_Establecimiento()))
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

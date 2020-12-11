package com.example.tacnafdbusiness;


import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
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

public class ImagenEstablecimientoTest {

    Faker faker = new Faker(new Locale("es"));

    ImagenEstablecimiento_Modelo Imagen_Establecimiento_Correcto;

    List<String> Imagen_Establecimiento_List = Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid(), faker.internet().url());

    @Before
    public void Create_Image_Establishment_Correctly(){

        Imagen_Establecimiento_Correcto = new ImagenEstablecimiento_Modelo(Imagen_Establecimiento_List.get(0), Imagen_Establecimiento_List.get(1), Imagen_Establecimiento_List.get(2));
    }

    @Test
    public void Create_Image_Establishment(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Imagen_Establecimiento_List);
            try {

                ImagenEstablecimiento_Modelo Imagen_Establecimiento = new ImagenEstablecimiento_Modelo(Imagen_Establecimiento_List.get(0), Imagen_Establecimiento_List.get(1), Imagen_Establecimiento_List.get(2));

                if(Imagen_Establecimiento_Correcto.getID_Imagen_Establecimiento().equals(Imagen_Establecimiento.getID_Imagen_Establecimiento())
                        && Imagen_Establecimiento_Correcto.getID_Establecimiento().equals(Imagen_Establecimiento.getID_Establecimiento()) &&
                        Imagen_Establecimiento_Correcto.getUrl_Imagen().equals(Imagen_Establecimiento.getUrl_Imagen()))
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

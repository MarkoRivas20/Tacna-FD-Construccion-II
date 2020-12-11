package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CuponTest {

    Faker faker = new Faker(new Locale("es"));

    Cupon_Modelo Cupon_Correcto;

    List<String> Cupon_List = Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid(), faker.book().title(), faker.internet().url(), faker.dune().quote(), String.valueOf(faker.number().randomDigit()),
            faker.backToTheFuture().date(), faker.backToTheFuture().date(), faker.expression("Activo"));

    @Before
    public void Create_Coupon_Correctly(){


        Cupon_Correcto = new Cupon_Modelo(Cupon_List.get(0), Cupon_List.get(1), Cupon_List.get(2), Cupon_List.get(3), Cupon_List.get(4), Integer.parseInt(Cupon_List.get(5)),
                Cupon_List.get(6), Cupon_List.get(7), Cupon_List.get(8));
    }

    @Test
    public void Create_Coupon(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Cupon_List);

            try {

                Cupon_Modelo Cupon = new Cupon_Modelo(Cupon_List.get(0), Cupon_List.get(1), Cupon_List.get(2), Cupon_List.get(3), Cupon_List.get(4), Integer.parseInt(Cupon_List.get(5)),
                        Cupon_List.get(6), Cupon_List.get(7), Cupon_List.get(8));

                if(Cupon_Correcto.getId_Cupon().equals(Cupon.getId_Cupon()) && Cupon_Correcto.getId_Establecimiento().equals(Cupon.getId_Establecimiento()) &&
                        Cupon_Correcto.getTitulo().equals(Cupon.getTitulo()) && Cupon_Correcto.getUrl_Imagen().equals(Cupon.getUrl_Imagen()) &&
                        Cupon_Correcto.getDescripcion().equals(Cupon.getDescripcion()) && Cupon_Correcto.getPorcentaje_Descuento() == Cupon.getPorcentaje_Descuento() &&
                        Cupon_Correcto.getFecha_Fin().equals(Cupon.getFecha_Fin()) && Cupon_Correcto.getFecha_Inicio().equals(Cupon.getFecha_Inicio()) &&
                        Cupon_Correcto.getEstado().equals(Cupon.getEstado()))
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

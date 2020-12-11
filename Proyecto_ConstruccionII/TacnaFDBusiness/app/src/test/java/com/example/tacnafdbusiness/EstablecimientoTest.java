package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EstablecimientoTest {

    Faker faker = new Faker(new Locale("es"));

    Establecimiento_Modelo Establecimiento_Correcto;

    List<String> Establecimiento_List = Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid(), faker.company().name(), faker.address().state(),
            faker.expression("Categoria"), faker.address().streetAddress(), faker.phoneNumber().cellPhone(), faker.dune().quote(), String.valueOf(faker.number().randomDigit()),
            String.valueOf(faker.number().randomDouble(1,0,5)), faker.internet().url(), faker.internet().url(),
            faker.address().latitude() + "/" + faker.address().longitude(), faker.expression("Activo"), faker.crypto().sha256(),
            faker.crypto().md5() + "/" + faker.crypto().md5(), faker.internet().url());

    @Before
    public void Create_Establishment_Correctly(){

        Establecimiento_Correcto = new Establecimiento_Modelo(Establecimiento_List.get(0), Establecimiento_List.get(1), Establecimiento_List.get(2), Establecimiento_List.get(3), Establecimiento_List.get(4),
                Establecimiento_List.get(5), Establecimiento_List.get(6), Establecimiento_List.get(7), Integer.parseInt(Establecimiento_List.get(8)), Double.parseDouble(Establecimiento_List.get(9)),
                Establecimiento_List.get(10), Establecimiento_List.get(11), Establecimiento_List.get(12), Establecimiento_List.get(13), Establecimiento_List.get(14), Establecimiento_List.get(15),
                Establecimiento_List.get(16));
    }

    @Test
    public void Create_Establishment(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Establecimiento_List);
            try {

                Establecimiento_Modelo Establecimiento = new Establecimiento_Modelo(Establecimiento_List.get(0), Establecimiento_List.get(1), Establecimiento_List.get(2), Establecimiento_List.get(3), Establecimiento_List.get(4),
                        Establecimiento_List.get(5), Establecimiento_List.get(6), Establecimiento_List.get(7), Integer.parseInt(Establecimiento_List.get(8)), Double.parseDouble(Establecimiento_List.get(9)),
                        Establecimiento_List.get(10), Establecimiento_List.get(11), Establecimiento_List.get(12), Establecimiento_List.get(13), Establecimiento_List.get(14), Establecimiento_List.get(15),
                        Establecimiento_List.get(16));

                if(Establecimiento_Correcto.getID_Establecimiento().equals(Establecimiento.getID_Establecimiento()) && Establecimiento_Correcto.getID_Usuario_Propietario().equals(Establecimiento.getID_Usuario_Propietario()) &&
                        Establecimiento_Correcto.getNombre().equals(Establecimiento.getNombre()) && Establecimiento_Correcto.getDistrito().equals(Establecimiento.getDistrito()) &&
                        Establecimiento_Correcto.getCategoria().equals(Establecimiento.getCategoria()) && Establecimiento_Correcto.getDistrito().equals(Establecimiento.getDireccion()) &&
                        Establecimiento_Correcto.getTelefono().equals(Establecimiento.getTelefono()) && Establecimiento_Correcto.getDescripcion().equals(Establecimiento.getDescripcion()) &&
                        Establecimiento_Correcto.getTotalResenas() == Establecimiento.getTotalResenas() && Establecimiento_Correcto.getPuntuacion().equals(Establecimiento.getPuntuacion()) &&
                        Establecimiento_Correcto.getUrl_Imagen_Logo().equals(Establecimiento.getUrl_Imagen_Logo()) && Establecimiento_Correcto.getUrl_Imagen_Documento().equals(Establecimiento.getUrl_Imagen_Documento()) &&
                        Establecimiento_Correcto.getPuntoGeografico().equals(Establecimiento.getPuntoGeografico()) && Establecimiento_Correcto.getEstado().equals(Establecimiento.getEstado()) &&
                        Establecimiento_Correcto.getCodigo_Paypal().equals(Establecimiento.getCodigo_Paypal()) && Establecimiento_Correcto.getCodigo_Culqi().equals(Establecimiento.getCodigo_Culqi()) &&
                        Establecimiento_Correcto.getUrl_Qr().equals(Establecimiento.getUrl_Qr()))
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

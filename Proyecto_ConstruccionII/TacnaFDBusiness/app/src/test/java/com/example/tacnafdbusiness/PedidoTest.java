package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PedidoTest {

    Faker faker = new Faker(new Locale("es"));

    Pedido_Modelo Pedido_Correcto;

    List<String> Pedido_List = Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid(), faker.idNumber().valid(), faker.idNumber().valid(), faker.dune().quote(), faker.backToTheFuture().date(),
            faker.commerce().price(), faker.address().fullAddress(), faker.expression("Activo"), faker.address().latitude() + "/" + faker.address().longitude());

    @Before
    public void Create_Order_Correctly(){

        Pedido_Correcto = new Pedido_Modelo(Pedido_List.get(0), Pedido_List.get(1), Pedido_List.get(2), Pedido_List.get(3), Pedido_List.get(4), Pedido_List.get(5),
                Double.parseDouble(Pedido_List.get(6)), Pedido_List.get(7), Pedido_List.get(8), Pedido_List.get(9));
    }

    @Test
    public void Create_Order(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Pedido_List);
            try {

                Pedido_Modelo Pedido = new Pedido_Modelo(Pedido_List.get(0), Pedido_List.get(1), Pedido_List.get(2), Pedido_List.get(3), Pedido_List.get(4), Pedido_List.get(5),
                        Double.parseDouble(Pedido_List.get(6)), Pedido_List.get(7), Pedido_List.get(8), Pedido_List.get(9));

                if(Pedido_Correcto.getID_Pedido().equals(Pedido.getID_Pedido()) && Pedido_Correcto.getID_Establecimiento().equals(Pedido.getID_Establecimiento()) &&
                        Pedido_Correcto.getID_Usuario_Cliente().equals(Pedido.getID_Usuario_Cliente()) && Pedido_Correcto.getID_Repartidor().equals(Pedido.getID_Repartidor()) &&
                        Pedido_Correcto.getDescripcion().equals(Pedido.getDescripcion()) && Pedido_Correcto.getFecha().equals(Pedido.getFecha())  &&
                        Pedido_Correcto.getPrecio_Total().equals(Pedido.getPrecio_Total()) && Pedido_Correcto.getDireccion_Destino().equals(Pedido.getDireccion_Destino()) &&
                        Pedido_Correcto.getEstado().equals(Pedido.getEstado()) && Pedido_Correcto.getPunto_Geografico_Destino().equals(Pedido.getPunto_Geografico_Destino()))
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

package com.example.tacnafdbusiness;

import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemMenu_Test {

    Faker faker = new Faker(new Locale("es"));

    ItemMenu_Modelo Item_Menu_Correcto;

    List<String> Item_Menu_List = Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid(), faker.commerce().productName(), faker.commerce().price(),
            faker.dune().quote(), faker.company().url(), faker.expression("Activo"));

    @Before
    public void Create_Item_Menu_Correctly(){

        Item_Menu_Correcto = new ItemMenu_Modelo(Item_Menu_List.get(0), Item_Menu_List.get(1), Item_Menu_List.get(2), Item_Menu_List.get(3), Item_Menu_List.get(4), Item_Menu_List.get(5), Item_Menu_List.get(6));
    }

    @Test
    public void Create_Item_Menu(){

        for(int i = 0 ; i < 100; i++){

            Collections.shuffle(Item_Menu_List);
            try {

                ItemMenu_Modelo Item_Menu = new ItemMenu_Modelo(Item_Menu_List.get(0), Item_Menu_List.get(1), Item_Menu_List.get(2), Item_Menu_List.get(3), Item_Menu_List.get(4),
                        Item_Menu_List.get(5), Item_Menu_List.get(6));

                if(Item_Menu_Correcto.getID_Item_Menu().equals(Item_Menu.getID_Item_Menu()) && Item_Menu_Correcto.getID_Establecimiento().equals(Item_Menu.getID_Establecimiento()) &&
                        Item_Menu_Correcto.getNombre().equals(Item_Menu.getNombre()) && Item_Menu_Correcto.getPrecio().equals(Item_Menu.getPrecio()) &&
                        Item_Menu_Correcto.getDescripcion().equals(Item_Menu.getDescripcion()) && Item_Menu_Correcto.getUrl_Imagen() == Item_Menu.getUrl_Imagen() &&
                        Item_Menu_Correcto.getEstado().equals(Item_Menu.getEstado()))
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

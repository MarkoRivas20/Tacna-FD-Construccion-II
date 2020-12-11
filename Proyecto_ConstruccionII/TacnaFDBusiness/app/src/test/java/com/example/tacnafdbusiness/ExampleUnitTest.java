package com.example.tacnafdbusiness;

import android.view.View;

import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.modelo.Cliente_Modelo;
import com.example.tacnafdbusiness.modelo.Cupon_Modelo;
import com.example.tacnafdbusiness.modelo.Establecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.example.tacnafdbusiness.modelo.Pedido_Modelo;
import com.example.tacnafdbusiness.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.example.tacnafdbusiness.modelo.Resena_Modelo;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.example.tacnafdbusiness.presentador.Login_Presentador;
import com.github.javafaker.Crypto;
import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    Faker faker = new Faker(new Locale("es"));
    Boolean aBoolean = false;

    @Test
    public void create_successful_Client() {

        aBoolean = false;

        try {
            Cliente_Modelo Cliente = new Cliente_Modelo(faker.idNumber().valid(), faker.internet().emailAddress(), faker.internet().password(),faker.name().firstName(),faker.name().lastName());


            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }

        assertTrue(aBoolean);

    }

    @Test
    public void create_successful_Coupon() {

        aBoolean = false;

        try {

            Cupon_Modelo Cupon = new Cupon_Modelo(faker.idNumber().valid(), faker.idNumber().valid(), faker.book().title(), faker.company().url(), faker.dune().quote(), faker.number().randomDigit(),
                    faker.backToTheFuture().date(), faker.backToTheFuture().date(), faker.expression("Activo"));

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_User() {

        aBoolean = false;

        try {
            Usuario_Modelo Usuario = new Usuario_Modelo(faker.idNumber().valid() ,faker.name().firstName(),faker.name().lastName(),faker.internet().emailAddress(),faker.internet().password(),
                    faker.phoneNumber().cellPhone(),faker.code().ean8());

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_Review() {

        aBoolean = false;

        try {
            Resena_Modelo Resena = new Resena_Modelo(faker.idNumber().valid(),faker.idNumber().valid(),faker.idNumber().valid(),faker.name().firstName() + " " + faker.name().lastName(),
                    faker.dune().quote(), faker.random().nextDouble(), faker.backToTheFuture().date());

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_DeliveryMan_Establishment() {

        aBoolean = false;

        try {
            RepartidorEstablecimiento_Modelo Repartidor_Establecimiento = new RepartidorEstablecimiento_Modelo(faker.idNumber().valid(),faker.idNumber().valid(),faker.idNumber().valid());

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_DeliveryMan() {

        aBoolean = false;

        try {
            Repartidor_Modelo Repartidor = new Repartidor_Modelo(faker.idNumber().valid(), faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), faker.name().lastName(),
                    faker.internet().url());

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_Order() {

        aBoolean = false;

        try {
            Pedido_Modelo Pedido = new Pedido_Modelo(faker.idNumber().valid(), faker.idNumber().valid(), faker.idNumber().valid(), faker.idNumber().valid(), faker.dune().quote(), faker.backToTheFuture().date(),
                    Double.parseDouble(faker.commerce().price()), faker.address().fullAddress(), faker.expression("Activo"), faker.address().latitude() + "/" + faker.address().longitude());

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_ItemMenu() {

        aBoolean = false;

        try {
            ItemMenu_Modelo Item_Menu = new ItemMenu_Modelo(faker.idNumber().valid(), faker.idNumber().valid(), faker.commerce().productName(), faker.commerce().price(), faker.dune().quote(),
                    faker.company().url(), faker.expression("Activo"));

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_Image_Establishment() {

        aBoolean = false;

        try {
            ImagenEstablecimiento_Modelo Imagen_Establecimiento = new ImagenEstablecimiento_Modelo(faker.idNumber().valid(), faker.idNumber().valid(), faker.company().url());

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }

    @Test
    public void create_successful_Establishment() {

        aBoolean = false;

        try {
            Establecimiento_Modelo Establecimiento = new Establecimiento_Modelo(faker.idNumber().valid(), faker.idNumber().valid(), faker.company().name(), faker.address().state(),
                    faker.expression("Categoria"), faker.address().streetAddress(), faker.phoneNumber().cellPhone(), faker.dune().quote(), faker.number().randomDigit(),
                    faker.number().randomDouble(1,0,5), faker.internet().url(), faker.internet().url(),
                    faker.address().latitude() + "/" + faker.address().longitude(), faker.expression("Activo"), faker.crypto().sha256(),
                    faker.crypto().md5() + "/" + faker.crypto().md5(), faker.internet().url());

            aBoolean = true;
        }catch (Exception e){
            aBoolean = false;
        }
        assertTrue(aBoolean);
    }
}
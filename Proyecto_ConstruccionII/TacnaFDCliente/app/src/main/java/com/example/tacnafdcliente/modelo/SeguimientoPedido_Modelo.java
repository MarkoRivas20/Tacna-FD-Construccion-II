package com.example.tacnafdcliente.modelo;

public class SeguimientoPedido_Modelo {

    String ID_Pedido;
    String PuntoGeografico;

    public SeguimientoPedido_Modelo(String ID_Pedido, String puntoGeografico) {
        this.ID_Pedido = ID_Pedido;
        PuntoGeografico = puntoGeografico;
    }

    public SeguimientoPedido_Modelo(){

    }

    public String getID_Pedido() {
        return ID_Pedido;
    }

    public void setID_Pedido(String ID_Pedido) {
        this.ID_Pedido = ID_Pedido;
    }

    public String getPuntoGeografico() {
        return PuntoGeografico;
    }

    public void setPuntoGeografico(String puntoGeografico) {
        PuntoGeografico = puntoGeografico;
    }
}

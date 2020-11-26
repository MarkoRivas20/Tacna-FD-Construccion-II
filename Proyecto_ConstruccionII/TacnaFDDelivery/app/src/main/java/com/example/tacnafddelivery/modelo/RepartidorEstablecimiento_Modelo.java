package com.example.tacnafddelivery.modelo;

public class RepartidorEstablecimiento_Modelo {

    private String ID_Repartidor_Establecimiento;
    private String ID_Usuario_Repartidor;
    private String ID_Establecimiento;

    public RepartidorEstablecimiento_Modelo(String ID_Repartidor_Establecimiento, String ID_Usuario_Repartidor, String ID_Establecimiento) {
        this.ID_Repartidor_Establecimiento = ID_Repartidor_Establecimiento;
        this.ID_Usuario_Repartidor = ID_Usuario_Repartidor;
        this.ID_Establecimiento = ID_Establecimiento;
    }

    public RepartidorEstablecimiento_Modelo(String ID_Usuario_Repartidor, String ID_Establecimiento) {
        this.ID_Usuario_Repartidor = ID_Usuario_Repartidor;
        this.ID_Establecimiento = ID_Establecimiento;
    }

    public RepartidorEstablecimiento_Modelo(){

    }

    public String getID_Repartidor_Establecimiento() {
        return ID_Repartidor_Establecimiento;
    }

    public void setID_Repartidor_Establecimiento(String ID_Repartidor_Establecimiento) {
        this.ID_Repartidor_Establecimiento = ID_Repartidor_Establecimiento;
    }

    public String getID_Usuario_Repartidor() {
        return ID_Usuario_Repartidor;
    }

    public void setID_Usuario_Repartidor(String ID_Usuario_Repartidor) {
        this.ID_Usuario_Repartidor = ID_Usuario_Repartidor;
    }

    public String getID_Establecimiento() {
        return ID_Establecimiento;
    }

    public void setID_Establecimiento(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

}

package com.example.tacnafdbusiness.modelo;

import java.util.Date;

public class Resena_Modelo {

    private String ID_Resena;
    private String ID_Usuario_Cliente;
    private String ID_Establecimiento;
    private String Nombre_Cliente;
    private String Descripcion;
    private Double Calificacion;
    private String Fecha;

    public Resena_Modelo(String ID_Resena, String ID_Usuario_Cliente, String ID_Establecimiento, String nombre_Cliente, String descripcion, Double calificacion, String fecha) {
        this.ID_Resena = ID_Resena;
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
        this.ID_Establecimiento = ID_Establecimiento;
        Nombre_Cliente = nombre_Cliente;
        Descripcion = descripcion;
        Calificacion = calificacion;
        Fecha = fecha;
    }

    public Resena_Modelo(String ID_Usuario_Cliente, String ID_Establecimiento, String nombre_Cliente, String descripcion, Double calificacion, String fecha) {
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
        this.ID_Establecimiento = ID_Establecimiento;
        Nombre_Cliente = nombre_Cliente;
        Descripcion = descripcion;
        Calificacion = calificacion;
        Fecha = fecha;
    }

    public Resena_Modelo(){

    }

    public String getID_Resena() {
        return ID_Resena;
    }

    public void setID_Resena(String ID_Resena) {
        this.ID_Resena = ID_Resena;
    }

    public String getID_Usuario_Cliente() {
        return ID_Usuario_Cliente;
    }

    public void setID_Usuario_Cliente(String ID_Usuario_Cliente) {
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
    }

    public String getID_Establecimiento() {
        return ID_Establecimiento;
    }

    public void setID_Establecimiento(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Double getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(Double calificacion) {
        Calificacion = calificacion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNombre_Cliente() {
        return Nombre_Cliente;
    }

    public void setNombre_Cliente(String nombre_Cliente) {
        Nombre_Cliente = nombre_Cliente;
    }


}

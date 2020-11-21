package com.example.tacnafdcliente.modelo;

public class CuponUsuario_Modelo {

    private String ID_Cupon_Usuario;
    private String ID_Cupon;
    private String ID_Usuario_Cliente;
    private String ID_Establecimiento;
    private String Nombre_Establecimiento;
    private String Titulo_Cupon;
    private String Url_Imagen_Cupon;
    private String Fecha;
    private String Estado;

    public CuponUsuario_Modelo(String ID_Cupon_Usuario, String ID_Cupon, String ID_Usuario_Cliente, String fecha, String estado) {
        this.ID_Cupon_Usuario = ID_Cupon_Usuario;
        this.ID_Cupon = ID_Cupon;
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
        Fecha = fecha;
        Estado = estado;
    }

    public CuponUsuario_Modelo(String ID_Cupon, String ID_Usuario_Cliente, String fecha, String estado) {
        this.ID_Cupon = ID_Cupon;
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
        Fecha = fecha;
        Estado = estado;
    }

    public CuponUsuario_Modelo(){

    }

    public String getID_Cupon_Usuario() {
        return ID_Cupon_Usuario;
    }

    public void setID_Cupon_Usuario(String ID_Cupon_Usuario) {
        this.ID_Cupon_Usuario = ID_Cupon_Usuario;
    }

    public String getID_Cupon() {
        return ID_Cupon;
    }

    public void setID_Cupon(String ID_Cupon) {
        this.ID_Cupon = ID_Cupon;
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

    public String getNombre_Establecimiento() {
        return Nombre_Establecimiento;
    }

    public void setNombre_Establecimiento(String nombre_Establecimiento) {
        Nombre_Establecimiento = nombre_Establecimiento;
    }

    public String getTitulo_Cupon() {
        return Titulo_Cupon;
    }

    public void setTitulo_Cupon(String titulo_Cupon) {
        Titulo_Cupon = titulo_Cupon;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getUrl_Imagen_Cupon() {
        return Url_Imagen_Cupon;
    }

    public void setUrl_Imagen_Cupon(String url_Imagen_Cupon) {
        Url_Imagen_Cupon = url_Imagen_Cupon;
    }

}

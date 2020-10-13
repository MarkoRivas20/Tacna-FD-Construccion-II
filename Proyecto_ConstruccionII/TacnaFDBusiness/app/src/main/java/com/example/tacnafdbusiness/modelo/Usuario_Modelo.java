package com.example.tacnafdbusiness.modelo;

public class Usuario_Modelo {

    private String ID_Usuario;
    private String Nombre;
    private String Apellido;
    private String Correo_Electronico;
    private String Contrasena;
    private String Celular;
    private String Ruc;
    private String Codigo_Paypal;

    public Usuario_Modelo(String ID_Usuario, String nombre, String apellido, String correo_Electronico, String contrasena, String celular, String ruc, String codigo_Paypal) {
        this.ID_Usuario = ID_Usuario;
        Nombre = nombre;
        Apellido = apellido;
        Correo_Electronico = correo_Electronico;
        Contrasena = contrasena;
        Celular = celular;
        Ruc = ruc;
        Codigo_Paypal = codigo_Paypal;
    }

    public Usuario_Modelo(String nombre, String apellido, String correo_Electronico, String contrasena, String celular, String ruc, String codigo_Paypal) {
        Nombre = nombre;
        Apellido = apellido;
        Correo_Electronico = correo_Electronico;
        Contrasena = contrasena;
        Celular = celular;
        Ruc = ruc;
        Codigo_Paypal = codigo_Paypal;
    }

    public Usuario_Modelo(){

    }

    public String getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCorreo_Electronico() {
        return Correo_Electronico;
    }

    public void setCorreo_Electronico(String correo_Electronico) {
        Correo_Electronico = correo_Electronico;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String ruc) {
        Ruc = ruc;
    }

    public String getCodigo_Paypal() {
        return Codigo_Paypal;
    }

    public void setCodigo_Paypal(String codigo_Paypal) {
        Codigo_Paypal = codigo_Paypal;
    }




}

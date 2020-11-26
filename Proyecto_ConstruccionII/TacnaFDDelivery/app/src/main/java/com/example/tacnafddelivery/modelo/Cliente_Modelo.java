package com.example.tacnafddelivery.modelo;

public class Cliente_Modelo {

    private String ID_Usuario_Cliente;
    private String Email;
    private String Contrasena;
    private String Nombre;
    private String Apellido;

    public Cliente_Modelo(String email, String contrasena, String nombre, String apellido) {
        Email = email;
        Contrasena = contrasena;
        Nombre = nombre;
        Apellido = apellido;
    }

    public Cliente_Modelo(String ID_Usuario_Cliente, String email, String contrasena, String nombre, String apellido) {
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
        Email = email;
        Contrasena = contrasena;
        Nombre = nombre;
        Apellido = apellido;
    }

    public Cliente_Modelo(){

    }

    public String getID_Usuario_Cliente() {
        return ID_Usuario_Cliente;
    }

    public void setID_Usuario_Cliente(String ID_Usuario_Cliente) {
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
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

}

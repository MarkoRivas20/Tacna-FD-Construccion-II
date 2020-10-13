package com.example.tacnafdcliente.modelo;

public class Usuario_Modelo {

    private String ID_Usuario;
    private String Nombre;
    private String Apellido;
    private String Correo_Electronico;
    private String Contrasena;

    public Usuario_Modelo(String ID_Usuario, String nombre, String apellido, String correo_Electronico, String contrasena) {
        this.ID_Usuario = ID_Usuario;
        Nombre = nombre;
        Apellido = apellido;
        Correo_Electronico = correo_Electronico;
        Contrasena = contrasena;
    }

    public Usuario_Modelo(String nombre, String apellido, String correo_Electronico, String contrasena) {
        Nombre = nombre;
        Apellido = apellido;
        Correo_Electronico = correo_Electronico;
        Contrasena = contrasena;
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


}

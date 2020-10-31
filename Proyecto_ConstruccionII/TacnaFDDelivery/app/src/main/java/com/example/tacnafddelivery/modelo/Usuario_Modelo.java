package com.example.tacnafddelivery.modelo;

public class Usuario_Modelo {

    private String ID_Usuario_Repartidor;
    private String Nombre;
    private String Apellido;
    private String Correo_Electronico;
    private String Contrasena;
    private String Url_Foto;

    public Usuario_Modelo(String ID_Usuario_Repartidor, String nombre, String apellido, String correo_Electronico, String contrasena, String url_Foto) {
        this.ID_Usuario_Repartidor = ID_Usuario_Repartidor;
        Nombre = nombre;
        Apellido = apellido;
        Correo_Electronico = correo_Electronico;
        Contrasena = contrasena;
        Url_Foto = url_Foto;
    }

    public Usuario_Modelo(String nombre, String apellido, String correo_Electronico, String contrasena, String url_Foto) {
        Nombre = nombre;
        Apellido = apellido;
        Correo_Electronico = correo_Electronico;
        Contrasena = contrasena;
        Url_Foto = url_Foto;
    }

    public Usuario_Modelo(){

    }

    public String getID_Usuario_Repartidor() {
        return ID_Usuario_Repartidor;
    }

    public void setID_Usuario_Repartidor(String ID_Usuario_Repartidor) {
        this.ID_Usuario_Repartidor = ID_Usuario_Repartidor;
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

    public String getUrl_Foto() {
        return Url_Foto;
    }

    public void setUrl_Foto(String url_Foto) {
        Url_Foto = url_Foto;
    }



}

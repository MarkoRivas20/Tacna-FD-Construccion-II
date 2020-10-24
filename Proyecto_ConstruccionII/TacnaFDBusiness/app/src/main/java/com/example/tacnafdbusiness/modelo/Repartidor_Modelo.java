package com.example.tacnafdbusiness.modelo;

public class Repartidor_Modelo {

    private String ID_Usuario_Repartidor;
    private String Email;
    private String Contrasena;
    private String Nombre;
    private String Apellido;
    private String Url_Foto;

    public Repartidor_Modelo(String ID_Usuario_Repartidor, String email, String contrasena, String nombre, String apellido, String url_Foto) {
        this.ID_Usuario_Repartidor = ID_Usuario_Repartidor;
        Email = email;
        Contrasena = contrasena;
        Nombre = nombre;
        Apellido = apellido;
        Url_Foto = url_Foto;
    }

    public Repartidor_Modelo(String email, String contrasena, String nombre, String apellido, String url_Foto) {
        Email = email;
        Contrasena = contrasena;
        Nombre = nombre;
        Apellido = apellido;
        Url_Foto = url_Foto;
    }

    public Repartidor_Modelo(){

    }

    public String getID_Usuario_Repartidor() {
        return ID_Usuario_Repartidor;
    }

    public void setID_Usuario_Repartidor(String ID_Usuario_Repartidor) {
        this.ID_Usuario_Repartidor = ID_Usuario_Repartidor;
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

    public String getUrl_Foto() {
        return Url_Foto;
    }

    public void setUrl_Foto(String url_Foto) {
        Url_Foto = url_Foto;
    }


}

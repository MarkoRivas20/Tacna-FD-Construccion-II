package com.example.tacnafddelivery.modelo;

public class Establecimiento_Modelo {


    private String ID_Establecimiento;
    private String ID_Usuario_Propietario;
    private String Nombre;
    private String Distrito;
    private String Categoria;
    private String Direccion;
    private String Telefono;
    private String Descripcion;
    private int TotalResenas;
    private Double Puntuacion;
    private String Url_Imagen_Logo;
    private String Url_Imagen_Documento;
    private String PuntoGeografico;
    private String Estado;
    private String Codigo_Paypal;
    private String Codigo_Culqi;
    private String Url_Qr;

    public Establecimiento_Modelo(String ID_Establecimiento, String ID_Usuario_Propietario, String nombre, String distrito, String categoria,
                                  String direccion, String telefono, String descripcion, int totalResenas, Double puntuacion, String url_Imagen_Logo,
                                  String url_Imagen_Documento, String puntoGeografico, String estado, String codigo_Paypal, String codigo_Culqi, String url_Qr) {
        this.ID_Establecimiento = ID_Establecimiento;
        this.ID_Usuario_Propietario = ID_Usuario_Propietario;
        Nombre = nombre;
        Distrito = distrito;
        Categoria = categoria;
        Direccion = direccion;
        Telefono = telefono;
        Descripcion = descripcion;
        TotalResenas = totalResenas;
        Puntuacion = puntuacion;
        Url_Imagen_Logo = url_Imagen_Logo;
        Url_Imagen_Documento = url_Imagen_Documento;
        PuntoGeografico = puntoGeografico;
        Estado = estado;
        Codigo_Paypal = codigo_Paypal;
        Codigo_Culqi = codigo_Culqi;
        Url_Qr = url_Qr;
    }

    public Establecimiento_Modelo(String ID_Establecimiento, String ID_Usuario_Propietario, String nombre, String distrito, String categoria,
                                  String direccion, String telefono, String descripcion, int totalResenas, Double puntuacion, String url_Imagen_Logo,
                                  String url_Imagen_Documento, String puntoGeografico, String estado) {
        this.ID_Establecimiento = ID_Establecimiento;
        this.ID_Usuario_Propietario = ID_Usuario_Propietario;
        Nombre = nombre;
        Distrito = distrito;
        Categoria = categoria;
        Direccion = direccion;
        Telefono = telefono;
        Descripcion = descripcion;
        TotalResenas = totalResenas;
        Puntuacion = puntuacion;
        Url_Imagen_Logo = url_Imagen_Logo;
        Url_Imagen_Documento = url_Imagen_Documento;
        PuntoGeografico = puntoGeografico;
        Estado = estado;
    }


    public Establecimiento_Modelo(){

    }

    public String getID_Establecimiento() {
        return ID_Establecimiento;
    }

    public void setID_Establecimiento(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

    public String getID_Usuario_Propietario() {
        return ID_Usuario_Propietario;
    }

    public void setID_Usuario_Propietario(String ID_Usuario_Propietario) {
        this.ID_Usuario_Propietario = ID_Usuario_Propietario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getTotalResenas() {
        return TotalResenas;
    }

    public void setTotalResenas(int totalResenas) {
        TotalResenas = totalResenas;
    }

    public Double getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        Puntuacion = puntuacion;
    }

    public String getUrl_Imagen_Logo() {
        return Url_Imagen_Logo;
    }

    public void setUrl_Imagen_Logo(String url_Imagen_Logo) {
        Url_Imagen_Logo = url_Imagen_Logo;
    }

    public String getUrl_Imagen_Documento() {
        return Url_Imagen_Documento;
    }

    public void setUrl_Imagen_Documento(String url_Imagen_Documento) {
        Url_Imagen_Documento = url_Imagen_Documento;
    }

    public String getPuntoGeografico() {
        return PuntoGeografico;
    }

    public void setPuntoGeografico(String puntoGeografico) {
        PuntoGeografico = puntoGeografico;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getCodigo_Paypal() {
        return Codigo_Paypal;
    }

    public void setCodigo_Paypal(String codigo_Paypal) {
        Codigo_Paypal = codigo_Paypal;
    }

    public String getCodigo_Culqi() {
        return Codigo_Culqi;
    }

    public void setCodigo_Culqi(String codigo_Culqi) {
        Codigo_Culqi = codigo_Culqi;
    }

    public String getUrl_Qr() {
        return Url_Qr;
    }

    public void setUrl_Qr(String url_Qr) {
        Url_Qr = url_Qr;
    }

}

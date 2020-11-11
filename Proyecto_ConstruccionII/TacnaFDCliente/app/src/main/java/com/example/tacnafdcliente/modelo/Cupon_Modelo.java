package com.example.tacnafdcliente.modelo;

public class Cupon_Modelo {


    private String Id_Cupon;
    private String Id_Establecimiento;
    private String Titulo;
    private String Url_Imagen;
    private String Descripcion;
    private int Porcentaje_Descuento;
    private String Fecha_Inicio;
    private String Fecha_Fin;
    private String Estado;

    public Cupon_Modelo(String id_Cupon, String id_Establecimiento, String titulo, String url_Imagen, String descripcion,
                        int porcentaje_Descuento, String fecha_Inicio, String fecha_Fin, String estado) {
        Id_Cupon = id_Cupon;
        Id_Establecimiento = id_Establecimiento;
        Titulo = titulo;
        Url_Imagen = url_Imagen;
        Descripcion = descripcion;
        Porcentaje_Descuento = porcentaje_Descuento;
        Fecha_Inicio = fecha_Inicio;
        Fecha_Fin = fecha_Fin;
        Estado = estado;
    }

    public Cupon_Modelo(String id_Establecimiento, String titulo, String url_Imagen, String descripcion, int porcentaje_Descuento,
                        String fecha_Inicio, String fecha_Fin, String estado) {
        Id_Establecimiento = id_Establecimiento;
        Titulo = titulo;
        Url_Imagen = url_Imagen;
        Descripcion = descripcion;
        Porcentaje_Descuento = porcentaje_Descuento;
        Fecha_Inicio = fecha_Inicio;
        Fecha_Fin = fecha_Fin;
        Estado = estado;
    }

    public Cupon_Modelo(){

    }

    public String getId_Cupon() {
        return Id_Cupon;
    }

    public void setId_Cupon(String id_Cupon) {
        Id_Cupon = id_Cupon;
    }

    public String getId_Establecimiento() {
        return Id_Establecimiento;
    }

    public void setId_Establecimiento(String id_Establecimiento) {
        Id_Establecimiento = id_Establecimiento;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getUrl_Imagen() {
        return Url_Imagen;
    }

    public void setUrl_Imagen(String url_Imagen) {
        Url_Imagen = url_Imagen;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public void setFecha_Inicio(String fecha_Inicio) {
        Fecha_Inicio = fecha_Inicio;
    }

    public String getFecha_Fin() {
        return Fecha_Fin;
    }

    public void setFecha_Fin(String fecha_Fin) {
        Fecha_Fin = fecha_Fin;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getPorcentaje_Descuento() {
        return Porcentaje_Descuento;
    }

    public void setPorcentaje_Descuento(int porcentaje_Descuento) {
        Porcentaje_Descuento = porcentaje_Descuento;
    }

}

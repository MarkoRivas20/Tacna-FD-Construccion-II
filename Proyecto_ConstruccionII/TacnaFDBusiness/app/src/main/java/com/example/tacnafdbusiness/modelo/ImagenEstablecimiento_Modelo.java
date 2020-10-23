package com.example.tacnafdbusiness.modelo;

public class ImagenEstablecimiento_Modelo {

    private String ID_Imagen_Establecimiento;
    private String ID_Establecimiento;
    private String Url_Imagen;

    public ImagenEstablecimiento_Modelo(String ID_Imagen_Establecimiento, String ID_Establecimiento, String url_Imagen) {
        this.ID_Imagen_Establecimiento = ID_Imagen_Establecimiento;
        this.ID_Establecimiento = ID_Establecimiento;
        Url_Imagen = url_Imagen;
    }

    public ImagenEstablecimiento_Modelo(String ID_Establecimiento, String url_Imagen) {
        this.ID_Establecimiento = ID_Establecimiento;
        Url_Imagen = url_Imagen;
    }

    public ImagenEstablecimiento_Modelo(){

    }

    public String getID_Imagen_Establecimiento() {
        return ID_Imagen_Establecimiento;
    }

    public void setID_Imagen_Establecimiento(String ID_Imagen_Establecimiento) {
        this.ID_Imagen_Establecimiento = ID_Imagen_Establecimiento;
    }

    public String getID_Establecimiento() {
        return ID_Establecimiento;
    }

    public void setID_Establecimiento(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

    public String getUrl_Imagen() {
        return Url_Imagen;
    }

    public void setUrl_Imagen(String url_Imagen) {
        Url_Imagen = url_Imagen;
    }



}

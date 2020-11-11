package com.example.tacnafdcliente.modelo;

public class ItemMenu_Modelo {

    private String ID_Item_Menu;
    private String ID_Establecimiento;
    private String Nombre;
    private String Precio;
    private String Descripcion;
    private String Url_Imagen;
    private String Estado;

    public ItemMenu_Modelo(String ID_Item_Menu, String ID_Establecimiento, String nombre, String precio, String descripcion, String url_Imagen, String estado) {
        this.ID_Item_Menu = ID_Item_Menu;
        this.ID_Establecimiento = ID_Establecimiento;
        Nombre = nombre;
        Precio = precio;
        Descripcion = descripcion;
        Url_Imagen = url_Imagen;
        Estado = estado;
    }

    public ItemMenu_Modelo(String ID_Establecimiento, String nombre, String precio, String descripcion, String url_Imagen, String estado) {
        this.ID_Establecimiento = ID_Establecimiento;
        Nombre = nombre;
        Precio = precio;
        Descripcion = descripcion;
        Url_Imagen = url_Imagen;
        Estado = estado;
    }

    public ItemMenu_Modelo(){

    }


    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getID_Item_Menu() {
        return ID_Item_Menu;
    }

    public void setID_Item_Menu(String ID_Item_Menu) {
        this.ID_Item_Menu = ID_Item_Menu;
    }

    public String getID_Establecimiento() {
        return ID_Establecimiento;
    }

    public void setID_Establecimiento(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUrl_Imagen() {
        return Url_Imagen;
    }

    public void setUrl_Imagen(String url_Imagen) {
        Url_Imagen = url_Imagen;
    }

}

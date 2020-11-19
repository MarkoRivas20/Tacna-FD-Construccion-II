package com.example.tacnafdcliente.modelo;

public class DetallePedido_Modelo {

    private String ID_Item_Menu;
    private String Nombre_Item_Menu;
    private int Cantidad;
    private Double Precio_Unitario;
    private Double Precio_Total;
    private String Url_Imagen;

    public DetallePedido_Modelo(String ID_Item_Menu, String nombre_Item_Menu, int cantidad, Double precio_Unitario, Double precio_Total, String url_Imagen) {
        this.ID_Item_Menu = ID_Item_Menu;
        Nombre_Item_Menu = nombre_Item_Menu;
        Cantidad = cantidad;
        Precio_Unitario = precio_Unitario;
        Precio_Total = precio_Total;
        Url_Imagen = url_Imagen;
    }

    public DetallePedido_Modelo(){

    }

    public String getID_Item_Menu() {
        return ID_Item_Menu;
    }

    public void setID_Item_Menu(String ID_Item_Menu) {
        this.ID_Item_Menu = ID_Item_Menu;
    }

    public String getNombre_Item_Menu() {
        return Nombre_Item_Menu;
    }

    public void setNombre_Item_Menu(String nombre_Item_Menu) {
        Nombre_Item_Menu = nombre_Item_Menu;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Double getPrecio_Unitario() {
        return Precio_Unitario;
    }

    public void setPrecio_Unitario(Double precio_Unitario) {
        Precio_Unitario = precio_Unitario;
    }

    public Double getPrecio_Total() {
        return Precio_Total;
    }

    public void setPrecio_Total(Double precio_Total) {
        Precio_Total = precio_Total;
    }

    public String getUrl_Imagen() {
        return Url_Imagen;
    }

    public void setUrl_Imagen(String url_Imagen) {
        Url_Imagen = url_Imagen;
    }
}

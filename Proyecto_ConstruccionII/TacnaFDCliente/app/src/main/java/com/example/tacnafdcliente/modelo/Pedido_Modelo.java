package com.example.tacnafdcliente.modelo;

public class Pedido_Modelo {

    private String ID_Pedido;
    private String ID_Establecimiento;
    private String ID_Usuario_Cliente;
    private String ID_Repartidor;
    private String Nombre_Establecimiento;
    private String Descripcion;
    private String Fecha;
    private Double Precio_Total;
    private String Direccion_Destino;
    private String Estado;
    private String Metodo_Pago;
    private String Punto_Geografico_Destino;

    public Pedido_Modelo(String ID_Pedido, String ID_Establecimiento, String ID_Usuario_Cliente, String descripcion, String fecha,
                         Double precio_Total, String direccion_Destino, String estado, String metodo_Pago, String punto_Geografico_Destino) {
        this.ID_Pedido = ID_Pedido;
        this.ID_Establecimiento = ID_Establecimiento;
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
        Descripcion = descripcion;
        Fecha = fecha;
        Precio_Total = precio_Total;
        Direccion_Destino = direccion_Destino;
        Estado = estado;
        Metodo_Pago = metodo_Pago;
        Punto_Geografico_Destino = punto_Geografico_Destino;
    }

    public Pedido_Modelo(String ID_Pedido, String ID_Establecimiento, String ID_Usuario_Cliente, String descripcion, String fecha,
                         Double precio_Total, String direccion_Destino, String estado, String punto_Geografico_Destino) {
        this.ID_Pedido = ID_Pedido;
        this.ID_Establecimiento = ID_Establecimiento;
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
        Descripcion = descripcion;
        Fecha = fecha;
        Precio_Total = precio_Total;
        Direccion_Destino = direccion_Destino;
        Estado = estado;
        Punto_Geografico_Destino = punto_Geografico_Destino;
    }

    public Pedido_Modelo(){

    }

    public String getID_Pedido() {
        return ID_Pedido;
    }

    public void setID_Pedido(String ID_Pedido) {
        this.ID_Pedido = ID_Pedido;
    }

    public String getID_Establecimiento() {
        return ID_Establecimiento;
    }

    public void setID_Establecimiento(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }

    public String getID_Usuario_Cliente() {
        return ID_Usuario_Cliente;
    }

    public void setID_Usuario_Cliente(String ID_Usuario_Cliente) {
        this.ID_Usuario_Cliente = ID_Usuario_Cliente;
    }

    public String getID_Repartidor() {
        return ID_Repartidor;
    }

    public void setID_Repartidor(String ID_Repartidor) {
        this.ID_Repartidor = ID_Repartidor;
    }

    public String getNombre_Establecimiento() {
        return Nombre_Establecimiento;
    }

    public void setNombre_Establecimiento(String nombre_Establecimiento) {
        Nombre_Establecimiento = nombre_Establecimiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Double getPrecio_Total() {
        return Precio_Total;
    }

    public void setPrecio_Total(Double precio_Total) {
        Precio_Total = precio_Total;
    }

    public String getDireccion_Destino() {
        return Direccion_Destino;
    }

    public void setDireccion_Destino(String direccion_Destino) {
        Direccion_Destino = direccion_Destino;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getMetodo_Pago() {
        return Metodo_Pago;
    }

    public void setMetodo_Pago(String metodo_Pago) {
        Metodo_Pago = metodo_Pago;
    }

    public String getPunto_Geografico_Destino() {
        return Punto_Geografico_Destino;
    }

    public void setPunto_Geografico_Destino(String punto_Geografico_Destino) {
        Punto_Geografico_Destino = punto_Geografico_Destino;
    }

}

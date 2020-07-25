package com.examenparcial.entidad;

public class Detalle {
    private Integer idProducto;
    private double precio;
    private Integer cantidad;

    public Detalle(){
    }

    public Detalle(Integer idProducto, double precio, Integer cantidad) {
        this.idProducto = idProducto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

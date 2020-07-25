package com.examenparcial.entidad;

public class Cliente {
    private Integer idCliente;
    private String nombres;
    private String apellidos;

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String nombres, String apellidos) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    @Override
    public String toString(){
        return apellidos + " " + nombres;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}

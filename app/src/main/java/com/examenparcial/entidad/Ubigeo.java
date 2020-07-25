package com.examenparcial.entidad;

public class Ubigeo {
    private int idUbigeo;
    private String departamento;
    private String provincia;
    private String distrito;

    public Ubigeo() {
    }

    public Ubigeo(int idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public Ubigeo(int idUbigeo, String departamento, String provincia, String distrito) {
        this.idUbigeo = idUbigeo;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
    }

    @Override
    public String toString(){
        return distrito;
    }

    public int getIdUbigeo() {
        return idUbigeo;
    }

    public void setIdUbigeo(int idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}

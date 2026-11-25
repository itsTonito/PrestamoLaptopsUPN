package com.upn.prestamolaptops.model;

public class Laptop {

    private final String codigo;
    private final String marcaModelo;
    private EstadoLaptop estado;
    private String codigoEstudianteAsignado;

    public Laptop(String codigo, String marcaModelo, EstadoLaptop estado, String codigoEstudianteAsignado) {
        this.codigo = codigo;
        this.marcaModelo = marcaModelo;
        this.estado = estado;
        this.codigoEstudianteAsignado = codigoEstudianteAsignado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMarcaModelo() {
        return marcaModelo;
    }

    public EstadoLaptop getEstado() {
        return estado;
    }

    public void setEstado(EstadoLaptop estado) {
        this.estado = estado;
    }

    public String getCodigoEstudianteAsignado() {
        return codigoEstudianteAsignado;
    }

    public void setCodigoEstudianteAsignado(String codigoEstudianteAsignado) {
        this.codigoEstudianteAsignado = codigoEstudianteAsignado;
    }

    public boolean estaDisponible() {
        return this.estado == EstadoLaptop.DISPONIBLE;
    }

    @Override
    public String toString() {
        return codigo + " \u2014 " + marcaModelo;
    }
}

package com.upn.prestamolaptops.model;

public enum EstadoLaptop {
    DISPONIBLE("Disponible"),
    PRESTADA("Prestada");

    private final String etiqueta;

    EstadoLaptop(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}

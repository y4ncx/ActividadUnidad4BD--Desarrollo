package com.y4ncx.actividad.domain;

public class Profesor {
    private String dni;
    private String nombreCompleto;
    private String domicilio;

    public Profesor(String dni, String nombreCompleto, String domicilio) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.domicilio = domicilio;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "dni='" + dni + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }
}

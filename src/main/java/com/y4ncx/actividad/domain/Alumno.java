package com.y4ncx.actividad.domain;

public class Alumno {

    private String dni;
    private String nombreCompleto;
    private int numMatricula;

    // Constructor
    public Alumno(String dni, String nombreCompleto, int numMatricula) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.numMatricula = numMatricula;
    }

    // Getters
    public String getDni() {
        return dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getNumMatricula() {
        return numMatricula;
    }

    // Setters
    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    // toString (opcional)
    @Override
    public String toString() {
        return "Alumno{" +
                "dni='" + dni + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", numMatricula=" + numMatricula +
                '}';
    }
}

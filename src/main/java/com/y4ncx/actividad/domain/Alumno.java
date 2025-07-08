package com.y4ncx.actividad.domain;

public class Alumno {

    private String dni;
    private String nombreCompleto;
    private int numMatricula;


    public Alumno(String dni, String nombreCompleto, int numMatricula) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.numMatricula = numMatricula;
    }


    public String getDni() {
        return dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getNumMatricula() {
        return numMatricula;
    }


    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }


    @Override
    public String toString() {
        return "Alumno{" +
                "dni='" + dni + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", numMatricula=" + numMatricula +
                '}';
    }
}

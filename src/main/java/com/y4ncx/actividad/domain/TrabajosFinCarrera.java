package com.y4ncx.actividad.domain;

import java.time.LocalDate;

public class TrabajosFinCarrera {
    private int numOrden;
    private String tema;
    private LocalDate fechaInicio;
    private String alumnoRealiza;

    // Constructor por defecto
    public TrabajosFinCarrera() {
    }

    // Constructor con parámetros
    public TrabajosFinCarrera(int numOrden, String tema, LocalDate fechaInicio, String alumnoRealiza) {
        this.numOrden = numOrden;
        this.tema = tema;
        this.fechaInicio = fechaInicio;
        this.alumnoRealiza = alumnoRealiza;
    }

    // Getters y Setters
    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getAlumnoRealiza() {
        return alumnoRealiza;
    }

    public void setAlumnoRealiza(String alumnoRealiza) {
        this.alumnoRealiza = alumnoRealiza;
    }

}

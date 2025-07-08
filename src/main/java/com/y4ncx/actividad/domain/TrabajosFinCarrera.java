package com.y4ncx.actividad.domain;

import java.time.LocalDate;

public class TrabajosFinCarrera {
    private int numOrden;
    private String tema;
    private LocalDate fechaInicio;
    private int alumnoRealiza;
    private int profesorDirige;

    public TrabajosFinCarrera(int numOrden, String tema, LocalDate fechaInicio, int alumnoRealiza, int profesorDirige) {
        this.numOrden = numOrden;
        this.tema = tema;
        this.fechaInicio = fechaInicio;
        this.alumnoRealiza = alumnoRealiza;
        this.profesorDirige = profesorDirige;
    }

    public int getNumOrden() { return numOrden; }
    public String getTema() { return tema; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public int getAlumnoRealiza() { return alumnoRealiza; }
    public int getProfesorDirige() { return profesorDirige; }

    public void setNumOrden(int numOrden) { this.numOrden = numOrden; }
    public void setTema(String tema) { this.tema = tema; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setAlumnoRealiza(int alumnoRealiza) { this.alumnoRealiza = alumnoRealiza; }
    public void setProfesorDirige(int profesorDirige) { this.profesorDirige = profesorDirige; }
}

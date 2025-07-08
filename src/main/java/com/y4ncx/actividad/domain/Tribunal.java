package com.y4ncx.actividad.domain;

public class Tribunal {

    private int numTribunal;
    private String lugarExamen;
    private int cantidadProfesores;
    private String alumnoPresente;
    private String tfcDefendido;
    private String fechaDefensa;

    public Tribunal(int numTribunal, String lugarExamen, int cantidadProfesores, String alumnoPresente, String tfcDefendido, String fechaDefensa) {
        this.numTribunal = numTribunal;
        this.lugarExamen = lugarExamen;
        this.cantidadProfesores = cantidadProfesores;
        this.alumnoPresente = alumnoPresente;
        this.tfcDefendido = tfcDefendido;
        this.fechaDefensa = fechaDefensa;
    }

    public int getNumTribunal() {
        return numTribunal;
    }

    public void setNumTribunal(int numTribunal) {
        this.numTribunal = numTribunal;
    }

    public String getLugarExamen() {
        return lugarExamen;
    }

    public void setLugarExamen(String lugarExamen) {
        this.lugarExamen = lugarExamen;
    }

    public int getCantidadProfesores() {
        return cantidadProfesores;
    }

    public void setCantidadProfesores(int cantidadProfesores) {
        this.cantidadProfesores = cantidadProfesores;
    }

    public String getAlumnoPresente() {
        return alumnoPresente;
    }

    public void setAlumnoPresente(String alumnoPresente) {
        this.alumnoPresente = alumnoPresente;
    }

    public String getTfcDefendido() {
        return tfcDefendido;
    }

    public void setTfcDefendido(String tfcDefendido) {
        this.tfcDefendido = tfcDefendido;
    }

    public String getFechaDefensa() {
        return fechaDefensa;
    }

    public void setFechaDefensa(String fechaDefensa) {
        this.fechaDefensa = fechaDefensa;
    }

    @Override
    public String toString() {
        return "Tribunal{" +
                "numTribunal=" + numTribunal +
                ", lugarExamen='" + lugarExamen + '\'' +
                ", cantidadProfesores=" + cantidadProfesores +
                ", alumnoPresente='" + alumnoPresente + '\'' +
                ", tfcDefendido='" + tfcDefendido + '\'' +
                ", fechaDefensa='" + fechaDefensa + '\'' +
                '}';
    }
}

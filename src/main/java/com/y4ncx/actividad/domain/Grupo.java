package com.y4ncx.actividad.domain;

public class Grupo {
    private int numGrupo;
    private String nombreGrupo;
    private int numComponentes;
    private String fechaIncorporacion;

    public Grupo(int numGrupo, String nombreGrupo, int numComponentes, String fechaIncorporacion) {
        this.numGrupo = numGrupo;
        this.nombreGrupo = nombreGrupo;
        this.numComponentes = numComponentes;
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public int getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(int numGrupo) {
        this.numGrupo = numGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public int getNumComponentes() {
        return numComponentes;
    }

    public void setNumComponentes(int numComponentes) {
        this.numComponentes = numComponentes;
    }

    public String getFechaIncorporacion() {
        return fechaIncorporacion;
    }

    public void setFechaIncorporacion(String fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "numGrupo=" + numGrupo +
                ", nombreGrupo='" + nombreGrupo + '\'' +
                ", numComponentes=" + numComponentes +
                ", fechaIncorporacion='" + fechaIncorporacion + '\'' +
                '}';
    }
}


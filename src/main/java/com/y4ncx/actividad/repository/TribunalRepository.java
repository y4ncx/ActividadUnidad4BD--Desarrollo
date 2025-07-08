package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Tribunal;

import java.util.List;

public interface TribunalRepository {


    List<Tribunal> listarTodos();

    void actualizar(Tribunal tribunal);

    void agregar(Tribunal tribunal);


    List<Tribunal> masDeTresProfesores();
    List<Tribunal> tribunalesConAlumnoPresente();
    List<Tribunal> tribunalesConAlumnoAusente();
    List<Tribunal> tribunalesConDefensa();
    List<Tribunal> tribunalesEntreFechas(String desde, String hasta);
    List<Tribunal> defensasEnFecha(String fecha);

    void eliminar(int id);
}

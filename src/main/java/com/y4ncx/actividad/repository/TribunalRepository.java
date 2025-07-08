package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Tribunal;

import java.util.List;

public interface TribunalRepository {

    // Operaciones CRUD básicas (si las necesitas implementar en el futuro)
    List<Tribunal> listarTodos();

    void actualizar(Tribunal tribunal);

    void agregar(Tribunal tribunal);

    // Consultas específicas disponibles actualmente
    List<Tribunal> masDeTresProfesores();
    List<Tribunal> tribunalesConAlumnoPresente();
    List<Tribunal> tribunalesConAlumnoAusente();
    List<Tribunal> tribunalesConDefensa();
    List<Tribunal> tribunalesEntreFechas(String desde, String hasta);
    List<Tribunal> defensasEnFecha(String fecha);

    void eliminar(int id);
}

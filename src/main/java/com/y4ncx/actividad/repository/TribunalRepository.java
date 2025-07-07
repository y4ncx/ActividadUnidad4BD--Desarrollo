package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Tribunal;
import java.util.List;

public interface TribunalRepository {
    List<Tribunal> listarTodos();
    List<Tribunal> tribunalesConAlumnoAusente();
    List<Tribunal> defensasEnFecha(String fecha);
}

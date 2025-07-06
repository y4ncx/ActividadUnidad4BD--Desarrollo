package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Alumno;
import java.util.List;

public interface AlumnoRepository {
    void guardar(Alumno alumno);
    List<Alumno> listarTodos();
    void eliminar(String dni);
    void actualizar(Alumno alumno);
}
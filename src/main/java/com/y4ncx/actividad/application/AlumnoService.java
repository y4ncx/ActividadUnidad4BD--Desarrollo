package com.y4ncx.actividad.application;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;
import com.y4ncx.actividad.repository.AlumnoRepository;

import java.util.List;

public class AlumnoService {
    private final AlumnoRepository repo = new AlumnoRepositoryImpl();

    public List<Alumno> listarTodos() {
        return repo.listarTodos();
    }

    public void guardar(Alumno alumno) {
        repo.guardar(alumno);
    }

    public void actualizar(Alumno alumno) {
        repo.actualizar(alumno);
    }

    public void eliminar(String dni) {
        repo.eliminar(dni);
    }

    public List<Alumno> alumnosConGrupoInvestigacion() {
        return repo.alumnosConGrupoInvestigacion();
    }
}


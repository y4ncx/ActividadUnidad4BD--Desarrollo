package com.y4ncx.actividad.application;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.infrastructure.ProfesorRepositoryImpl;
import com.y4ncx.actividad.repository.ProfesorRepository;

import java.util.List;

public class ProfesorService {
    private final ProfesorRepository repo = new ProfesorRepositoryImpl();

    public void agregar(Profesor profesor) {
        repo.agregar(profesor);
    }

    public void actualizar(Profesor profesor) {
        repo.actualizar(profesor);
    }

    public void eliminar(String dni) {
        repo.eliminar(dni);
    }

    public List<Profesor> listarTodos() {
        return repo.listarTodos();
    }

    public int contarColaboraciones(String dni) {
        return repo.contarColaboracionesPorProfesor(dni);
    }
}

package com.y4ncx.actividad.application;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.infrastructure.TribunalRepositoryImpl;
import com.y4ncx.actividad.repository.TribunalRepository;

import java.util.List;

public class TribunalService {
    private final TribunalRepository repo = new TribunalRepositoryImpl();

    public void agregar(Tribunal tribunal) {
        repo.agregar(tribunal);
    }

    public void actualizar(Tribunal tribunal) {
        repo.actualizar(tribunal);
    }

    public void eliminar(int id) {
        repo.eliminar(id);
    }

    public List<Tribunal> listarTodos() {
        return repo.listarTodos();
    }
}

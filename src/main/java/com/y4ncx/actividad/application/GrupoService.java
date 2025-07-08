package com.y4ncx.actividad.application;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.infrastructure.GrupoRepositoryImpl;
import com.y4ncx.actividad.repository.GrupoRepository;

import java.util.List;

public class GrupoService {
    private final GrupoRepository repo = new GrupoRepositoryImpl();

    public void agregar(Grupo grupo) {
        repo.agregar(grupo);
    }

    public void actualizar(Grupo grupo) {
        repo.actualizar(grupo);
    }

    public void eliminar(int numGrupo) {
        repo.eliminar(numGrupo);
    }

    public List<Grupo> listarTodos() {
        return repo.listarTodos();
    }
}

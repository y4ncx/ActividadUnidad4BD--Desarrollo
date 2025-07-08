package com.y4ncx.actividad.application;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.infrastructure.TrabajosFinCarreraRepositoryImpl;
import com.y4ncx.actividad.repository.TrabajosFinCarreraRepository;

import java.util.List;

public class TrabajosFinCarreraService {
    private final TrabajosFinCarreraRepository repo = new TrabajosFinCarreraRepositoryImpl();

    public void agregar(TrabajosFinCarrera tfc) {
        repo.agregar(tfc);
    }

    public void actualizar(TrabajosFinCarrera tfc) {
        repo.actualizar(tfc);
    }

    public void eliminar(int id) {
        repo.eliminar(id);
    }

    public List<TrabajosFinCarrera> listarTodos() {
        return repo.listarTodos();
    }
}

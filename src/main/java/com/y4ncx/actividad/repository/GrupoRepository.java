package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Grupo;
import java.util.List;

public interface GrupoRepository {
    List<Grupo> listarTodos();
    Grupo buscarPorNumero(int numGrupo);
    List<Grupo> gruposConMasDeNIntegrantes(int n);
}

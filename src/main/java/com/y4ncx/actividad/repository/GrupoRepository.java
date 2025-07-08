package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Grupo;
import java.util.List;

public interface GrupoRepository {

    void actualizar(Grupo grupo);


    void agregar(Grupo grupo);
    List<Grupo> listarTodos();
    Grupo buscarPorNumero(int numGrupo);
    List<Grupo> gruposConMasDeNIntegrantes(int n);

    List<Grupo> gruposConMasDe(int cantidad);

    List<Grupo> incorporadosEnAnio(String anio);

    List<Grupo> ordenadosPorIntegrantesDesc();

    void eliminar(int numGrupo);

}

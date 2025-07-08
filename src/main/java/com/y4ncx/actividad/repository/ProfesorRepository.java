package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Profesor;

import java.util.List;

public interface ProfesorRepository {
    void agregar(Profesor profesor);        // 👈 Agregar nuevo profesor
    void actualizar(Profesor profesor);     // 👈 Actualizar datos de un profesor

    List<Profesor> listarTodos();
    List<Profesor> conMasDeUnTFC();
    List<String[]> profesoresQueColaboraronEnTFCDirigidoPorOtros();
    List<String[]> cantidadColaboracionesPorProfesor();
    List<String[]> profesoresConColaboraciones();
    List<String[]> profesoresSinDireccionTFCConColaboracion();
    List<String[]> profesoresConColaboracionesAjeno();
    int contarColaboracionesPorProfesor(String dni);

    void eliminar(String dni);
}


package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Alumno;
import java.util.List;

public interface AlumnoRepository {
    void guardar(Alumno alumno);
    List<Alumno> listarTodos();
    void eliminar(String dni);
    void actualizar(Alumno alumno);

    // Métodos personalizados agregados:
    Alumno buscarPorMatricula(int matricula);
    List<Alumno> alumnosSinTFC();
    List<Alumno> alumnosConGrupoInvestigacion();
    String grupoDeAlumno(String dni);

    List<Alumno> alumnosDefendieronEntreFechas(String desde, String hasta);
    List<String[]> alumnosConColaboracionExterna();
    List<String[]> alumnosConGrupoYFecha();
    List<Alumno> alumnosDefendieronEntre(String desde, String hasta);
    List<String[]> alumnosConDirector();
    List<Alumno> alumnosConColaboracion();
    List<String[]> alumnosPorTribunal();
    List<Alumno> alumnosSinTribunal();
}

package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;

import java.util.List;

public interface TrabajosFinCarreraRepository {

    void agregar(TrabajosFinCarrera nuevo);
    void actualizar(TrabajosFinCarrera tfc);
    void eliminar(int numOrden);
    List<TrabajosFinCarrera> listarTodos();
    List<TrabajosFinCarrera> listarEnCurso();
    TrabajosFinCarrera obtenerPorAlumno(String dniAlumno);
    List<TrabajosFinCarrera> defendidosUltimos6Meses();
    List<String> temasConColaboracion();
    List<TrabajosFinCarrera> listarPorAnio(int anio);
    List<String[]> cantidadPorProfesor();
    int cantidadPorTribunal(String idTribunal);
    TrabajosFinCarrera defensaMasReciente();
    List<TrabajosFinCarrera> defendidosPorTribunalYFecha(String idTribunal, String fecha);





}

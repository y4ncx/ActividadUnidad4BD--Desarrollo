package com.y4ncx.actividad.repository;

import com.y4ncx.actividad.domain.Profesor;
import java.util.List;

public interface ProfesorRepository {
    List<Profesor> listarTodos();
    List<Profesor> conMasDeUnTFC();
    List<String[]> profesoresConColaboraciones();
}

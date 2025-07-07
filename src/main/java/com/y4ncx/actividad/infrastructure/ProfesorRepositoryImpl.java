package com.y4ncx.actividad.infrastructure;


import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.repository.ProfesorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorRepositoryImpl implements ProfesorRepository {

    @Override
    public List<Profesor> listarTodos() {
        List<Profesor> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM profesores";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Profesor(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getString("direccion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Profesor> conMasDeUnTFC() {
        List<Profesor> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT p.* FROM profesores p " +
                    "JOIN tfc t ON p.dni = t.dni_profesor " +
                    "GROUP BY p.dni " +
                    "HAVING COUNT(t.id) > 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Profesor(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getString("direccion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> profesoresConColaboraciones() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT DISTINCT p.nombre_completo AS profesor, c.tipo_colaboracion " +
                    "FROM profesores p " +
                    "JOIN colaboraciones c ON p.dni = c.dni_profesor";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{rs.getString("profesor"), rs.getString("tipo_colaboracion")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

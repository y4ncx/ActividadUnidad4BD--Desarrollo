package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.repository.TribunalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TribunalRepositoryImpl implements TribunalRepository {

    @Override
    public List<Tribunal> listarTodos() {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(rs.getInt("id"),
                        rs.getString("lugar_examen"),
                        rs.getInt("cantidad_profesores"),
                        rs.getString("alumno_presente"),
                        rs.getString("tfc_defendido"),
                        rs.getString("fecha_defensa")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Tribunal> tribunalesConAlumnoAusente() {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales WHERE alumno_presente = false";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(rs.getInt("id"),
                        rs.getString("lugar_examen"),
                        rs.getInt("cantidad_profesores"),
                        rs.getString("alumno_presente"),
                        rs.getString("tfc_defendido"),
                        rs.getString("fecha_defensa")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Tribunal> defensasEnFecha(String fecha) {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales WHERE fecha_defensa = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fecha);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(rs.getInt("id"),
                        rs.getString("lugar_examen"),
                        rs.getInt("cantidad_profesores"),
                        rs.getString("alumno_presente"),
                        rs.getString("tfc_defendido"),
                        rs.getString("fecha_defensa")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

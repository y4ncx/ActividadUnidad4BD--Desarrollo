package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.repository.AlumnoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoRepositoryImpl implements AlumnoRepository {


    @Override
    public void guardar(Alumno alumno) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO alumnos (dni, nombre_completo, num_matricula) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, alumno.getDni());
            stmt.setString(2, alumno.getNombreCompleto());
            stmt.setInt(3, alumno.getNumMatricula());
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código MySQL para entrada duplicada
                throw new RuntimeException("El DNI ya existe.");
            } else {
                throw new RuntimeException("Error al guardar alumno: " + e.getMessage());
            }
        }
    }


    @Override
    public List<Alumno> listarTodos() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM alumnos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")
                );
                lista.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void eliminar(String dni) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "DELETE FROM alumnos WHERE dni = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Alumno alumno) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "UPDATE alumnos SET nombre_completo = ?, num_matricula = ? WHERE dni = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, alumno.getNombreCompleto());
            stmt.setInt(2, alumno.getNumMatricula());
            stmt.setString(3, alumno.getDni());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

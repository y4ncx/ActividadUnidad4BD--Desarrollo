package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.repository.TribunalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TribunalRepositoryImpl implements TribunalRepository {

    @Override
    public void eliminar(int numTribunal) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "DELETE FROM tribunales WHERE num_tribunal = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, numTribunal);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actualizar(Tribunal tribunal) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "UPDATE tribunales SET lugar_examen = ?, cantidad_profesores = ?, alumno_presente = ?, tfc_defendido = ?, fecha_defensa = ? WHERE num_tribunal = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tribunal.getLugarExamen());
            stmt.setInt(2, tribunal.getCantidadProfesores());
            stmt.setString(3, tribunal.getAlumnoPresente());
            stmt.setString(4, tribunal.getTfcDefendido());
            stmt.setString(5, tribunal.getFechaDefensa());
            stmt.setInt(6, tribunal.getNumTribunal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void agregar(Tribunal tribunal) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO tribunales (num_tribunal, lugar_examen, cantidad_profesores, alumno_presente, tfc_defendido, fecha_defensa) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tribunal.getNumTribunal());
            stmt.setString(2, tribunal.getLugarExamen());
            stmt.setInt(3, tribunal.getCantidadProfesores());
            stmt.setString(4, tribunal.getAlumnoPresente());
            stmt.setString(5, tribunal.getTfcDefendido());
            stmt.setString(6, tribunal.getFechaDefensa());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public List<Tribunal> masDeTresProfesores() {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales WHERE cantidad_profesores > 3";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(
                        rs.getInt("num_tribunal"),
                        rs.getString("lugar_examen"),
                        rs.getInt("cantidad_profesores"),
                        rs.getString("alumno_presente"),
                        rs.getString("tfc_defendido"),
                        rs.getString("fecha_defensa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Tribunal> tribunalesConAlumnoPresente() {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales WHERE alumno_presente IS NOT NULL AND alumno_presente <> ''";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(
                        rs.getInt("num_tribunal"),
                        rs.getString("lugar_examen"),
                        rs.getInt("cantidad_profesores"),
                        rs.getString("alumno_presente"),
                        rs.getString("tfc_defendido"),
                        rs.getString("fecha_defensa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Tribunal> tribunalesConDefensa() {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales WHERE tfc_defendido IS NOT NULL AND tfc_defendido <> ''";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(
                        rs.getInt("num_tribunal"),
                        rs.getString("lugar_examen"),
                        rs.getInt("cantidad_profesores"),
                        rs.getString("alumno_presente"),
                        rs.getString("tfc_defendido"),
                        rs.getString("fecha_defensa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Tribunal> tribunalesEntreFechas(String desde, String hasta) {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales WHERE fecha_defensa BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desde);
            stmt.setString(2, hasta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(
                        rs.getInt("num_tribunal"),
                        rs.getString("lugar_examen"),
                        rs.getInt("cantidad_profesores"),
                        rs.getString("alumno_presente"),
                        rs.getString("tfc_defendido"),
                        rs.getString("fecha_defensa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    @Override
    public List<Tribunal> listarTodos() {
        List<Tribunal> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tribunales";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tribunal(rs.getInt("num_tribunal"),
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

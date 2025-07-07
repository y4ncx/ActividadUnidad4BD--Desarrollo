package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.repository.TrabajosFinCarreraRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrabajosFinCarreraRepositoryImpl implements TrabajosFinCarreraRepository {

    @Override
    public List<TrabajosFinCarrera> listarTodos() {
        List<TrabajosFinCarrera> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tfc";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<TrabajosFinCarrera> listarEnCurso() {
        List<TrabajosFinCarrera> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tfc WHERE defendido = FALSE OR defendido IS NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public TrabajosFinCarrera obtenerPorAlumno(String dniAlumno) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tfc WHERE dni_alumno = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dniAlumno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrabajosFinCarrera> defendidosUltimos6Meses() {
        List<TrabajosFinCarrera> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tfc WHERE defendido = TRUE AND fecha_defensa >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String> temasConColaboracion() {
        List<String> temas = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT DISTINCT t.tema FROM tfc t JOIN colaboraciones c ON t.num_orden = c.num_tfc";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                temas.add(rs.getString("tema"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temas;
    }

    @Override
    public List<TrabajosFinCarrera> listarPorAnio(int anio) {
        List<TrabajosFinCarrera> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tfc WHERE YEAR(fecha_inicio) = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, anio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> cantidadPorProfesor() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT p.nombre, COUNT(t.num_orden) AS cantidad FROM tfc t " +
                    "JOIN profesores p ON t.dni_profesor = p.dni GROUP BY p.nombre";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(new String[]{rs.getString("nombre"), String.valueOf(rs.getInt("cantidad"))});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public int cantidadPorTribunal(String idTribunal) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT COUNT(*) FROM tfc WHERE id_tribunal = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idTribunal);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public TrabajosFinCarrera defensaMasReciente() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tfc WHERE defendido = TRUE ORDER BY fecha_defensa DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrabajosFinCarrera> defendidosPorTribunalYFecha(String idTribunal, String fecha) {
        List<TrabajosFinCarrera> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM tfc WHERE id_tribunal = ? AND fecha_defensa = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idTribunal);
            stmt.setString(2, fecha);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método privado para mapear el resultSet
    private TrabajosFinCarrera mapear(ResultSet rs) throws SQLException {
        return new TrabajosFinCarrera(
                rs.getInt("num_orden"),
                rs.getString("tema"),
                rs.getDate("fecha_inicio").toLocalDate(),
                rs.getString("dni_alumno")
        );
    }
}

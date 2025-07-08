package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.repository.TrabajosFinCarreraRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrabajosFinCarreraRepositoryImpl implements TrabajosFinCarreraRepository {

    @Override
    public void agregar(TrabajosFinCarrera nuevo) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO trabajo_fin_carrera (num_orden, tema, fecha_inicio, alumno_realiza, profesor_dirige) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nuevo.getNumOrden());
            stmt.setString(2, nuevo.getTema());
            stmt.setDate(3, java.sql.Date.valueOf(nuevo.getFechaInicio()));
            stmt.setInt(4, nuevo.getAlumnoRealiza());
            stmt.setInt(5, nuevo.getProfesorDirige());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private TrabajosFinCarrera mapear(ResultSet rs) throws SQLException {
        return new TrabajosFinCarrera(
                rs.getInt("num_orden"),
                rs.getString("tema"),
                rs.getDate("fecha_inicio").toLocalDate(),
                rs.getInt("alumno_realiza"),
                rs.getInt("profesor_dirige")
        );
    }


    @Override
    public List<TrabajosFinCarrera> listarTodos() {
        List<TrabajosFinCarrera> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM trabajo_fin_carrera";
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
            String sql = "SELECT * FROM trabajo_fin_carrera " +
                    "WHERE num_orden NOT IN (" +
                    "  SELECT tfc_defendido FROM tribunales " +
                    "  WHERE tfc_defendido IS NOT NULL AND tfc_defendido <> ''" +
                    ")";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new TrabajosFinCarrera(
                        rs.getInt("num_orden"),
                        rs.getString("tema"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getInt("alumno_realiza"),
                        rs.getInt("profesor_dirige")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }



    @Override
    public TrabajosFinCarrera obtenerPorAlumno(String dniAlumno) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM trabajo_fin_carrera WHERE alumno_realiza = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(dniAlumno));
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
            String sql = "SELECT t.* FROM trabajo_fin_carrera t " +
                    "JOIN tribunales tr ON t.num_orden = tr.tfc_defendido " +
                    "WHERE tr.fecha_defensa >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)";
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
            String sql = "SELECT DISTINCT t.tema FROM trabajo_fin_carrera t " +
                    "JOIN colaboraciones c ON t.num_orden = c.id_tfc";
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
            String sql = "SELECT * FROM trabajo_fin_carrera WHERE YEAR(fecha_inicio) = ?";
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
        List<String[]> datos = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT p.nombre_completo, COUNT(t.num_orden) AS cantidad " +
                    "FROM profesores p JOIN trabajo_fin_carrera t ON p.dni = t.profesor_dirige " +
                    "GROUP BY p.dni";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                datos.add(new String[]{rs.getString("nombre_completo"), rs.getString("cantidad")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }

    @Override
    public int cantidadPorTribunal(String idTribunal) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT COUNT(*) AS total FROM tribunales WHERE num_tribunal = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idTribunal));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public TrabajosFinCarrera defensaMasReciente() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT t.* FROM trabajo_fin_carrera t " +
                    "JOIN tribunales tr ON t.num_orden = tr.tfc_defendido " +
                    "ORDER BY tr.fecha_defensa DESC LIMIT 1";
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
            String sql = "SELECT t.* FROM trabajo_fin_carrera t " +
                    "JOIN tribunales tr ON t.num_orden = tr.tfc_defendido " +
                    "WHERE tr.num_tribunal = ? AND tr.fecha_defensa = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idTribunal));
            stmt.setDate(2, Date.valueOf(fecha));
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
    public void actualizar(TrabajosFinCarrera tfc) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "UPDATE trabajo_fin_carrera SET tema = ?, fecha_inicio = ?, alumno_realiza = ?, profesor_dirige = ? WHERE num_orden = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tfc.getTema());
            stmt.setDate(2, Date.valueOf(tfc.getFechaInicio()));
            stmt.setInt(3, tfc.getAlumnoRealiza());
            stmt.setInt(4, tfc.getProfesorDirige());
            stmt.setInt(5, tfc.getNumOrden());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void eliminar(int numOrden) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "DELETE FROM trabajo_fin_carrera WHERE num_orden = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, numOrden);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

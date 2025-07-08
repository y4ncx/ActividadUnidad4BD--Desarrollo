package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.repository.ProfesorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorRepositoryImpl implements ProfesorRepository {

    @Override
    public void eliminar(String dni) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "DELETE FROM profesores WHERE dni = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void agregar(Profesor profesor) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO profesores (dni, nombre_completo, domicilio) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, profesor.getDni());
            stmt.setString(2, profesor.getNombreCompleto());
            stmt.setString(3, profesor.getDomicilio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Profesor profesor) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "UPDATE profesores SET nombre_completo = ?, domicilio = ? WHERE dni = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, profesor.getNombreCompleto());
            stmt.setString(2, profesor.getDomicilio());
            stmt.setString(3, profesor.getDni());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Profesor> listarTodos() {
        List<Profesor> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM profesores";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Profesor(
                        rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getString("domicilio")
                ));
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
            String sql = "SELECT p.dni, p.nombre_completo, p.domicilio " +
                    "FROM profesores p " +
                    "JOIN trabajo_fin_carrera tfc ON p.dni = tfc.profesor_dirige " +
                    "GROUP BY p.dni, p.nombre_completo, p.domicilio " +
                    "HAVING COUNT(tfc.num_orden) > 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Profesor(
                        rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getString("domicilio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> profesoresQueColaboraronEnTFCDirigidoPorOtros() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT DISTINCT p.nombre_completo AS colaborador, t.tema " +
                    "FROM colaboraciones c " +
                    "JOIN profesores p ON p.dni = c.dni_profesor " +
                    "JOIN trabajo_fin_carrera t ON c.id_tfc = t.num_orden " +
                    "WHERE c.dni_profesor != t.profesor_dirige";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("colaborador"),
                        rs.getString("tema")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> cantidadColaboracionesPorProfesor() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT p.nombre_completo, COUNT(*) AS cantidad " +
                    "FROM colaboraciones c " +
                    "JOIN profesores p ON p.dni = c.dni_profesor " +
                    "GROUP BY p.nombre_completo";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("nombre_completo"),
                        rs.getString("cantidad")
                });
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
            String sql = "SELECT p.nombre_completo, c.tipo_colaboracion " +
                    "FROM colaboraciones c " +
                    "JOIN profesores p ON p.dni = c.dni_profesor";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("nombre_completo"),
                        rs.getString("tipo_colaboracion")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> profesoresConColaboracionesAjeno() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT p.nombre_completo, c.tipo_colaboracion " +
                    "FROM colaboraciones c " +
                    "JOIN profesores p ON p.dni = c.dni_profesor";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("nombre_completo"),
                        rs.getString("tipo_colaboracion")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> profesoresSinDireccionTFCConColaboracion() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT DISTINCT p.dni, p.nombre_completo, p.domicilio " +
                    "FROM profesores p " +
                    "JOIN colaboraciones c ON p.dni = c.dni_profesor " +
                    "WHERE p.dni NOT IN (SELECT profesor_dirige FROM trabajo_fin_carrera)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[] {
                        rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getString("domicilio")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public int contarColaboracionesPorProfesor(String dni) {
        int cantidad = 0;
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT COUNT(*) AS total FROM colaboraciones WHERE dni_profesor = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidad;
    }
}

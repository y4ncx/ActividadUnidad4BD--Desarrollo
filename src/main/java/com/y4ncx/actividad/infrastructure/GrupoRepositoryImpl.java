package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.repository.GrupoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoRepositoryImpl implements GrupoRepository {

    @Override
    public void actualizar(Grupo grupo) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "UPDATE grupos SET nombre_grupo = ?, num_componentes = ?, fecha_incorporacion = ? WHERE num_grupo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, grupo.getNombreGrupo());
            stmt.setInt(2, grupo.getNumComponentes());
            stmt.setString(3, grupo.getFechaIncorporacion());
            stmt.setInt(4, grupo.getNumGrupo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregar(Grupo grupo) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO grupos (num_grupo, nombre_grupo, num_componentes, fecha_incorporacion) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, grupo.getNumGrupo());
            stmt.setString(2, grupo.getNombreGrupo());
            stmt.setInt(3, grupo.getNumComponentes());
            stmt.setString(4, grupo.getFechaIncorporacion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int numGrupo) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "DELETE FROM grupos WHERE num_grupo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, numGrupo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<Grupo> gruposConMasDe(int cantidad) {
        List<Grupo> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM grupos WHERE num_componentes > ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cantidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Grupo(
                        rs.getInt("num_grupo"),
                        rs.getString("nombre_grupo"),
                        rs.getInt("num_componentes"),
                        rs.getString("fecha_incorporacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    @Override
    public List<Grupo> incorporadosEnAnio(String anio) {
        List<Grupo> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM grupos WHERE fecha_incorporacion LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, anio + "%"); // e.g., "2023%"
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Grupo(
                        rs.getInt("num_grupo"),
                        rs.getString("nombre_grupo"),
                        rs.getInt("num_componentes"),
                        rs.getString("fecha_incorporacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Grupo> ordenadosPorIntegrantesDesc() {
        List<Grupo> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM grupos ORDER BY num_componentes DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Grupo(
                        rs.getInt("num_grupo"),
                        rs.getString("nombre_grupo"),
                        rs.getInt("num_componentes"),
                        rs.getString("fecha_incorporacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Grupo> listarTodos() {
        List<Grupo> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM grupos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Grupo(rs.getInt("num_grupo"), rs.getString("nombre_grupo"),
                        rs.getInt("num_componentes"), rs.getString("fecha_incorporacion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Grupo buscarPorNumero(int numGrupo) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM grupos WHERE num_grupo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, numGrupo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Grupo(rs.getInt("num_grupo"), rs.getString("nombre_grupo"),
                        rs.getInt("num_componentes"), rs.getString("fecha_incorporacion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Grupo> gruposConMasDeNIntegrantes(int n) {
        List<Grupo> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM grupos_investigacion WHERE num_componentes > ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, n);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Grupo(rs.getInt("num_grupo"), rs.getString("nombre_grupo"),
                        rs.getInt("num_componentes"), rs.getString("fecha_incorporacion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.repository.GrupoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoRepositoryImpl implements GrupoRepository {

    @Override
    public List<Grupo> listarTodos() {
        List<Grupo> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM grupos_investigacion";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Grupo(rs.getInt("id"), rs.getString("nombre"),
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
            String sql = "SELECT * FROM grupos_investigacion WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, numGrupo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Grupo(rs.getInt("id"), rs.getString("nombre"),
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
                lista.add(new Grupo(rs.getInt("id"), rs.getString("nombre"),
                        rs.getInt("num_componentes"), rs.getString("fecha_incorporacion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

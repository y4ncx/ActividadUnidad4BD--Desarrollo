package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.repository.AlumnoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoRepositoryImpl implements AlumnoRepository {

    // 1. Guardar un alumno
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
            if (e.getErrorCode() == 1062) {
                throw new RuntimeException("El DNI ya existe.");
            } else {
                throw new RuntimeException("Error al guardar alumno: " + e.getMessage());
            }
        }
    }

    // 2. Listar todos los alumnos
    @Override
    public List<Alumno> listarTodos() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT dni, nombre_completo, num_matricula FROM alumnos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"), rs.getString("nombre_completo"), rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. Eliminar un alumno por DNI
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

    // 4. Actualizar datos de un alumno
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

    // 5. Buscar alumno por matrícula
    @Override
    public Alumno buscarPorMatricula(int matricula) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM alumnos WHERE num_matricula = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Alumno(rs.getString("dni"), rs.getString("nombre_completo"), rs.getInt("num_matricula"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 6. Alumnos que no han defendido TFC
    @Override
    public List<Alumno> alumnosSinTFC() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.* FROM alumnos a " +
                    "LEFT JOIN tfc t ON a.num_matricula = t.num_matricula " +
                    "WHERE t.defendido = false OR t.defendido IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"), rs.getString("nombre_completo"), rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 7. Alumnos que tienen grupo de investigación
    @Override
    public List<Alumno> alumnosConGrupoInvestigacion() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.* FROM alumnos a JOIN alumnos_grupos ag ON a.num_matricula = ag.num_matricula";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"), rs.getString("nombre_completo"), rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 8. Nombre del grupo al que pertenece un alumno
    @Override
    public String grupoDeAlumno(int matricula) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT g.nombre FROM grupos_investigacion g " +
                    "JOIN alumnos_grupos ag ON g.id = ag.id_grupo " +
                    "WHERE ag.num_matricula = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("nombre");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No asignado";
    }

    // 9. Alumnos que defendieron entre fechas
    @Override
    public List<Alumno> alumnosDefendieronEntre(String desde, String hasta) {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.* FROM alumnos a " +
                    "JOIN tfc t ON a.num_matricula = t.num_matricula " +
                    "WHERE t.defendido = true AND t.fecha_defensa BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desde);
            stmt.setString(2, hasta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"), rs.getString("nombre_completo"), rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 10. Alumnos con su director
    @Override
    public List<String[]> alumnosConDirector() {
        List<String[]> datos = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.nombre_completo AS alumno, p.nombre_completo AS profesor " +
                    "FROM alumnos a JOIN tfc t ON a.num_matricula = t.num_matricula " +
                    "JOIN profesores p ON t.dni_profesor = p.dni";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                datos.add(new String[]{rs.getString("alumno"), rs.getString("profesor")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }

    // 11. Alumnos con colaboración externa
    @Override
    public List<Alumno> alumnosConColaboracion() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT DISTINCT a.* FROM alumnos a " +
                    "JOIN tfc t ON a.num_matricula = t.num_matricula " +
                    "JOIN colaboraciones c ON t.id = c.id_tfc";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"), rs.getString("nombre_completo"), rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 12. Alumnos por tribunal
    @Override
    public List<String[]> alumnosPorTribunal() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.nombre_completo, t.id AS tribunal " +
                    "FROM alumnos a JOIN tfc tf ON a.num_matricula = tf.num_matricula " +
                    "JOIN tribunales t ON tf.id_tribunal = t.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{rs.getString("nombre_completo"), rs.getString("tribunal")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 13. Alumnos sin tribunal asignado
    @Override
    public List<Alumno> alumnosSinTribunal() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.* FROM alumnos a " +
                    "JOIN tfc t ON a.num_matricula = t.num_matricula " +
                    "WHERE t.id_tribunal IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"), rs.getString("nombre_completo"), rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

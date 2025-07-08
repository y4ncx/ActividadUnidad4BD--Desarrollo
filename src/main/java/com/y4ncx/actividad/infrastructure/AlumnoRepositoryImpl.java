package com.y4ncx.actividad.infrastructure;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.repository.AlumnoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoRepositoryImpl implements AlumnoRepository {

    @Override
    public String grupoDeAlumno(String dni) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT g.nombre_grupo FROM grupos g " +
                    "JOIN alumno_grupo ag ON g.num_grupo = ag.grupo_numero " +
                    "JOIN alumnos a ON ag.alumno_dni = a.dni " +
                    "WHERE a.dni = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("nombre_grupo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No asignado";
    }



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

    @Override
    public List<Alumno> listarTodos() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM alumnos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")));
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

    @Override
    public Alumno buscarPorMatricula(int matricula) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM alumnos WHERE num_matricula = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Alumno(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Alumno> alumnosSinTFC() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM alumnos WHERE num_matricula NOT IN (SELECT alumno_realiza FROM trabajo_fin_carrera)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> alumnosConGrupoYFecha() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.nombre_completo AS alumno, g.nombre_grupo AS grupo, g.fecha_incorporacion " +
                    "FROM alumnos a " +
                    "JOIN grupos g ON a.dni = g.num_componentes";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("alumno"),
                        rs.getString("grupo"),
                        rs.getString("fecha_incorporacion")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    @Override
    public List<Alumno> alumnosConGrupoInvestigacion() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT * FROM alumnos WHERE num_matricula IN (SELECT num_componentes FROM grupos)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }





    @Override
    public List<Alumno> alumnosDefendieronEntre(String desde, String hasta) {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.* FROM alumnos a " +
                    "JOIN trabajo_fin_carrera t ON a.num_matricula = t.alumno_realiza " +
                    "JOIN tribunales tr ON t.num_orden = tr.tfc_defendido " +
                    "WHERE tr.fecha_defensa BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desde);
            stmt.setString(2, hasta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> alumnosConDirector() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.nombre_completo AS alumno, p.nombre_completo AS profesor " +
                    "FROM alumnos a " +
                    "JOIN trabajo_fin_carrera tfc ON a.dni = tfc.alumno_realiza " +
                    "JOIN profesores p ON tfc.profesor_dirige = p.dni";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("alumno"),
                        rs.getString("profesor")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> alumnosConColaboracionExterna() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT DISTINCT a.nombre_completo AS alumno, p.nombre_completo AS profesor_colaborador " +
                    "FROM alumnos a " +
                    "JOIN trabajo_fin_carrera t ON a.dni = t.alumno_realiza " +
                    "JOIN colaboraciones c ON c.id_tfc = t.num_orden " +
                    "JOIN profesores p ON p.dni = c.dni_profesor " +
                    "WHERE c.dni_profesor <> t.profesor_dirige";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("alumno"),
                        rs.getString("profesor_colaborador")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Alumno> alumnosDefendieronEntreFechas(String desde, String hasta) {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.* FROM alumnos a " +
                    "JOIN trabajo_fin_carrera t ON a.dni = t.alumno_realiza " +
                    "JOIN tribunales tr ON t.num_orden = tr.tfc_defendido " +
                    "WHERE tr.fecha_defensa BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desde);
            stmt.setString(2, hasta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(
                        rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }




    @Override
    public List<Alumno> alumnosConColaboracion() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT DISTINCT a.* FROM alumnos a " +
                    "JOIN trabajo_fin_carrera t ON a.num_matricula = t.alumno_realiza " +
                    "JOIN colaboraciones c ON c.id_tfc = t.num_orden";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String[]> alumnosPorTribunal() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.nombre_completo AS alumno, t.num_tribunal, t.fecha_defensa " +
                    "FROM alumnos a " +
                    "JOIN trabajo_fin_carrera tfc ON a.dni = tfc.alumno_realiza " +
                    "JOIN tribunales t ON tfc.num_orden = t.tfc_defendido";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("alumno"),
                        rs.getString("num_tribunal"),
                        rs.getString("fecha_defensa")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    @Override
    public List<Alumno> alumnosSinTribunal() {
        List<Alumno> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT a.* FROM alumnos a " +
                    "JOIN trabajo_fin_carrera tfc ON a.num_matricula = tfc.alumno_realiza " +
                    "LEFT JOIN tribunales t ON tfc.num_orden = t.tfc_defendido " +
                    "WHERE t.num_tribunal IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Alumno(rs.getString("dni"),
                        rs.getString("nombre_completo"),
                        rs.getInt("num_matricula")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

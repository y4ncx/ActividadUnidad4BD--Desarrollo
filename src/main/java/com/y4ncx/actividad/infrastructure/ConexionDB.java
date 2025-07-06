package com.y4ncx.actividad.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_jassir_yances_35_trabajos_de_grado";
    private static final String USER = "root";
    private static final String PASSWORD = "password123";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

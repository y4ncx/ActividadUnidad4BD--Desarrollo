package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaAlumnosFrame extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private AlumnoRepositoryImpl repo;

    public ConsultaAlumnosFrame() {
        setTitle("🔍 Consultas de Alumnos");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new AlumnoRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(5, 2, 10, 10));

        // Botones de consulta
        JButton btn1 = new JButton("1. Listar todos");
        JButton btn2 = new JButton("2. Buscar por matrícula");
        JButton btn3 = new JButton("3. Alumnos sin TFC");
        JButton btn4 = new JButton("4. Alumnos con grupo");
        JButton btn5 = new JButton("5. Grupo de un alumno");
        JButton btn6 = new JButton("6. Defendieron entre fechas");
        JButton btn7 = new JButton("7. Alumnos con director");
        JButton btn8 = new JButton("8. Con colaboración externa");
        JButton btn9 = new JButton("9. Alumnos por tribunal");
        JButton btn10 = new JButton("10. Sin tribunal");

        panelBotones.add(btn1);
        panelBotones.add(btn2);
        panelBotones.add(btn3);
        panelBotones.add(btn4);
        panelBotones.add(btn5);
        panelBotones.add(btn6);
        panelBotones.add(btn7);
        panelBotones.add(btn8);
        panelBotones.add(btn9);
        panelBotones.add(btn10);

        add(panelBotones, BorderLayout.SOUTH);

        // ACCIONES

        btn1.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Matrícula"});
            modelo.setRowCount(0);
            for (Alumno a : repo.listarTodos()) {
                modelo.addRow(new Object[]{a.getDni(), a.getNombreCompleto(), a.getNumMatricula()});
            }
        });

        btn2.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Número de matrícula:");
            if (input != null) {
                try {
                    int mat = Integer.parseInt(input);
                    Alumno a = repo.buscarPorMatricula(mat);
                    modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Matrícula"});
                    modelo.setRowCount(0);
                    if (a != null) {
                        modelo.addRow(new Object[]{a.getDni(), a.getNombreCompleto(), a.getNumMatricula()});
                    } else {
                        JOptionPane.showMessageDialog(this, "No encontrado");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Número inválido");
                }
            }
        });

        btn3.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Matrícula"});
            modelo.setRowCount(0);
            for (Alumno a : repo.alumnosSinTFC()) {
                modelo.addRow(new Object[]{a.getDni(), a.getNombreCompleto(), a.getNumMatricula()});
            }
        });

        btn4.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"Alumno", "Grupo", "Fecha de Incorporación"});
            modelo.setRowCount(0);
            List<String[]> lista = repo.alumnosConGrupoYFecha();
            for (String[] fila : lista) {
                modelo.addRow(fila);
            }
        });


        btn5.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Número de dni:");
            if (input != null) {
                try {
                    int mat = Integer.parseInt(input);
                    String grupo = repo.grupoDeAlumno(String.valueOf(mat));
                    modelo.setColumnIdentifiers(new String[]{"Dni", "Grupo"});
                    modelo.setRowCount(0);
                    modelo.addRow(new Object[]{mat, grupo});
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Número inválido");
                }
            }
        });

        btn6.addActionListener(e -> {
            String desde = JOptionPane.showInputDialog(this, "Fecha desde (YYYY-MM-DD):");
            String hasta = JOptionPane.showInputDialog(this, "Fecha hasta (YYYY-MM-DD):");

            if (desde != null && hasta != null && !desde.isEmpty() && !hasta.isEmpty()) {
                modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Matrícula"});
                modelo.setRowCount(0);
                List<Alumno> lista = repo.alumnosDefendieronEntreFechas(desde, hasta);
                for (Alumno a : lista) {
                    modelo.addRow(new Object[]{
                            a.getDni(), a.getNombreCompleto(), a.getNumMatricula()
                    });
                }
            }
        });

        btn7.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"Alumno", "Profesor"});
            modelo.setRowCount(0);
            for (String[] fila : repo.alumnosConDirector()) {
                modelo.addRow(fila);
            }
        });

        btn8.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"Alumno", "Profesor colaborador"});
            modelo.setRowCount(0);
            List<String[]> lista = repo.alumnosConColaboracionExterna();
            for (String[] fila : lista) {
                modelo.addRow(fila);
            }
        });

        btn9.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"Alumno", "Tribunal"});
            modelo.setRowCount(0);
            for (String[] fila : repo.alumnosPorTribunal()) {
                modelo.addRow(fila);
            }
        });

        btn10.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Matrícula"});
            modelo.setRowCount(0);
            for (Alumno a : repo.alumnosSinTribunal()) {
                modelo.addRow(new Object[]{a.getDni(), a.getNombreCompleto(), a.getNumMatricula()});
            }
        });

        setVisible(true);
    }
}

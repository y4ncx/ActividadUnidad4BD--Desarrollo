package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;
import com.y4ncx.actividad.repository.AlumnoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private AlumnoRepository repo = new AlumnoRepositoryImpl();

    public VentanaPrincipal() {
        setTitle("Gestión de Alumnos");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------- Tabla ----------
        String[] columnas = {"DNI", "Nombre completo", "Número de matrícula"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        cargarAlumnos();
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ---------- Botones ----------
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Alumno");
        JButton btnEditar = new JButton("Editar Alumno");
        JButton btnEliminar = new JButton("Eliminar Alumno");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // ---------- Eventos ----------
        btnAgregar.addActionListener(e -> new VentanaAgregarAlumno(this::cargarAlumnos));

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String dni = tabla.getValueAt(fila, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar alumno con DNI " + dni + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    repo.eliminar(dni);
                    cargarAlumnos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un alumno para eliminar");
            }
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String dni = tabla.getValueAt(fila, 0).toString();
                String nombre = tabla.getValueAt(fila, 1).toString();
                String matriculaStr = tabla.getValueAt(fila, 2).toString();

                String nuevoNombre = JOptionPane.showInputDialog(this, "Editar nombre:", nombre);
                String nuevaMatriculaStr = JOptionPane.showInputDialog(this, "Editar matrícula:", matriculaStr);

                try {
                    int nuevaMatricula = Integer.parseInt(nuevaMatriculaStr);
                    Alumno actualizado = new Alumno(dni, nuevoNombre, nuevaMatricula);
                    repo.actualizar(actualizado);
                    cargarAlumnos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Matrícula inválida.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un alumno para editar");
            }
        });

        setVisible(true);
    }

    private void cargarAlumnos() {
        modelo.setRowCount(0);
        List<Alumno> alumnos = repo.listarTodos();
        for (Alumno a : alumnos) {
            modelo.addRow(new Object[]{a.getDni(), a.getNombreCompleto(), a.getNumMatricula()});
        }
    }
}
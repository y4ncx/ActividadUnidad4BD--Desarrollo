package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.ConexionDB;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;
import com.y4ncx.actividad.repository.AlumnoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

public class VentanaVerAlumnos extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private AlumnoRepository repo = new AlumnoRepositoryImpl();

    public VentanaVerAlumnos() {
        setTitle("Lista de Alumnos");
        setSize(600, 300);
        setLocationRelativeTo(null);

        String[] columnas = {"DNI", "Nombre completo", "Número de matrícula"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        cargarAlumnos();

        // Menú emergente
        JPopupMenu menu = new JPopupMenu();
        JMenuItem editarItem = new JMenuItem("Editar");
        JMenuItem eliminarItem = new JMenuItem("Eliminar");

        menu.add(editarItem);
        menu.add(eliminarItem);

        tabla.setComponentPopupMenu(menu);

        tabla.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = tabla.rowAtPoint(e.getPoint());
                tabla.setRowSelectionInterval(row, row);
            }
        });

        editarItem.addActionListener(e -> editarAlumno());
        eliminarItem.addActionListener(e -> eliminarAlumno());

        add(new JScrollPane(tabla));
        setVisible(true);
    }

    private void cargarAlumnos() {
        modelo.setRowCount(0);
        List<Alumno> alumnos = repo.listarTodos();
        for (Alumno a : alumnos) {
            modelo.addRow(new Object[]{a.getDni(), a.getNombreCompleto(), a.getNumMatricula()});
        }
    }

    private void eliminarAlumno() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            String dni = tabla.getValueAt(fila, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar alumno con DNI " + dni + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                repo.eliminar(dni);
                cargarAlumnos();
            }
        }
    }

    private void editarAlumno() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            String dniOriginal = tabla.getValueAt(fila, 0).toString();
            String nombre = tabla.getValueAt(fila, 1).toString();
            String matriculaStr = tabla.getValueAt(fila, 2).toString();

            String nuevoNombre = JOptionPane.showInputDialog(this, "Editar nombre:", nombre);
            String nuevaMatriculaStr = JOptionPane.showInputDialog(this, "Editar matrícula:", matriculaStr);

            try {
                int nuevaMatricula = Integer.parseInt(nuevaMatriculaStr);
                Alumno actualizado = new Alumno(dniOriginal, nuevoNombre, nuevaMatricula);
                repo.actualizar(actualizado);
                cargarAlumnos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Matrícula inválida.");
            }
        }
    }
}

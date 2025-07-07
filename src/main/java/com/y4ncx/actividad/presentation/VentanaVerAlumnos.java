package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;
import com.y4ncx.actividad.presentation.consultas.ConsultaAlumnosFrame;
import com.y4ncx.actividad.repository.AlumnoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerAlumnos extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private AlumnoRepository repo;

    public VentanaVerAlumnos() {
        setTitle("Gestión de Alumnos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        repo = new AlumnoRepositoryImpl();

        String[] columnas = {"DNI", "Nombre completo", "Matrícula"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Botones
        JButton btnAgregar = new JButton("📋 Agregar");
        JButton btnEditar = new JButton("✏️ Editar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnConsultas = new JButton("🔍 Ver Consultas");

        btnAgregar.setBackground(new Color(0, 120, 255));
        btnEditar.setBackground(new Color(0, 120, 255));
        btnEliminar.setBackground(new Color(0, 120, 255));
        btnConsultas.setBackground(new Color(100, 100, 255));

        btnAgregar.setForeground(Color.WHITE);
        btnEditar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnConsultas.setForeground(Color.WHITE);

        btnAgregar.addActionListener(e -> new VentanaAgregarAlumno(() -> cargarAlumnos()));
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un alumno para editar.");
                return;
            }
            new VentanaEditarAlumno(tabla);
        });
        btnEliminar.addActionListener(e -> eliminarAlumno());
        btnConsultas.addActionListener(e -> new ConsultaAlumnosFrame());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultas);

        add(panelBotones, BorderLayout.SOUTH);

        cargarAlumnos();
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
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un alumno para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este alumno?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String dni = tabla.getValueAt(fila, 0).toString();
        repo.eliminar(dni);
        cargarAlumnos();
    }
}

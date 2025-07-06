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
        setTitle("🎓 Gestión de Alumnos");

        // Ícono de la ventana
        ImageIcon icon = new ImageIcon(getClass().getResource("/iconos/icon.jpg"));
        setIconImage(icon.getImage());

        setSize(700, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Tabla de alumnos
        modelo = new DefaultTableModel(new String[]{"DNI", "Nombre completo", "Matrícula"}, 0);
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(25);
        cargarAlumnos();

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Botones con estilo
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JButton btnAgregar = crearBoton("† Agregar");
        JButton btnEditar = crearBoton("† Editar");
        JButton btnEliminar = crearBoton("† Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnAgregar.addActionListener(e -> new VentanaAgregarAlumno(this::cargarAlumnos));

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String dni = tabla.getValueAt(fila, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar a " + dni + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
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
                String matricula = tabla.getValueAt(fila, 2).toString();

                String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombre);
                String nuevaMatricula = JOptionPane.showInputDialog(this, "Nueva matrícula:", matricula);

                try {
                    int numMatricula = Integer.parseInt(nuevaMatricula);
                    repo.actualizar(new Alumno(dni, nuevoNombre, numMatricula));
                    cargarAlumnos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Matrícula inválida");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un alumno para editar");
            }
        });

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void cargarAlumnos() {
        modelo.setRowCount(0);
        for (Alumno a : repo.listarTodos()) {
            modelo.addRow(new Object[]{a.getDni(), a.getNombreCompleto(), a.getNumMatricula()});
        }
    }
}

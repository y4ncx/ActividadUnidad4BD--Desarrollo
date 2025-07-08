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




        JButton btnAgregar = crearBoton(" Agregar");
        JButton btnEditar = crearBoton("️ Editar");
        JButton btnEliminar = crearBoton("️ Eliminar");
        JButton btnConsultas = crearBoton(" Ver Consultas");

        btnConsultas.setBackground(new Color(100, 100, 255));

        btnAgregar.addActionListener(e -> new VentanaAgregarAlumno(this::cargarAlumnos));

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un alumno de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String dni = (String) modelo.getValueAt(fila, 0);
            String nombre = (String) modelo.getValueAt(fila, 1);
            int matricula = Integer.parseInt(modelo.getValueAt(fila, 2).toString());

            JTextField campoDni = new JTextField(dni);
            campoDni.setEditable(false);
            JTextField campoNombre = new JTextField(nombre);
            JTextField campoMatricula = new JTextField(String.valueOf(matricula));

            Object[] inputs = {
                    "DNI:", campoDni,
                    "Nombre completo:", campoNombre,
                    "Matrícula:", campoMatricula
            };

            int opcion = JOptionPane.showConfirmDialog(this, inputs, "Editar Alumno", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                try {
                    String nuevoNombre = campoNombre.getText();
                    int nuevaMatricula = Integer.parseInt(campoMatricula.getText());

                    AlumnoRepository repo = new AlumnoRepositoryImpl();
                    repo.actualizar(new Alumno(dni, nuevoNombre, nuevaMatricula));
                    JOptionPane.showMessageDialog(this, "✅ Alumno actualizado correctamente.");
                    cargarAlumnos(); // refresca la tabla
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "⚠️ Matrícula inválida.");
                }
            }
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

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 120, 255));
        btn.setFocusPainted(false);
        return btn;
    }
}

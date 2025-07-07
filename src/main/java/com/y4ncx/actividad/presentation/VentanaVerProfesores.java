package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.infrastructure.ProfesorRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerProfesores extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private ProfesorRepositoryImpl repo;

    public VentanaVerProfesores() {
        setTitle("👨‍🏫 Gestión de Profesores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new ProfesorRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("➕ Agregar");
        JButton btnEditar = new JButton("✏️ Editar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnConsultas = new JButton("🔍 Consultas");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultas);

        add(panelBotones, BorderLayout.SOUTH);

        // Mostrar tabla al inicio
        actualizarTabla();


        // BOTÓN CONSULTAS (más adelante se abre ventana emergente con varias)
        btnConsultas.addActionListener(e -> JOptionPane.showMessageDialog(this, "🔧 Consultas próximamente"));

        // BOTÓN AGREGAR
        btnAgregar.addActionListener(e -> {
            JTextField dni = new JTextField();
            JTextField nombre = new JTextField();
            JTextField domicilio = new JTextField();
            Object[] inputs = {
                    "DNI:", dni,
                    "Nombre completo:", nombre,
                    "Domicilio:", domicilio
            };
            int result = JOptionPane.showConfirmDialog(this, inputs, "Agregar Profesor", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Profesor nuevo = new Profesor(dni.getText(), nombre.getText(), domicilio.getText());
                // Aquí puedes implementar el método repo.insertarProfesor(nuevo);
                JOptionPane.showMessageDialog(this, "✅ Profesor agregado (simulado)");
                actualizarTabla();
            }
        });

        // BOTÓN EDITAR
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un profesor");
                return;
            }

            String dniOriginal = (String) modelo.getValueAt(fila, 0);

            JTextField nombre = new JTextField((String) modelo.getValueAt(fila, 1));
            JTextField domicilio = new JTextField((String) modelo.getValueAt(fila, 2));

            Object[] inputs = {
                    "Nombre completo:", nombre,
                    "Domicilio:", domicilio
            };
            int result = JOptionPane.showConfirmDialog(this, inputs, "Editar Profesor", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Aquí podrías actualizar con repo.actualizarProfesor(...);
                JOptionPane.showMessageDialog(this, "✅ Profesor editado (simulado)");
                actualizarTabla();
            }
        });

        // BOTÓN ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un profesor");
                return;
            }

            String dni = (String) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar al profesor con DNI " + dni + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Aquí podrías usar repo.eliminarProfesor(dni);
                JOptionPane.showMessageDialog(this, "✅ Profesor eliminado (simulado)");
                actualizarTabla();
            }
        });

        setVisible(true);
    }

    private void actualizarTabla() {
        modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Domicilio"});
        modelo.setRowCount(0);
        List<Profesor> lista = repo.listarTodos();
        for (Profesor p : lista) {
            modelo.addRow(new Object[]{p.getDni(), p.getNombreCompleto(), p.getDomicilio()});
        }
    }
}

package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.infrastructure.TribunalRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerTribunales extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private TribunalRepositoryImpl repo;

    public VentanaVerTribunales() {
        setTitle("⚖️ Gestión de Tribunales");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new TribunalRepositoryImpl();
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

        mostrarDatos();

        btnAgregar.addActionListener(e -> {
            JTextField num = new JTextField();
            JTextField lugar = new JTextField();
            JTextField profs = new JTextField();
            JTextField campoPresente = new JTextField();  // CAMBIO
            JTextField campoDefendido = new JTextField(); // CAMBIO
            JTextField fecha = new JTextField();
            Object[] inputs = {
                    "N° Tribunal:", num,
                    "Lugar de examen:", lugar,
                    "Cantidad de profesores:", profs,
                    "Alumno presente:", campoPresente,
                    "TFC defendido:", campoDefendido,
                    "Fecha defensa (YYYY-MM-DD):", fecha
            };
            int res = JOptionPane.showConfirmDialog(this, inputs, "Agregar Tribunal", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                // Aquí deberías guardar en la base de datos si lo deseas
                JOptionPane.showMessageDialog(this, "✅ Tribunal agregado (simulado)");
                mostrarDatos();
            }
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un tribunal de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JTextField lugar = new JTextField((String) modelo.getValueAt(fila, 1));
            JTextField cantidad = new JTextField(modelo.getValueAt(fila, 2).toString());
            JCheckBox alumnoPresente = new JCheckBox("Alumno presente", (boolean) modelo.getValueAt(fila, 3));
            JCheckBox tfcDefendido = new JCheckBox("TFC defendido", (boolean) modelo.getValueAt(fila, 4));
            JTextField fecha = new JTextField((String) modelo.getValueAt(fila, 5));

            Object[] inputs = {
                    "Lugar de examen:", lugar,
                    "Cantidad de profesores:", cantidad,
                    alumnoPresente,
                    tfcDefendido,
                    "Fecha defensa:", fecha
            };
            int res = JOptionPane.showConfirmDialog(this, inputs, "Editar Tribunal", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "✅ Tribunal editado (simulado)");
                mostrarDatos();
            }
        });


        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un tribunal para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar tribunal #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "✅ Tribunal eliminado (simulado)");
                mostrarDatos();
            }
        });


        btnConsultas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "🔍 Consultas de tribunales próximamente");
        });

        setVisible(true);
    }

    private void mostrarDatos() {
        modelo.setColumnIdentifiers(new String[]{"N° Tribunal", "Lugar", "Profesores", "Alumno presente", "TFC defendido", "Fecha Defensa"});
        modelo.setRowCount(0);
        for (Tribunal t : repo.listarTodos()) {
            modelo.addRow(new Object[]{
                    t.getNumTribunal(), t.getLugarExamen(), t.getCantidadProfesores(),
                    t.isAlumnoPresente(), t.isTfcDefendido(), t.getFechaDefensa()
            });
        }
    }
}

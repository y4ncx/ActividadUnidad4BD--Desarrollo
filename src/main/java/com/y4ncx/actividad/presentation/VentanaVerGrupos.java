package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.infrastructure.GrupoRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerGrupos extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private GrupoRepositoryImpl repo;

    public VentanaVerGrupos() {
        setTitle("🧪 Gestión de Grupos de Investigación");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new GrupoRepositoryImpl();
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
            JTextField numGrupo = new JTextField();
            JTextField nombre = new JTextField();
            JTextField componentes = new JTextField();
            JTextField fecha = new JTextField();
            Object[] inputs = {
                    "Número de Grupo:", numGrupo,
                    "Nombre:", nombre,
                    "N° de componentes:", componentes,
                    "Fecha incorporación (YYYY-MM-DD):", fecha
            };
            int res = JOptionPane.showConfirmDialog(this, inputs, "Agregar Grupo", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "✅ Grupo agregado (simulado)");
                mostrarDatos();
            }
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un grupo de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JTextField nombre = new JTextField((String) modelo.getValueAt(fila, 1));
            JTextField componentes = new JTextField(modelo.getValueAt(fila, 2).toString());
            JTextField fecha = new JTextField((String) modelo.getValueAt(fila, 3));
            Object[] inputs = {
                    "Nombre:", nombre,
                    "N° componentes:", componentes,
                    "Fecha incorporación:", fecha
            };
            int res = JOptionPane.showConfirmDialog(this, inputs, "Editar Grupo", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "✅ Grupo editado (simulado)");
                mostrarDatos();
            }
        });


        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un grupo para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nombre = (String) modelo.getValueAt(fila, 1);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar grupo \"" + nombre + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "✅ Grupo eliminado (simulado)");
                mostrarDatos();
            }
        });


        btnConsultas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "🔍 Consultas de grupos próximamente");
        });

        setVisible(true);
    }

    private void mostrarDatos() {
        modelo.setColumnIdentifiers(new String[]{"N° Grupo", "Nombre", "Componentes", "Fecha Incorporación"});
        modelo.setRowCount(0);
        for (Grupo g : repo.listarTodos()) {
            modelo.addRow(new Object[]{
                    g.getNumGrupo(), g.getNombreGrupo(), g.getNumComponentes(), g.getFechaIncorporacion()
            });
        }
    }
}

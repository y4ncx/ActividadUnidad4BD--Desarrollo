package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.infrastructure.GrupoRepositoryImpl;
import com.y4ncx.actividad.repository.GrupoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaEditarGrupos extends JFrame {

    public VentanaEditarGrupos(JTable tabla, Runnable callback) {
        setTitle("✏️ Editar Grupo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        GrupoRepository repo = new GrupoRepositoryImpl();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un grupo para editar.");
            dispose();
            return;
        }

        int numGrupo = (int) modelo.getValueAt(fila, 0);
        JTextField txtNombre = new JTextField((String) modelo.getValueAt(fila, 1));
        JTextField txtIntegrantes = new JTextField(modelo.getValueAt(fila, 2).toString());
        JTextField txtFecha = new JTextField((String) modelo.getValueAt(fila, 3));

        add(new JLabel("Nombre del grupo:")); add(txtNombre);
        add(new JLabel("N° de integrantes:")); add(txtIntegrantes);
        add(new JLabel("Fecha incorporación:")); add(txtFecha);

        JButton btnGuardar = new JButton("Actualizar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                int integrantes = Integer.parseInt(txtIntegrantes.getText());
                String fecha = txtFecha.getText();

                Grupo g = new Grupo(numGrupo, nombre, integrantes, fecha);
                repo.actualizar(g);
                JOptionPane.showMessageDialog(this, "✅ Grupo actualizado");
                callback.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Datos inválidos");
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(btnGuardar);
        add(btnCancelar);

        setVisible(true);
    }
}

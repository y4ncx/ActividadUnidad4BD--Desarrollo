package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.infrastructure.TribunalRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaEditarTribunal extends JFrame {

    public VentanaEditarTribunal(JTable tabla, Runnable onEditarCallback) {
        setTitle("✏️ Editar Tribunal");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        TribunalRepositoryImpl repo = new TribunalRepositoryImpl();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int fila = tabla.getSelectedRow();

        int numTribunal = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
        JTextField txtLugar = new JTextField(modelo.getValueAt(fila, 1).toString());
        JTextField txtProfesores = new JTextField(modelo.getValueAt(fila, 2).toString());
        JTextField txtAlumnoPresente = new JTextField(modelo.getValueAt(fila, 3).toString());
        JTextField txtTfcDefendido = new JTextField(modelo.getValueAt(fila, 4).toString());
        JTextField txtFecha = new JTextField(modelo.getValueAt(fila, 5).toString());

        add(new JLabel("Lugar de examen:"));
        add(txtLugar);
        add(new JLabel("Cantidad de profesores:"));
        add(txtProfesores);
        add(new JLabel("Alumno presente:"));
        add(txtAlumnoPresente);
        add(new JLabel("TFC defendido:"));
        add(txtTfcDefendido);
        add(new JLabel("Fecha defensa (YYYY-MM-DD):"));
        add(txtFecha);

        JButton btnGuardar = new JButton("💾 Actualizar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                Tribunal tribunal = new Tribunal(
                        numTribunal,
                        txtLugar.getText(),
                        Integer.parseInt(txtProfesores.getText()),
                        txtAlumnoPresente.getText().trim(),
                        txtTfcDefendido.getText().trim(),
                        txtFecha.getText().trim()
                );
                repo.actualizar(tribunal);
                JOptionPane.showMessageDialog(this, "✅ Tribunal actualizado correctamente");
                onEditarCallback.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar tribunal: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(btnGuardar);
        add(btnCancelar);

        setVisible(true);
    }
}

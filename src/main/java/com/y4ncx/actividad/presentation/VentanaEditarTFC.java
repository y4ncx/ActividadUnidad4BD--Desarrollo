package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.infrastructure.TrabajosFinCarreraRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class VentanaEditarTFC extends JFrame {

    public VentanaEditarTFC(JTable tabla, Runnable actualizarTabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un T.F.C. para editar.");
            dispose();
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        String ordenStr = modelo.getValueAt(fila, 0).toString();
        String temaActual = modelo.getValueAt(fila, 1).toString();
        String fechaActual = modelo.getValueAt(fila, 2).toString();
        String alumnoActual = modelo.getValueAt(fila, 3).toString();

        JTextField txtTema = new JTextField(temaActual, 20);
        JTextField txtFecha = new JTextField(fechaActual);
        JTextField txtAlumno = new JTextField(alumnoActual);

        JButton btnActualizar = new JButton("Guardar Cambios");

        btnActualizar.addActionListener(e -> {
            try {
                int orden = Integer.parseInt(ordenStr);
                String tema = txtTema.getText();
                LocalDate fecha = LocalDate.parse(txtFecha.getText());
                String alumno = txtAlumno.getText();

                TrabajosFinCarrera tfc = new TrabajosFinCarrera(orden, tema, fecha, alumno);
                TrabajosFinCarreraRepositoryImpl repo = new TrabajosFinCarreraRepositoryImpl();
                repo.actualizar(tfc);

                JOptionPane.showMessageDialog(this, "T.F.C. actualizado correctamente.");
                actualizarTabla.run();
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setTitle("✏️ Editar T.F.C.");
        setSize(400, 230);
        setLayout(new GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel("Tema:")); add(txtTema);
        add(new JLabel("Fecha de Inicio:")); add(txtFecha);
        add(new JLabel("Alumno:")); add(txtAlumno);
        add(new JLabel("")); add(btnActualizar);

        setVisible(true);
    }
}

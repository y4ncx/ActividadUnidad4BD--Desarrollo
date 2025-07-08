package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.infrastructure.TrabajosFinCarreraRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class VentanaEditarTFC extends JFrame {

    public VentanaEditarTFC(JTable tabla, DefaultTableModel modelo, Runnable callback) {
        setTitle("✏️ Editar TFC");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un T.F.C. para editar");
            dispose();
            return;
        }

        TrabajosFinCarreraRepositoryImpl repo = new TrabajosFinCarreraRepositoryImpl();

        int numOrden = (int) modelo.getValueAt(fila, 0);

        JTextField txtTema = new JTextField(modelo.getValueAt(fila, 1).toString());
        JTextField txtFecha = new JTextField(modelo.getValueAt(fila, 2).toString());
        JTextField txtAlumno = new JTextField(modelo.getValueAt(fila, 3).toString());
        JTextField txtProfesor = new JTextField(modelo.getValueAt(fila, 4).toString());

        add(new JLabel("Tema:")); add(txtTema);
        add(new JLabel("Fecha Inicio:")); add(txtFecha);
        add(new JLabel("Alumno (DNI):")); add(txtAlumno);
        add(new JLabel("Profesor (DNI):")); add(txtProfesor);

        JButton btnGuardar = new JButton("💾 Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                String tema = txtTema.getText();
                LocalDate fecha = LocalDate.parse(txtFecha.getText());
                int alumno = Integer.parseInt(txtAlumno.getText());
                int profesor = Integer.parseInt(txtProfesor.getText());

                repo.actualizar(new TrabajosFinCarrera(numOrden, tema, fecha, alumno, profesor));
                JOptionPane.showMessageDialog(this, "TFC actualizado correctamente");
                callback.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(btnGuardar);
        setVisible(true);
    }
}

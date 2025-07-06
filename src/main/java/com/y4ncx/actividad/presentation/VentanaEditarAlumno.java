package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;

import javax.swing.*;
import java.awt.*;

public class VentanaEditarAlumno extends JFrame {

    public VentanaEditarAlumno(JTable tabla) {
        setTitle("✏️ Editar Alumno");
        setSize(350, 250);
        setLocationRelativeTo(null);

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un alumno para editar");
            dispose();
            return;
        }

        String dniSeleccionado = tabla.getValueAt(fila, 0).toString();
        String nombreSeleccionado = tabla.getValueAt(fila, 1).toString();
        String matriculaSeleccionada = tabla.getValueAt(fila, 2).toString();

        JLabel lblDNI = new JLabel("DNI:");
        JTextField txtDNI = new JTextField(dniSeleccionado, 15);
        txtDNI.setEditable(false); // no se permite cambiar el DNI

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(nombreSeleccionado, 15);

        JLabel lblMatricula = new JLabel("Matrícula:");
        JTextField txtMatricula = new JTextField(matriculaSeleccionada, 10);

        JButton btnActualizar = new JButton("Guardar cambios");

        btnActualizar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            int matricula = Integer.parseInt(txtMatricula.getText());

            Alumno alumno = new Alumno(dniSeleccionado, nombre, matricula);
            alumno.setDni(dniSeleccionado);
            alumno.setNombreCompleto(nombre);
            alumno.setNumMatricula(matricula);

            AlumnoRepositoryImpl repo = new AlumnoRepositoryImpl();
            repo.actualizar(alumno);

            JOptionPane.showMessageDialog(this, "Alumno actualizado correctamente");
            dispose();
        });

        setLayout(new GridLayout(4, 2, 5, 5));
        add(lblDNI); add(txtDNI);
        add(lblNombre); add(txtNombre);
        add(lblMatricula); add(txtMatricula);
        add(new JLabel()); add(btnActualizar);

        setVisible(true);
    }
}

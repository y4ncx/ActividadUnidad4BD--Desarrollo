package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;
import com.y4ncx.actividad.repository.AlumnoRepository;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class VentanaAgregarAlumno extends JFrame {

    public VentanaAgregarAlumno(Runnable callbackActualizarTabla) {
        setTitle("Agregar Alumno");
        setSize(350, 250);
        setLocationRelativeTo(null);

        JLabel lblDNI = new JLabel("DNI:");
        JTextField txtDNI = new JTextField(15);

        JLabel lblNombre = new JLabel("Nombre completo:");
        JTextField txtNombre = new JTextField(15);

        JLabel lblMatricula = new JLabel("Número de matrícula:");
        JTextField txtMatricula = new JTextField(10);

        JButton btnGuardar = new JButton("Guardar");

        AlumnoRepository repo = new AlumnoRepositoryImpl();

        btnGuardar.addActionListener(e -> {
            try {
                String dni = txtDNI.getText();
                String nombre = txtNombre.getText();
                int matricula = Integer.parseInt(txtMatricula.getText());

                Alumno alumno = new Alumno(dni, nombre, matricula);

                try {
                    repo.guardar(new Alumno(dni, nombre, matricula));
                    JOptionPane.showMessageDialog(this, "Alumno agregado correctamente");
                    callbackActualizarTabla.run();
                    dispose();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }


                callbackActualizarTabla.run();  // 🔁 Actualizar tabla
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        });

        setLayout(new GridLayout(4, 2, 5, 5));
        add(lblDNI); add(txtDNI);
        add(lblNombre); add(txtNombre);
        add(lblMatricula); add(txtMatricula);
        add(new JLabel()); add(btnGuardar);

        setVisible(true);
    }
}

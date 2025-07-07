package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Alumno;
import com.y4ncx.actividad.infrastructure.AlumnoRepositoryImpl;
import com.y4ncx.actividad.repository.AlumnoRepository;

import javax.swing.*;
import java.awt.*;

public class VentanaAgregarAlumno extends JFrame {

    public VentanaAgregarAlumno(Runnable callbackActualizarTabla) {
        setTitle("➕ Nuevo Alumno");
        setSize(350, 230);
        setLocationRelativeTo(null);

        AlumnoRepository repo = new AlumnoRepositoryImpl();

        JLabel lblDNI = new JLabel("DNI:");
        JTextField txtDNI = new JTextField(15);

        JLabel lblNombre = new JLabel("Nombre completo:");
        JTextField txtNombre = new JTextField(15);

        JLabel lblMatricula = new JLabel("Matrícula:");
        JTextField txtMatricula = new JTextField(10);

        JButton btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnGuardar.addActionListener(e -> {
            try {
                String dni = txtDNI.getText();
                String nombre = txtNombre.getText();
                int matricula = Integer.parseInt(txtMatricula.getText());

                repo.guardar(new Alumno(dni, nombre, matricula));
                JOptionPane.showMessageDialog(this, "Alumno guardado correctamente");
                callbackActualizarTabla.run();
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Matrícula inválida");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLayout(new GridLayout(4, 2, 8, 8));
        add(lblDNI); add(txtDNI);
        add(lblNombre); add(txtNombre);
        add(lblMatricula); add(txtMatricula);
        add(new JLabel()); add(btnGuardar);

        setVisible(true);
    }
}
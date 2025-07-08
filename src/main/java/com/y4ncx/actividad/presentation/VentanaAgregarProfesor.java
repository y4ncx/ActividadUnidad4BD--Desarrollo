package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.infrastructure.ProfesorRepositoryImpl;
import com.y4ncx.actividad.repository.ProfesorRepository;

import javax.swing.*;
import java.awt.*;

public class VentanaAgregarProfesor extends JFrame {

    public VentanaAgregarProfesor(Runnable callbackActualizar) {
        setTitle("➕ Nuevo Profesor");
        setSize(350, 230);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        ProfesorRepository repo = new ProfesorRepositoryImpl();

        JTextField txtDNI = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtDireccion = new JTextField();

        JButton btnGuardar = new JButton("Guardar");

        btnGuardar.addActionListener(e -> {
            try {
                String dni = txtDNI.getText();
                String nombre = txtNombre.getText();
                String direccion = txtDireccion.getText();

                repo.agregar(new Profesor(dni, nombre, direccion));
                JOptionPane.showMessageDialog(this, "✅ Profesor guardado correctamente");
                callbackActualizar.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        });

        add(new JLabel("DNI:")); add(txtDNI);
        add(new JLabel("Nombre completo:")); add(txtNombre);
        add(new JLabel("Dirección:")); add(txtDireccion);
        add(new JLabel()); add(btnGuardar);

        setVisible(true);
    }
}

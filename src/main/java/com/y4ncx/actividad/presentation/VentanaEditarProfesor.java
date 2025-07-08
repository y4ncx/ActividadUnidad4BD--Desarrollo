package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.infrastructure.ProfesorRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaEditarProfesor extends JFrame {

    public VentanaEditarProfesor(JTable tabla, Runnable callbackActualizar) {
        setTitle("✏️ Editar Profesor");
        setSize(350, 230);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        int fila = tabla.getSelectedRow();
        String dni = tabla.getValueAt(fila, 0).toString();
        String nombre = tabla.getValueAt(fila, 1).toString();
        String direccion = tabla.getValueAt(fila, 2).toString();

        JTextField txtNombre = new JTextField(nombre);
        JTextField txtDireccion = new JTextField(direccion);

        JButton btnGuardar = new JButton("Guardar");

        btnGuardar.addActionListener(e -> {
            String nuevoNombre = txtNombre.getText();
            String nuevaDir = txtDireccion.getText();

            Profesor profesorActualizado = new Profesor(dni, nuevoNombre, nuevaDir);
            new ProfesorRepositoryImpl().actualizar(profesorActualizado);

            JOptionPane.showMessageDialog(this, "✅ Profesor actualizado");
            callbackActualizar.run();
            dispose();
        });

        add(new JLabel("Nombre:")); add(txtNombre);
        add(new JLabel("Dirección:")); add(txtDireccion);
        add(new JLabel()); add(btnGuardar);

        setVisible(true);
    }
}

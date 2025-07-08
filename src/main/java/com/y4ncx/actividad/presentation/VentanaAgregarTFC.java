package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.infrastructure.TrabajosFinCarreraRepositoryImpl;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class VentanaAgregarTFC extends JFrame {

    public VentanaAgregarTFC(Runnable callbackActualizarTabla) {
        setTitle("➕ Agregar T.F.C.");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        TrabajosFinCarreraRepositoryImpl repo = new TrabajosFinCarreraRepositoryImpl();

        JTextField txtOrden = new JTextField();
        JTextField txtTema = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtAlumno = new JTextField();
        JTextField txtProfesor = new JTextField();

        add(new JLabel("N° de orden:"));     add(txtOrden);
        add(new JLabel("Tema:"));            add(txtTema);
        add(new JLabel("Fecha inicio (YYYY-MM-DD):")); add(txtFecha);
        add(new JLabel("DNI del alumno:"));  add(txtAlumno);
        add(new JLabel("DNI del profesor:"));add(txtProfesor);

        JButton btnGuardar = new JButton("💾 Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnGuardar.addActionListener(e -> {
            try {
                int orden = Integer.parseInt(txtOrden.getText().trim());
                String tema = txtTema.getText().trim();
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
                int alumno = Integer.parseInt(txtAlumno.getText().trim());
                int profesor = Integer.parseInt(txtProfesor.getText().trim());

                TrabajosFinCarrera tfc = new TrabajosFinCarrera(orden, tema, fecha, alumno, profesor);
                repo.agregar(tfc);

                JOptionPane.showMessageDialog(this, "✅ T.F.C. guardado correctamente");
                callbackActualizarTabla.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(btnGuardar); add(btnCancelar);
        setVisible(true);
    }
}

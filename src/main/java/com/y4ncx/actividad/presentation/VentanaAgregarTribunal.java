package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.infrastructure.TribunalRepositoryImpl;

import javax.swing.*;
import java.awt.*;

public class VentanaAgregarTribunal extends JFrame {

    public VentanaAgregarTribunal(Runnable onAgregarCallback) {
        setTitle("➕ Agregar Tribunal");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        TribunalRepositoryImpl repo = new TribunalRepositoryImpl();

        JTextField txtNumero = new JTextField();
        JTextField txtLugar = new JTextField();
        JTextField txtProfesores = new JTextField();
        JTextField txtAlumnoPresente = new JTextField();
        JTextField txtTfcDefendido = new JTextField();
        JTextField txtFecha = new JTextField();

        add(new JLabel("N° Tribunal:"));
        add(txtNumero);
        add(new JLabel("Lugar de examen:"));
        add(txtLugar);
        add(new JLabel("Cantidad de profesores:"));
        add(txtProfesores);
        add(new JLabel("Alumno presente (DNI o vacío):"));
        add(txtAlumnoPresente);
        add(new JLabel("TFC defendido (ID o vacío):"));
        add(txtTfcDefendido);
        add(new JLabel("Fecha de defensa (YYYY-MM-DD):"));
        add(txtFecha);

        JButton btnGuardar = new JButton("💾 Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                Tribunal tribunal = new Tribunal(
                        Integer.parseInt(txtNumero.getText()),
                        txtLugar.getText(),
                        Integer.parseInt(txtProfesores.getText()),
                        txtAlumnoPresente.getText().trim(),
                        txtTfcDefendido.getText().trim(),
                        txtFecha.getText().trim()
                );
                repo.agregar(tribunal);
                JOptionPane.showMessageDialog(this, "✅ Tribunal agregado correctamente");
                onAgregarCallback.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar tribunal: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(btnGuardar);
        add(btnCancelar);

        setVisible(true);
    }
}

package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.infrastructure.GrupoRepositoryImpl;
import com.y4ncx.actividad.repository.GrupoRepository;

import javax.swing.*;
import java.awt.*;

public class VentanaAgregarGrupos extends JFrame {

    public VentanaAgregarGrupos(Runnable callback) {
        setTitle("➕ Nuevo Grupo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        GrupoRepository repo = new GrupoRepositoryImpl();

        JTextField txtNumGrupo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtIntegrantes = new JTextField();
        JTextField txtFecha = new JTextField();

        add(new JLabel("N° de Grupo:")); add(txtNumGrupo);
        add(new JLabel("Nombre del grupo:")); add(txtNombre);
        add(new JLabel("N° de integrantes:")); add(txtIntegrantes);
        add(new JLabel("Fecha incorporación (YYYY-MM-DD):")); add(txtFecha);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                int num = Integer.parseInt(txtNumGrupo.getText());
                String nombre = txtNombre.getText();
                int integrantes = Integer.parseInt(txtIntegrantes.getText());
                String fecha = txtFecha.getText();

                repo.agregar(new Grupo(num, nombre, integrantes, fecha));
                JOptionPane.showMessageDialog(this, "✅ Grupo agregado exitosamente");
                callback.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Datos inválidos");
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(btnGuardar);
        add(btnCancelar);

        setVisible(true);
    }
}

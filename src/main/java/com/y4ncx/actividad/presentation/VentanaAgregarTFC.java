package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.infrastructure.TrabajosFinCarreraRepositoryImpl;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.function.Consumer;

public class VentanaAgregarTFC extends JDialog {

    private final TrabajosFinCarreraRepositoryImpl repo = new TrabajosFinCarreraRepositoryImpl();

    public VentanaAgregarTFC(Consumer<Void> callbackActualizar) {
        setTitle("➕ Agregar T.F.C.");
        setModal(true);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        JTextField campoOrden = new JTextField();
        JTextField campoTema = new JTextField();
        JTextField campoFecha = new JTextField(); // formato YYYY-MM-DD
        JTextField campoAlumno = new JTextField(); // DNI del alumno
        JTextField campoTribunal = new JTextField(); // Número del tribunal (defendió)

        panelCampos.add(new JLabel("Número de Orden:"));
        panelCampos.add(campoOrden);
        panelCampos.add(new JLabel("Tema:"));
        panelCampos.add(campoTema);
        panelCampos.add(new JLabel("Fecha de Inicio (YYYY-MM-DD):"));
        panelCampos.add(campoFecha);
        panelCampos.add(new JLabel("DNI del Alumno:"));
        panelCampos.add(campoAlumno);
        panelCampos.add(new JLabel("N° Tribunal (defendió):"));
        panelCampos.add(campoTribunal);

        add(panelCampos, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                int orden = Integer.parseInt(campoOrden.getText().trim());
                String tema = campoTema.getText().trim();
                LocalDate fechaInicio = LocalDate.parse(campoFecha.getText().trim());
                String alumnoDni = campoAlumno.getText().trim();
                String tribunalNum = campoTribunal.getText().trim(); // puedes almacenarlo si lo usas luego

                TrabajosFinCarrera nuevo = new TrabajosFinCarrera(orden, tema, fechaInicio, alumnoDni);
                repo.agregar(nuevo);

                callbackActualizar.accept(null);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar T.F.C.: " + ex.getMessage());
            }
        });

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.add(btnGuardar);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }
}

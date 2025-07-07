package com.y4ncx.actividad.presentation;

import javax.swing.*;
import java.awt.*;

public class VentanaMenuPrincipal extends JFrame {

    public VentanaMenuPrincipal() {
        setTitle("🎓 Sistema de Gestión Académica");
        setSize(420, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 15, 15));

        // Botones
        JButton btnAlumnos = new JButton(" Alumnos");
        JButton btnTFC = new JButton(" Trabajos de Fin de Carrera");
        JButton btnProfesores = new JButton(" Profesores");
        JButton btnTribunales = new JButton(" Tribunales Evaluadores");
        JButton btnGrupos = new JButton(" Grupos de Investigación");
        JButton btnSalir = new JButton(" Salir");

        // Estilos
        configurarBoton(btnAlumnos, new Color(33, 150, 243));
        configurarBoton(btnTFC, new Color(76, 175, 80));
        configurarBoton(btnProfesores, new Color(255, 152, 0));
        configurarBoton(btnTribunales, new Color(156, 39, 176));
        configurarBoton(btnGrupos, new Color(0, 188, 212));
        configurarBoton(btnSalir, new Color(244, 67, 54));

        // Acciones (solo Alumnos y TFC están implementadas por ahora)
        btnAlumnos.addActionListener(e -> new VentanaVerAlumnos());
        btnTFC.addActionListener(e -> new VentanaVerTFC());

        btnProfesores.addActionListener(e -> new VentanaVerProfesores());
        btnTribunales.addActionListener(e -> new VentanaVerTribunales());
        btnGrupos.addActionListener(e -> new VentanaVerGrupos());

        btnSalir.addActionListener(e -> System.exit(0));

        // Agregar al layout
        add(btnAlumnos);
        add(btnTFC);
        add(btnProfesores);
        add(btnTribunales);
        add(btnGrupos);
        add(btnSalir);

        setVisible(true);
    }

    private void configurarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(300, 50));
    }
}

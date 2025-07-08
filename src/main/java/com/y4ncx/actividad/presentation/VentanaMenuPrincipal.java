package com.y4ncx.actividad.presentation;

import javax.swing.*;
import java.awt.*;

public class VentanaMenuPrincipal extends JFrame {

    public VentanaMenuPrincipal() {
        setTitle("🎓 Sistema de Gestión Académica");
        setSize(420, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        // 🔝 Panel superior con título e imagen
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(Color.BLACK);

        JLabel titulo = new JLabel("GESTIÓN DE TRABAJOS TFC", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/icon.jpg"));
            Image img = icono.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            JLabel lblImagen = new JLabel(new ImageIcon(img));
            lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelSuperior.add(titulo);
            panelSuperior.add(lblImagen);
        } catch (Exception e) {
            JLabel lblError = new JLabel("❌ Imagen no encontrada");
            lblError.setForeground(Color.RED);
            lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelSuperior.add(titulo);
            panelSuperior.add(lblError);
        }

        add(panelSuperior, BorderLayout.NORTH);

        // 📦 Panel central con botones
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 15, 15));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // Botones
        JButton btnAlumnos = new JButton(" Alumnos");
        JButton btnTFC = new JButton(" Trabajos de Fin de Carrera");
        JButton btnProfesores = new JButton(" Profesores");
        JButton btnTribunales = new JButton(" Tribunales Evaluadores");
        JButton btnGrupos = new JButton(" Grupos de Investigación");
        JButton btnSalir = new JButton(" Salir");

        configurarBoton(btnAlumnos, new Color(173, 175, 177));
        configurarBoton(btnTFC, new Color(153, 156, 161));
        configurarBoton(btnProfesores, new Color(88, 92, 99));
        configurarBoton(btnTribunales, new Color(73, 80, 87));
        configurarBoton(btnGrupos, new Color(52, 58, 64));
        configurarBoton(btnSalir, new Color(33, 37, 41));

        // Acciones
        btnAlumnos.addActionListener(e -> new VentanaVerAlumnos());
        btnTFC.addActionListener(e -> new VentanaVerTFC());
        btnProfesores.addActionListener(e -> new VentanaVerProfesores());
        btnTribunales.addActionListener(e -> new VentanaVerTribunales());
        btnGrupos.addActionListener(e -> new VentanaVerGrupos());
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnAlumnos);
        panelBotones.add(btnTFC);
        panelBotones.add(btnProfesores);
        panelBotones.add(btnTribunales);
        panelBotones.add(btnGrupos);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
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

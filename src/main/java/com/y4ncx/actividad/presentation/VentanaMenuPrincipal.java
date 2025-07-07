package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.presentation.consultas.ConsultaTFCFrame;

import javax.swing.*;
import java.awt.*;

public class VentanaMenuPrincipal extends JFrame {

    public VentanaMenuPrincipal() {
        setTitle("🎓 Sistema de Gestión de T.F.C.");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnAlumnos = crearBoton("👨‍🎓 Alumnos");
        JButton btnTFC = crearBoton("📚 TFC");
        JButton btnProfesores = crearBoton("🧑‍🏫 Profesores");
        JButton btnTribunales = crearBoton("⚖️ Tribunales");
        JButton btnGrupos = crearBoton("🧪 Grupos de Investigación");
        JButton btnSalir = crearBoton("🚪 Salir");

        add(btnAlumnos);
        add(btnTFC);
        add(btnProfesores);
        add(btnTribunales);
        add(btnGrupos);
        add(btnSalir);

        btnAlumnos.addActionListener(e -> new VentanaVerAlumnos());
        btnTFC.addActionListener(e -> new ConsultaTFCFrame());
        btnProfesores.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ventana de profesores en construcción 🛠️"));
        btnTribunales.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ventana de tribunales en construcción 🛠️"));
        btnGrupos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ventana de grupos en construcción 🛠️"));

        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }
}

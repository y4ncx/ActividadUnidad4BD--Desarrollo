package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.infrastructure.TribunalRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaTribunalesFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private TribunalRepositoryImpl repo;

    public ConsultaTribunalesFrame() {
        setTitle("📊 Consultas de Tribunales");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new TribunalRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton btn1 = new JButton("1. Listar todos");
        JButton btn2 = new JButton("2. + de 3 profesores");
        JButton btn3 = new JButton("3. Con alumno presente");
        JButton btn4 = new JButton("4. Donde se defendió TFC");
        JButton btn5 = new JButton("5. Entre dos fechas");

        panelBotones.add(btn1);
        panelBotones.add(btn2);
        panelBotones.add(btn3);
        panelBotones.add(btn4);
        panelBotones.add(btn5);

        add(panelBotones, BorderLayout.SOUTH);

        // Listeners
        btn1.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"N°", "Lugar", "Profesores", "Alumno presente", "TFC defendido", "Fecha"});
            modelo.setRowCount(0);
            for (Tribunal t : repo.listarTodos()) {
                modelo.addRow(new Object[]{
                        t.getNumTribunal(), t.getLugarExamen(), t.getCantidadProfesores(),
                        t.getAlumnoPresente(), t.getTfcDefendido(), t.getFechaDefensa()
                });
            }
        });

        btn2.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"N°", "Lugar", "Profesores"});
            modelo.setRowCount(0);
            for (Tribunal t : repo.masDeTresProfesores()) {
                modelo.addRow(new Object[]{
                        t.getNumTribunal(), t.getLugarExamen(), t.getCantidadProfesores()
                });
            }
        });

        btn3.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"N°", "Lugar", "Alumno presente"});
            modelo.setRowCount(0);
            for (Tribunal t : repo.tribunalesConAlumnoPresente()) {
                modelo.addRow(new Object[]{
                        t.getNumTribunal(), t.getLugarExamen(), t.getAlumnoPresente()
                });
            }
        });

        btn4.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"N°", "Lugar", "TFC defendido"});
            modelo.setRowCount(0);
            for (Tribunal t : repo.tribunalesConDefensa()) {
                modelo.addRow(new Object[]{
                        t.getNumTribunal(), t.getLugarExamen(), t.getTfcDefendido()
                });
            }
        });

        btn5.addActionListener(e -> {
            JTextField desde = new JTextField();
            JTextField hasta = new JTextField();
            Object[] inputs = {
                    "Desde (YYYY-MM-DD):", desde,
                    "Hasta (YYYY-MM-DD):", hasta
            };
            int res = JOptionPane.showConfirmDialog(this, inputs, "Fechas", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                modelo.setColumnIdentifiers(new String[]{"N°", "Lugar", "Fecha"});
                modelo.setRowCount(0);
                for (Tribunal t : repo.tribunalesEntreFechas(desde.getText(), hasta.getText())) {
                    modelo.addRow(new Object[]{
                            t.getNumTribunal(), t.getLugarExamen(), t.getFechaDefensa()
                    });
                }
            }
        });

        setVisible(true);
    }
}

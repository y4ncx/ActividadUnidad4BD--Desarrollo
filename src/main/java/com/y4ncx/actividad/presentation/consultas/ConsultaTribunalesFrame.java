package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.infrastructure.TribunalRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConsultaTribunalesFrame extends JFrame {
    private DefaultTableModel modelo;
    private JTable tabla;
    private TribunalRepositoryImpl repo;

    public ConsultaTribunalesFrame() {
        setTitle("⚖️ Consultas de Tribunales");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        repo = new TribunalRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnListar = new JButton("📋 Listar Tribunales");
        btnListar.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{
                    "ID Tribunal", "Lugar", "Cantidad Profesores", "Alumno Presente", "Defendido", "Fecha Defensa"
            });
            modelo.setRowCount(0);
            for (Tribunal t : repo.listarTodos()) {
                modelo.addRow(new Object[]{
                        t.getNumTribunal(),
                        t.getLugarExamen(),
                        t.getCantidadProfesores(),
                        t.isAlumnoPresente(),
                        t.isTfcDefendido(),
                        t.getFechaDefensa()
                });
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnListar);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }
}

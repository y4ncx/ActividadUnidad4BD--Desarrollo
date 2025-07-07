package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.infrastructure.ProfesorRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaProfesoresFrame extends JFrame {
    private DefaultTableModel modelo;
    private JTable tabla;
    private ProfesorRepositoryImpl repo;

    public ConsultaProfesoresFrame() {
        setTitle("👨‍🏫 Consultas de Profesores");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        repo = new ProfesorRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnListar = new JButton("📋 Listar Profesores");
        btnListar.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Domicilio"});
            modelo.setRowCount(0);
            for (Profesor p : repo.listarTodos()) {
                modelo.addRow(new Object[]{p.getDni(), p.getNombreCompleto(), p.getDomicilio()});
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnListar);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }
}

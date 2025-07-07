package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.infrastructure.GrupoRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaGruposFrame extends JFrame {
    private DefaultTableModel modelo;
    private JTable tabla;
    private GrupoRepositoryImpl repo;

    public ConsultaGruposFrame() {
        setTitle("🔬 Consultas de Grupos de Investigación");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        repo = new GrupoRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnListar = new JButton("📋 Listar Grupos");
        btnListar.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"ID Grupo", "Nombre", "Integrantes", "Fecha Incorporación"});
            modelo.setRowCount(0);
            for (Grupo g : repo.listarTodos()) {
                modelo.addRow(new Object[]{
                        g.getNumGrupo(),
                        g.getNombreGrupo(),
                        g.getNumComponentes(),
                        g.getFechaIncorporacion()
                });
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnListar);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }
}

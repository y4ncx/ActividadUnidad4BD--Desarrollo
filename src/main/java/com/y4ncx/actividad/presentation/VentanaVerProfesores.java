package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.infrastructure.ProfesorRepositoryImpl;
import com.y4ncx.actividad.repository.ProfesorRepository;
import com.y4ncx.actividad.presentation.consultas.ConsultaProfesoresFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerProfesores extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private ProfesorRepository repo = new ProfesorRepositoryImpl();

    public VentanaVerProfesores() {
        setTitle("👨‍🏫 Gestión de Profesores");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(new String[]{"DNI", "Nombre", "Dirección"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JButton btnAgregar = crearBoton(" Agregar");
        JButton btnEditar = crearBoton("️ Editar");
        JButton btnEliminar = crearBoton("️ Eliminar");
        JButton btnConsultas = crearBoton(" Consultas");

        btnAgregar.addActionListener(e -> new VentanaAgregarProfesor(this::cargar));
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un profesor para editar.");
                return;
            }
            new VentanaEditarProfesor(tabla, this::cargar);
        });
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) return;
            String dni = tabla.getValueAt(fila, 0).toString();
            repo.eliminar(dni);
            cargar();
        });
        btnConsultas.addActionListener(e -> new ConsultaProfesoresFrame());

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(btnAgregar); panel.add(btnEditar); panel.add(btnEliminar); panel.add(btnConsultas);
        add(panel, BorderLayout.SOUTH);

        cargar();
        setVisible(true);
    }

    private void cargar() {
        modelo.setRowCount(0);
        for (Profesor p : repo.listarTodos()) {
            modelo.addRow(new Object[]{p.getDni(), p.getNombreCompleto(), p.getDomicilio()});
        }
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 120, 255));
        btn.setFocusPainted(false);
        return btn;
    }
}

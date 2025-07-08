package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.infrastructure.TrabajosFinCarreraRepositoryImpl;
import com.y4ncx.actividad.repository.TrabajosFinCarreraRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerTFC extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private TrabajosFinCarreraRepository repo;

    public VentanaVerTFC() {
        setTitle("📚 Gestión de Trabajos Fin de Carrera");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        repo = new TrabajosFinCarreraRepositoryImpl();

        modelo = new DefaultTableModel(new String[]{
                "Orden", "Tema", "Fecha Inicio", "Alumno", "Profesor"
        }, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Botones
        JButton btnAgregar = new JButton(" Agregar");
        JButton btnEditar = new JButton("️ Editar");
        JButton btnEliminar = new JButton("️ Eliminar");
        JButton btnConsultas = new JButton(" Consultas");

        btnAgregar.setBackground(new Color(0, 120, 255));
        btnEditar.setBackground(new Color(0, 120, 255));
        btnEliminar.setBackground(new Color(0, 120, 255));
        btnConsultas.setBackground(new Color(100, 100, 255));

        btnAgregar.setForeground(Color.WHITE);
        btnEditar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnConsultas.setForeground(Color.WHITE);

        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConsultas.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultas);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción para botón Agregar
        btnAgregar.addActionListener(e -> new VentanaAgregarTFC(this::cargarTFC));

        // Acción Consultas (debes tener ConsultaTFCFrame)
        btnConsultas.addActionListener(e -> new com.y4ncx.actividad.presentation.consultas.ConsultaTFCFrame());

        // Acciones editar y eliminar (opcional)
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un TFC para editar");
                return;
            }
            new VentanaEditarTFC(tabla, modelo, this::cargarTFC);
        });


        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un TFC para eliminar");
                return;
            }

            int numOrden = (int) modelo.getValueAt(fila, 0); // Se asume que el num_orden está en la columna 0

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar el TFC #" + numOrden + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                repo.eliminar(numOrden);
                JOptionPane.showMessageDialog(this, "🗑️ TFC eliminado correctamente");
                cargarTFC(); // Refresca la tabla
            }
        });

        cargarTFC();
        setVisible(true);
    }

    private void cargarTFC() {
        modelo.setRowCount(0);
        List<TrabajosFinCarrera> lista = repo.listarTodos();
        for (TrabajosFinCarrera tfc : lista) {
            modelo.addRow(new Object[]{
                    tfc.getNumOrden(),
                    tfc.getTema(),
                    tfc.getFechaInicio(),
                    tfc.getAlumnoRealiza(),
                    tfc.getProfesorDirige()
            });
        }
    }
}

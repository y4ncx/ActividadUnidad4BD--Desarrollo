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
        setTitle("📚 Gestión de T.F.C.");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new TrabajosFinCarreraRepositoryImpl();

        // Configurar tabla
        modelo = new DefaultTableModel(new String[]{"N° Orden", "Tema", "Fecha Inicio", "Alumno"}, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Botones
        JButton btnAgregar = crearBoton("➕ Agregar");
        JButton btnEditar = crearBoton("✏️ Editar");
        JButton btnEliminar = crearBoton("🗑️ Eliminar");
        JButton btnConsultas = crearBoton("📊 Ver Consultas");

        btnAgregar.addActionListener(e -> {
            JTextField campoOrden = new JTextField();
            JTextField campoTema = new JTextField();
            JTextField campoFecha = new JTextField(); // formato yyyy-mm-dd
            JTextField campoAlumno = new JTextField();

            Object[] campos = {
                    "Número de orden:", campoOrden,
                    "Tema:", campoTema,
                    "Fecha de inicio (YYYY-MM-DD):", campoFecha,
                    "Alumno (N° matrícula):", campoAlumno
            };

            int resultado = JOptionPane.showConfirmDialog(this, campos, "Agregar T.F.C.", JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    int orden = Integer.parseInt(campoOrden.getText());
                    String tema = campoTema.getText();
                    String fecha = campoFecha.getText();
                    String alumno = campoAlumno.getText();

                    if (tema.isEmpty() || fecha.isEmpty() || alumno.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.");
                        return;
                    }

                    TrabajosFinCarrera nuevo = new TrabajosFinCarrera(orden, tema, java.time.LocalDate.parse(fecha), alumno);
                    repo.agregar(nuevo);
                    cargarTFC();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al agregar. Verifica los datos.");
                }
            }
        });


        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un T.F.C. para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            new VentanaEditarTFC(tabla, this::cargarTFC);
        });


        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un T.F.C. para eliminar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar este T.F.C.?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int orden = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                repo.eliminar(orden);
                cargarTFC();
            }
        });

        btnConsultas.addActionListener(e -> new com.y4ncx.actividad.presentation.consultas.ConsultaTFCFrame());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultas);

        add(panelBotones, BorderLayout.SOUTH);

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
                    tfc.getAlumnoRealiza()
            });
        }
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(0, 120, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}

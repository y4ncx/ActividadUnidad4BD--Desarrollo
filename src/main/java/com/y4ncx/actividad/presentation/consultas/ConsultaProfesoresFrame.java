package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.Profesor;
import com.y4ncx.actividad.infrastructure.ProfesorRepositoryImpl;
import com.y4ncx.actividad.repository.ProfesorRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaProfesoresFrame extends JFrame {

    private final ProfesorRepository repo = new ProfesorRepositoryImpl();
    private final JTable tabla;
    private final DefaultTableModel modelo;

    public ConsultaProfesoresFrame() {
        setTitle("📊 Consultas de Profesores");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton btnTodos = new JButton("📋 Listar todos los profesores");
        JButton btnMasDeUnTFC = new JButton("🎓 Profesores con más de un T.F.C.");
        JButton btnColaboraciones = new JButton("🤝 Profesores con colaboraciones");
        JButton btnCantidadColaboraciones = new JButton("📈 Cantidad de colaboraciones por profesor");
        JButton btnNoDirigieron = new JButton("❌ No dirigieron T.F.C pero colaboraron");

        panelBotones.add(btnTodos);
        panelBotones.add(btnMasDeUnTFC);
        panelBotones.add(btnColaboraciones);
        panelBotones.add(btnCantidadColaboraciones);
        panelBotones.add(btnNoDirigieron);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.WEST);

        // Acción 1: Listar todos
        btnTodos.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Dirección"});
            modelo.setRowCount(0);
            List<Profesor> lista = repo.listarTodos();
            for (Profesor p : lista) {
                modelo.addRow(new Object[]{p.getDni(), p.getNombreCompleto(), p.getDomicilio()});
            }
        });

        // Acción 2: Más de un TFC
        btnMasDeUnTFC.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Dirección"});
            modelo.setRowCount(0);
            List<Profesor> lista = repo.conMasDeUnTFC();
            for (Profesor p : lista) {
                modelo.addRow(new Object[]{p.getDni(), p.getNombreCompleto(), p.getDomicilio()});
            }
        });

        // Acción 3: Profesores con colaboraciones
        btnColaboraciones.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"Profesor", "Tipo de colaboración"});
            modelo.setRowCount(0);
            List<String[]> lista = repo.profesoresConColaboraciones();
            for (String[] fila : lista) {
                modelo.addRow(fila);
            }
        });

        // Acción 4: Cantidad de colaboraciones por profesor
        btnCantidadColaboraciones.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"Profesor", "Total de colaboraciones"});
            modelo.setRowCount(0);
            List<String[]> lista = repo.cantidadColaboracionesPorProfesor();
            for (String[] fila : lista) {
                modelo.addRow(fila);
            }
        });

        // Acción 5: No dirigieron TFC pero colaboraron
        btnNoDirigieron.addActionListener(e -> {
            modelo.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Dirección"});
            modelo.setRowCount(0);
            List<String[]> lista = repo.profesoresSinDireccionTFCConColaboracion();
            for (String[] p : lista) {
                modelo.addRow(new Object[]{p[0], p[1], p[2]});
            }

        });

        setVisible(true);
    }
}

package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.TrabajosFinCarrera;
import com.y4ncx.actividad.infrastructure.TrabajosFinCarreraRepositoryImpl;
import com.y4ncx.actividad.repository.TrabajosFinCarreraRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaTFCFrame extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private TrabajosFinCarreraRepository repo;

    public ConsultaTFCFrame() {
        setTitle("📚 Consultas de T.F.C.");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        repo = new TrabajosFinCarreraRepositoryImpl();
        modelo = new DefaultTableModel(new String[]{"Orden", "Tema", "Fecha Inicio", "Alumno"}, 0);
        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(5, 2, 5, 5));

        JButton btnTodos = new JButton("📋 Listar todos");
        JButton btnEnCurso = new JButton("🚧 TFC en curso");
        JButton btnPorAlumno = new JButton("🔍 Buscar por alumno");
        JButton btnUltimos6Meses = new JButton("🕒 Defendidos últimos 6 meses");
        JButton btnTemasColaborados = new JButton("👥 Temas con colaboración");
        JButton btnPorAnio = new JButton("📅 TFC por año");
        JButton btnPorProfesor = new JButton("👨‍🏫 Cantidad por profesor");
        JButton btnPorTribunal = new JButton("⚖️ Cantidad por tribunal");
        JButton btnMasReciente = new JButton("🆕 Defensa más reciente");
        JButton btnPorTribunalFecha = new JButton("📆 Defendidos por tribunal y fecha");

        panelBotones.add(btnTodos);
        panelBotones.add(btnEnCurso);
        panelBotones.add(btnPorAlumno);
        panelBotones.add(btnUltimos6Meses);
        panelBotones.add(btnTemasColaborados);
        panelBotones.add(btnPorAnio);
        panelBotones.add(btnPorProfesor);
        panelBotones.add(btnPorTribunal);
        panelBotones.add(btnMasReciente);
        panelBotones.add(btnPorTribunalFecha);

        add(panelBotones, BorderLayout.SOUTH);

        // Botón Volver arriba
        JButton btnVolver = new JButton("🔙 Volver");
        btnVolver.setBackground(Color.GRAY);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> dispose());

        JPanel panelVolver = new JPanel();
        panelVolver.add(btnVolver);
        add(panelVolver, BorderLayout.NORTH);

        // Acciones
        btnTodos.addActionListener(e -> mostrarLista(repo.listarTodos()));
        btnEnCurso.addActionListener(e -> mostrarLista(repo.listarEnCurso()));
        btnPorAlumno.addActionListener(e -> {
            String dni = JOptionPane.showInputDialog(this, "DNI del alumno:");
            TrabajosFinCarrera t = repo.obtenerPorAlumno(dni);
            modelo.setRowCount(0);
            if (t != null) {
                modelo.addRow(new Object[]{t.getNumOrden(), t.getTema(), t.getFechaInicio(), t.getAlumnoRealiza()});
            }
        });
        btnUltimos6Meses.addActionListener(e -> mostrarLista(repo.defendidosUltimos6Meses()));
        btnTemasColaborados.addActionListener(e -> {
            modelo.setRowCount(0);
            for (String tema : repo.temasConColaboracion()) {
                modelo.addRow(new Object[]{"-", tema, "-", "-"});
            }
        });
        btnPorAnio.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Año:");
            try {
                int anio = Integer.parseInt(input);
                mostrarLista(repo.listarPorAnio(anio));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Año inválido.");
            }
        });
        btnPorProfesor.addActionListener(e -> {
            modelo.setRowCount(0);
            for (String[] fila : repo.cantidadPorProfesor()) {
                modelo.addRow(new Object[]{"-", "Profesor: " + fila[0], "Cantidad: " + fila[1], "-"});
            }
        });
        btnPorTribunal.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID Tribunal:");
            int cantidad = repo.cantidadPorTribunal(id);
            modelo.setRowCount(0);
            modelo.addRow(new Object[]{"-", "Tribunal " + id, "TFC evaluados: " + cantidad, "-"});
        });
        btnMasReciente.addActionListener(e -> {
            TrabajosFinCarrera t = repo.defensaMasReciente();
            modelo.setRowCount(0);
            if (t != null) {
                modelo.addRow(new Object[]{t.getNumOrden(), t.getTema(), t.getFechaInicio(), t.getAlumnoRealiza()});
            }
        });
        btnPorTribunalFecha.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID Tribunal:");
            String fecha = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD):");
            mostrarLista(repo.defendidosPorTribunalYFecha(id, fecha));
        });

        setVisible(true);
    }

    private void mostrarLista(List<TrabajosFinCarrera> lista) {
        modelo.setRowCount(0);
        for (TrabajosFinCarrera t : lista) {
            modelo.addRow(new Object[]{
                    t.getNumOrden(),
                    t.getTema(),
                    t.getFechaInicio(),
                    t.getAlumnoRealiza()
            });
        }
    }
}

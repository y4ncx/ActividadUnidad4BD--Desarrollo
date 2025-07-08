package com.y4ncx.actividad.presentation.consultas;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.infrastructure.GrupoRepositoryImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaGruposFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private GrupoRepositoryImpl repo = new GrupoRepositoryImpl();

    public ConsultaGruposFrame() { // CORREGIDO: constructor correcto
        setTitle("🔍 Consultas de Grupos");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Tabla
        modelo = new DefaultTableModel(new String[]{"#Grupo", "Nombre", "#Integrantes", "Fecha Incorporación"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Panel de botones
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton btnTodos = new JButton("📋 Listar todos los grupos");
        JButton btnMasDeX = new JButton("📈 Grupos con más de X integrantes");
        JButton btnPorNumero = new JButton("🔍 Buscar grupo por número");
        JButton btnPorAño = new JButton("🗓️ Grupos incorporados en un año");
        JButton btnOrdenados = new JButton("⬇️ Grupos ordenados por cantidad de integrantes");

        panel.add(btnTodos);
        panel.add(btnMasDeX);
        panel.add(btnPorNumero);
        panel.add(btnPorAño);
        panel.add(btnOrdenados);

        add(panel, BorderLayout.EAST);

        // Acciones
        btnTodos.addActionListener(e -> {
            List<Grupo> lista = repo.listarTodos();
            cargarDatos(lista);
        });

        btnMasDeX.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "¿Más de cuántos integrantes?");
            if (input != null && !input.isEmpty()) {
                try {
                    int cantidad = Integer.parseInt(input);
                    List<Grupo> lista = repo.gruposConMasDe(cantidad);
                    cargarDatos(lista);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingresa un número válido.");
                }
            }
        });

        btnPorNumero.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Número de grupo:");
            if (input != null && !input.isEmpty()) {
                try {
                    int numero = Integer.parseInt(input);
                    Grupo grupo = repo.buscarPorNumero(numero);
                    if (grupo != null) {
                        cargarDatos(List.of(grupo));
                    } else {
                        JOptionPane.showMessageDialog(this, "Grupo no encontrado.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingresa un número válido.");
                }
            }
        });

        btnPorAño.addActionListener(e -> {
            String anio = JOptionPane.showInputDialog(this, "Año (YYYY):");
            if (anio != null && !anio.isEmpty()) {
                List<Grupo> lista = repo.incorporadosEnAnio(anio);
                cargarDatos(lista);
            }
        });

        btnOrdenados.addActionListener(e -> {
            List<Grupo> lista = repo.ordenadosPorIntegrantesDesc();
            cargarDatos(lista);
        });

        setVisible(true);
    }

    private void cargarDatos(List<Grupo> lista) {
        modelo.setRowCount(0);
        for (Grupo g : lista) {
            modelo.addRow(new Object[]{
                    g.getNumGrupo(),
                    g.getNombreGrupo(),
                    g.getNumComponentes(),
                    g.getFechaIncorporacion()
            });
        }
    }
}

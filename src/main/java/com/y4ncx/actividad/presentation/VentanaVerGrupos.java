package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Grupo;
import com.y4ncx.actividad.infrastructure.GrupoRepositoryImpl;
import com.y4ncx.actividad.presentation.consultas.ConsultaGruposFrame;
import com.y4ncx.actividad.repository.GrupoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerGrupos extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private GrupoRepositoryImpl repo;

    public VentanaVerGrupos() {
        setTitle("🧪 Gestión de Grupos de Investigación");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new GrupoRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnAgregar = crearBoton(" Agregar");
        JButton btnEditar = crearBoton(" Editar");
        JButton btnEliminar = crearBoton(" Eliminar");
        JButton btnConsultas = crearBoton(" Consultas");

        btnConsultas.setBackground(new Color(100, 100, 255));


        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultas);

        add(panelBotones, BorderLayout.SOUTH);

        mostrarDatos();

        btnAgregar.addActionListener(e -> {
            JTextField numGrupo = new JTextField();
            JTextField nombre = new JTextField();
            JTextField componentes = new JTextField();
            JTextField fecha = new JTextField();

            Object[] inputs = {
                    "Número de Grupo:", numGrupo,
                    "Nombre:", nombre,
                    "N° de componentes:", componentes,
                    "Fecha incorporación (YYYY-MM-DD):", fecha
            };

            int res = JOptionPane.showConfirmDialog(this, inputs, "Agregar Grupo", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    Grupo nuevo = new Grupo(
                            Integer.parseInt(numGrupo.getText()),
                            nombre.getText(),
                            Integer.parseInt(componentes.getText()),
                            fecha.getText()
                    );
                    GrupoRepository repo = new GrupoRepositoryImpl();
                    repo.agregar(nuevo);
                    JOptionPane.showMessageDialog(this, "✅ Grupo agregado correctamente.");
                    cargarGrupos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "❌ Error en los datos numéricos.");
                }
            }
        });


        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un grupo de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int numGrupo = (int) modelo.getValueAt(fila, 0);
            JTextField nombre = new JTextField((String) modelo.getValueAt(fila, 1));
            JTextField componentes = new JTextField(modelo.getValueAt(fila, 2).toString());
            JTextField fecha = new JTextField((String) modelo.getValueAt(fila, 3));

            Object[] inputs = {
                    "Nombre:", nombre,
                    "N° componentes:", componentes,
                    "Fecha incorporación:", fecha
            };

            int res = JOptionPane.showConfirmDialog(this, inputs, "Editar Grupo", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    Grupo actualizado = new Grupo(
                            numGrupo,
                            nombre.getText(),
                            Integer.parseInt(componentes.getText()),
                            fecha.getText()
                    );
                    GrupoRepository repo = new GrupoRepositoryImpl();
                    repo.actualizar(actualizado);
                    JOptionPane.showMessageDialog(this, "✅ Grupo actualizado.");
                    cargarGrupos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "❌ Error en los datos numéricos.");
                }
            }
        });



        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un grupo para eliminar.");
                return;
            }

            int numGrupo = (int) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas eliminar el grupo #" + numGrupo + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                GrupoRepository repo = new GrupoRepositoryImpl();
                repo.eliminar(numGrupo);
                JOptionPane.showMessageDialog(this, "✅ Grupo eliminado");
                cargarGrupos(); // método que refresca la tabla
            }
        });




        btnConsultas.addActionListener(e -> {
           new ConsultaGruposFrame();
        });

        setVisible(true);
    }

    private void mostrarDatos() {
        modelo.setColumnIdentifiers(new String[]{"N° Grupo", "Nombre", "Componentes", "Fecha Incorporación"});
        modelo.setRowCount(0);
        for (Grupo g : repo.listarTodos()) {
            modelo.addRow(new Object[]{
                    g.getNumGrupo(), g.getNombreGrupo(), g.getNumComponentes(), g.getFechaIncorporacion()
            });
        }
    }

    private void cargarGrupos() {
        modelo.setRowCount(0);
        GrupoRepository repo = new GrupoRepositoryImpl();
        List<Grupo> lista = repo.listarTodos();
        for (Grupo g : lista) {
            modelo.addRow(new Object[]{
                    g.getNumGrupo(),
                    g.getNombreGrupo(),
                    g.getNumComponentes(),
                    g.getFechaIncorporacion()
            });
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

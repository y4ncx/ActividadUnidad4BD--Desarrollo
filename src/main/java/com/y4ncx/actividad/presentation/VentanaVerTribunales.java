package com.y4ncx.actividad.presentation;

import com.y4ncx.actividad.domain.Tribunal;
import com.y4ncx.actividad.infrastructure.TribunalRepositoryImpl;
import com.y4ncx.actividad.repository.TribunalRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerTribunales extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private TribunalRepositoryImpl repo;

    public VentanaVerTribunales() {
        setTitle("⚖️ Gestión de Tribunales");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        repo = new TribunalRepositoryImpl();
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnAgregar = crearBoton(" Agregar");
        JButton btnEditar = crearBoton(" Editar");
        JButton btnEliminar = crearBoton("️ Eliminar");
        JButton btnConsultas = crearBoton(" Consultas");
        btnConsultas.setBackground(new Color(100, 100, 255));


        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultas);

        add(panelBotones, BorderLayout.SOUTH);

        mostrarDatos();

        btnAgregar.addActionListener(e -> {
            JTextField num = new JTextField();
            JTextField lugar = new JTextField();
            JTextField profs = new JTextField();
            JTextField campoPresente = new JTextField();
            JTextField campoDefendido = new JTextField();
            JTextField fecha = new JTextField();

            Object[] inputs = {
                    "N° Tribunal:", num,
                    "Lugar de examen:", lugar,
                    "Cantidad de profesores:", profs,
                    "Alumno presente (DNI):", campoPresente,
                    "TFC defendido (N° orden):", campoDefendido,
                    "Fecha defensa (YYYY-MM-DD):", fecha
            };

            int res = JOptionPane.showConfirmDialog(
                    null, inputs, "➕ Agregar Tribunal", JOptionPane.OK_CANCEL_OPTION
            );

            if (res == JOptionPane.OK_OPTION) {
                try {
                    int numero = Integer.parseInt(num.getText().trim());
                    String lugarExamen = lugar.getText().trim();
                    int cantidad = Integer.parseInt(profs.getText().trim());
                    String alumnoPresente = campoPresente.getText().trim();
                    String tfcDefendido = campoDefendido.getText().trim();
                    String fechaDefensa = fecha.getText().trim();

                    // Aquí iría el guardado en BD si ya lo tienes
                    Tribunal nuevo = new Tribunal(numero, lugarExamen, cantidad, alumnoPresente, tfcDefendido, fechaDefensa);
                    TribunalRepository repo = new TribunalRepositoryImpl();
                    repo.agregar(nuevo);

                    JOptionPane.showMessageDialog(this, "✅ Tribunal agregado exitosamente");
                    mostrarDatos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "⚠️ Verifica que los campos numéricos estén bien escritos.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "❌ Error al guardar: " + ex.getMessage());
                }
            }
        });


        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un tribunal de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }


            int numTribunal = (int) modelo.getValueAt(fila, 0);
            JTextField txtLugar = new JTextField(modelo.getValueAt(fila, 1).toString());
            JTextField txtCantidad = new JTextField(modelo.getValueAt(fila, 2).toString());
            JTextField txtAlumnoPresente = new JTextField(modelo.getValueAt(fila, 3).toString());
            JTextField txtTfcDefendido = new JTextField(modelo.getValueAt(fila, 4).toString());
            JTextField txtFecha = new JTextField(modelo.getValueAt(fila, 5).toString());

            Object[] inputs = {
                    "Lugar de examen:", txtLugar,
                    "Cantidad de profesores:", txtCantidad,
                    "Alumno presente (DNI):", txtAlumnoPresente,
                    "TFC defendido (N° orden):", txtTfcDefendido,
                    "Fecha defensa (YYYY-MM-DD):", txtFecha
            };

            int res = JOptionPane.showConfirmDialog(this, inputs, "✏️ Editar Tribunal", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    String lugar = txtLugar.getText().trim();
                    int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                    String alumnoPresente = txtAlumnoPresente.getText().trim();
                    String tfcDefendido = txtTfcDefendido.getText().trim();
                    String fecha = txtFecha.getText().trim();

                    Tribunal actualizado = new Tribunal(numTribunal, lugar, cantidad, alumnoPresente, tfcDefendido, fecha);
                    TribunalRepository repo = new TribunalRepositoryImpl();
                    repo.actualizar(actualizado);

                    JOptionPane.showMessageDialog(this, "✅ Tribunal actualizado correctamente");
                    mostrarDatos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "❌ Error al actualizar tribunal: " + ex.getMessage());
                }
            }
        });



        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un tribunal para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar tribunal #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    TribunalRepository repo = new TribunalRepositoryImpl();
                    repo.eliminar(id);
                    JOptionPane.showMessageDialog(this, "✅ Tribunal eliminado correctamente");
                    mostrarDatos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar tribunal: " + ex.getMessage());
                }
            }
        });



        btnConsultas.addActionListener(e -> {
            new com.y4ncx.actividad.presentation.consultas.ConsultaTribunalesFrame();
        });

        setVisible(true);
    }

    private void mostrarDatos() {
        modelo.setColumnIdentifiers(new String[]{"N° Tribunal", "Lugar", "Profesores", "Alumno presente", "TFC defendido", "Fecha Defensa"});
        modelo.setRowCount(0);
        for (Tribunal t : repo.listarTodos()) {
            modelo.addRow(new Object[]{
                    t.getNumTribunal(), t.getLugarExamen(), t.getCantidadProfesores(),
                    t.getAlumnoPresente(), t.getTfcDefendido(), t.getFechaDefensa()
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

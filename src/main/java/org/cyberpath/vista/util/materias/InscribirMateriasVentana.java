package org.cyberpath.vista.util.materias;

import org.cyberpath.controlador.PantallasControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Salidas;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.pantallas.combo.MenuPrincipalVentana;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class InscribirMateriasVentana extends PlantillaBaseVentana {

    private JPanel panelInscripciones;
    private JTextField campoBusqueda;
    private JPanel panelMaterias;

    public InscribirMateriasVentana() {
        super("Inscripción de Materias", 1000, 700);
    }

    @Override
    protected void inicializarComponentes() {
        panelInscripciones = crearPanelDegradadoDecorativo();
        panelInscripciones.setOpaque(false); // Si usas fondo decorativo

        // --- Panel superior con campo de búsqueda ---
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setOpaque(false);

        JPanel filaCampo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaCampo.setOpaque(false);

        JLabel buscar = crearEtiqueta("Buscar materia: ");
        buscar.setForeground(Color.WHITE);

        campoBusqueda = new JTextField(30);
        panelBusqueda.add(Box.createHorizontalStrut(10)); // espacio entre campo y botón
        filaCampo.add(buscar);
        filaCampo.add(campoBusqueda);

        JPanel filaBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaBoton.setOpaque(false);
        JButton botonBuscar = crearBotonEstilizado("Buscar", null, e -> cargarMateriasFiltradas());
        botonBuscar.setPreferredSize(new Dimension(120, 30));
        filaBoton.add(botonBuscar);

        panelBusqueda.add(filaCampo);
        panelBusqueda.add(Box.createVerticalStrut(5)); // Espacio entre campo y botón
        panelBusqueda.add(filaBoton);

        // --- Panel central con botones de materias ---
        panelMaterias = new JPanel();
        panelMaterias.setLayout(new BoxLayout(panelMaterias, BoxLayout.Y_AXIS));
        panelMaterias.setOpaque(false);

        JScrollPane scrollMaterias = new JScrollPane(panelMaterias);
        scrollMaterias.setOpaque(false);
        scrollMaterias.getViewport().setOpaque(false);
        scrollMaterias.setBorder(BorderFactory.createEmptyBorder());

        panelInscripciones.add(panelBusqueda, BorderLayout.NORTH);
        panelInscripciones.add(scrollMaterias, BorderLayout.CENTER);

        // Cargar materias al iniciar
        cargarMateriasFiltradas();
    }

    private void cargarMateriasFiltradas() {
        panelMaterias.removeAll();

        String filtro = campoBusqueda.getText().trim().toLowerCase();
        List<Materia> materiasFiltradas = Materia.materiaDao.findAll().stream()
                .filter(m -> m.getNombre().toLowerCase().contains(filtro))
                .collect(Collectors.toList());

        if (materiasFiltradas.isEmpty()) {
            JLabel sinResultados = new JLabel("No se encontraron materias.");
            sinResultados.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            sinResultados.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMaterias.add(Box.createVerticalStrut(20));
            panelMaterias.add(sinResultados);
        } else {
            for (Materia materia : materiasFiltradas) {
                JButton btnMateria = crearBotonEstilizado(materia.getNombre(), null, e -> {
                    int confirmacion = JOptionPane.showConfirmDialog(this,
                            "¿Deseas inscribir la materia '" + materia.getNombre() + "'?",
                            "Confirmar inscripción", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        // Aquí puedes llamar a un servicio o controlador para guardar la inscripción
                        JOptionPane.showMessageDialog(this,
                                "Materia '" + materia.getNombre() + "' inscrita con éxito.",
                                "Inscripción exitosa", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                btnMateria.setPreferredSize(new Dimension(120,30));
                panelMaterias.add(btnMateria);
                panelMaterias.add(Box.createVerticalStrut(10));
            }
        }

        panelMaterias.revalidate();
        panelMaterias.repaint();
    }

    @Override
    protected void agregarEventos() {
        // Ya se agregaron directamente en los botones
    }

    @Override
    public JPanel getContenido() {
        return panelInscripciones;
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(18);
        VariablesGlobales.usuario = ejemplo;
        InscribirMateriasVentana inscribirMateriasVentana = new InscribirMateriasVentana();
        PantallasControlador.asignarContenedor(inscribirMateriasVentana.getContenido());
        SwingUtilities.invokeLater(() -> inscribirMateriasVentana.setVisible(true));
    }
}
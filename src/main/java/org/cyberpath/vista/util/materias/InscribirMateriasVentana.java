package org.cyberpath.vista.util.materias;

import org.cyberpath.controlador.Pantallas.PantallasControlador;
import org.cyberpath.controlador.materias.InscripcionMateriasControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
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
        panelInscripciones.setLayout(new GridBagLayout());
        panelInscripciones.setOpaque(false);

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

        JButton botonBuscar = crearBotonEstilizado("Buscar", null, e -> cargarMateriasFiltradas());
        botonBuscar.setPreferredSize(new Dimension(120, 30));
        filaCampo.add(botonBuscar);

        panelBusqueda.add(filaCampo);
        panelBusqueda.add(Box.createVerticalStrut(5)); // Espacio entre campo y botón

        // --- Panel central con botones de materias ---
        panelMaterias = new JPanel(new GridBagLayout());
        panelMaterias.setOpaque(false);

        JScrollPane scrollMaterias = new JScrollPane(panelMaterias);
        scrollMaterias.setOpaque(false);
        scrollMaterias.getViewport().setOpaque(false);
        scrollMaterias.setBorder(BorderFactory.createEmptyBorder());

        panelInscripciones.add(panelBusqueda, crearConstraintCentrado(0,0,1,1,100));
        panelInscripciones.add(scrollMaterias, crearConstraintCentrado(1,0,1,1,100));

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
            sinResultados.setForeground(Color.WHITE);
            sinResultados.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMaterias.add(Box.createVerticalStrut(20));
            panelMaterias.add(sinResultados);
        } else {
            int i = 1;
            for (Materia materia : materiasFiltradas) {
                JButton btnMateria = crearBotonEstilizado(materia.getNombre(), null, e -> {
                    int confirmacion = JOptionPane.showConfirmDialog(this,
                            "¿Deseas inscribir la materia '" + materia.getNombre() + "'?",
                            "Confirmar inscripción", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        new InscripcionMateriasControlador().procesarInscripcion(materia);
                        JOptionPane.showMessageDialog(this,
                                "Materia '" + materia.getNombre() + "' inscrita con éxito.",
                                "Inscripción exitosa", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                panelMaterias.add(btnMateria, crearConstraintCentrado(i,0,1,1,100));
                panelMaterias.add(Box.createVerticalStrut(10));
                btnMateria.setPreferredSize(new Dimension(600, 40));
                i++;
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
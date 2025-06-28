package org.cyberpath.vista.pantallas.materias;

import org.cyberpath.controlador.materias.InscribirMateriasControlador;
import org.cyberpath.controlador.pantallas.PantallasControlador;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.relacionesUsuario.UsuarioMateria;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.Sistema;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class InscribirMateriasVentana extends PlantillaBaseVentana {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Inscripción de Materias";
    private static final int SEARCH_FIELD_COLUMNS = 30;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_HEIGHT_MATERIA = 40;

    private Sistema sistema = Sistema.getInstance();
    private JPanel panelInscripciones;
    private JTextField campoBusqueda;
    private JPanel panelMaterias;

    public InscribirMateriasVentana() throws Exception {
        super(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
        initializeAccessibilityThread();
    }

    public static void main(String[] args) throws Exception {
        Usuario ejemplo = Usuario.usuarioDao.findById(18);
        VariablesGlobales.usuario = ejemplo;
        InscribirMateriasVentana inscribirMateriasVentana = new InscribirMateriasVentana();
        PantallasControlador.asignarContenedor(inscribirMateriasVentana.getContenido());
        SwingUtilities.invokeLater(() -> inscribirMateriasVentana.setVisible(true));
    }

    @Override
    protected void inicializarComponentes() {
        panelInscripciones = crearPanelDegradadoDecorativo();
        panelInscripciones.setLayout(new GridBagLayout());
        panelInscripciones.setOpaque(false);

        panelInscripciones.add(createSearchPanel(), crearConstraintCentrado(0, 0, 1, 1, 100));
        panelInscripciones.add(createScrollPanel(), crearConstraintCentrado(1, 0, 1, 1, 100));
        panelInscripciones.add(crearBotonSalirAPantallaPrincipal(), crearConstraintCentrado(2, 0, 1, 1, 100));

        cargarMateriasFiltradas();
    }

    private void initializeAccessibilityThread() {
        new Thread(() -> {
            sistema.pausa(2);
            try {
                InscribirMateriasControlador.procesarAccesibilidad(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private JPanel createSearchPanel() {
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setOpaque(false);

        JPanel filaCampo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaCampo.setOpaque(false);

        JLabel buscar = crearEtiqueta("Buscar materia: ");
        buscar.setForeground(Color.WHITE);

        campoBusqueda = new JTextField(SEARCH_FIELD_COLUMNS);
        JButton botonBuscar = crearBotonEstilizado("Buscar", null, e -> cargarMateriasFiltradas());
        botonBuscar.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        filaCampo.add(buscar);
        filaCampo.add(campoBusqueda);
        filaCampo.add(botonBuscar);
        panelBusqueda.add(filaCampo);
        panelBusqueda.add(Box.createVerticalStrut(5));

        return panelBusqueda;
    }

    private JScrollPane createScrollPanel() {
        panelMaterias = new JPanel(new GridBagLayout());
        panelMaterias.setOpaque(false);

        JScrollPane scrollMaterias = new JScrollPane(panelMaterias);
        scrollMaterias.setOpaque(false);
        scrollMaterias.getViewport().setOpaque(false);
        scrollMaterias.setBorder(BorderFactory.createEmptyBorder());

        return scrollMaterias;
    }

    private void cargarMateriasFiltradas() {
        panelMaterias.removeAll();

        List<Integer> idsMateriasInscritas = VariablesGlobales.usuario.getMateriasInscritas().stream()
                .map(inscripcion -> inscripcion.getMateria().getId())
                .collect(Collectors.toList());

        String filtro = campoBusqueda.getText().trim().toLowerCase();

        List<Materia> materiasFiltradas = Materia.materiaDao.findAll().stream()
                .filter(m -> !idsMateriasInscritas.contains(m.getId()))
                .filter(m -> m.getNombre().toLowerCase().contains(filtro))
                .collect(Collectors.toList());

        if (materiasFiltradas.isEmpty()) {
            mostrarMensajeSinResultados();
        } else {
            agregarBotonesMaterias(materiasFiltradas);
        }

        panelMaterias.revalidate();
        panelMaterias.repaint();
    }

    private void mostrarMensajeSinResultados() {
        JLabel sinResultados = new JLabel("No se encontraron materias.");
        sinResultados.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        sinResultados.setForeground(Color.WHITE);
        sinResultados.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMaterias.add(Box.createVerticalStrut(20));
        panelMaterias.add(sinResultados);
    }

    private void agregarBotonesMaterias(List<Materia> materiasFiltradas) {
        int i = 1;
        for (Materia materia : materiasFiltradas) {
            JButton btnMateria = crearBotonEstilizado(materia.getNombre(), null, e -> {
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Deseas inscribir la materia '" + materia.getNombre() + "'?",
                        "Confirmar inscripción", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if ( InscribirMateriasControlador.procesarInscripcion(materia) ){
                        JOptionPane.showMessageDialog(this,
                                "Materia '" + materia.getNombre() + "' inscrita con éxito.",
                                "Inscripción exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Materia '" + materia.getNombre() + "' ya fue inscrita.",
                                "Error en Inscripción", JOptionPane.INFORMATION_MESSAGE);
                    }

                }


            });
            panelMaterias.add(btnMateria, crearConstraintCentrado(i, 0, 1, 1, 100));
            panelMaterias.add(Box.createVerticalStrut(10));
            btnMateria.setPreferredSize(new Dimension(600, BUTTON_HEIGHT_MATERIA));
            i++;
        }
    }

    @Override
    protected void agregarEventos() {
    }

    @Override
    public JPanel getContenido() {
        return panelInscripciones;
    }
}

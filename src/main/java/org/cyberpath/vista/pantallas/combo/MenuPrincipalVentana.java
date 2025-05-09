package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.controlador.Pantallas.PantallasControlador;
import org.cyberpath.controlador.Pantallas.PantallasEnum;
import org.cyberpath.modelo.baseDatos.dao.implementacion.DaoImpl;
import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.modelo.entidades.usuario.Usuario;
import org.cyberpath.util.VariablesGlobales;
import org.cyberpath.vista.util.base.PlantillaBaseVentana;
import org.cyberpath.vista.util.materias.ContenidoPracticoVentana;
import org.cyberpath.vista.util.materias.ContenidoTeoricoVentana;
import org.cyberpath.vista.util.materias.SubtemaVentana;
import org.cyberpath.vista.util.materias.TemaVentana;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Stack;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class MenuPrincipalVentana extends PlantillaBaseVentana {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private final Stack<PanelHistorial> historial = new Stack<>();

    public MenuPrincipalVentana() {
        super("Menú Principal", 1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected void inicializarComponentes() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        getPanelCentral().add(mainPanel, BorderLayout.CENTER);  // Cambio aquí
        mostrarMenuMaterias();
    }

    @Override
    protected void agregarEventos() {
        // Puedes añadir eventos generales aquí si los necesitas
    }

    @Override
    public JPanel getContenido() {
        return mainPanel;
    }

    public JPanel getPanelContenedor() {
        return super.getPanelCentral();
    }

    private void mostrarMenuMaterias() {
        JPanel panelMaterias = crearPanelDegradadoDecorativo("Materias");
        panelMaterias.setLayout(new BoxLayout(panelMaterias, BoxLayout.Y_AXIS));

        // Usa la implementación que inicializa correctamente las relaciones
        List<Materia> materiasInscritas = new DaoImpl<Usuario>().obtenerMateriasInscritasPorUsuario(
                VariablesGlobales.usuario.getId());

        if (!materiasInscritas.isEmpty()) {
            for (Materia materia : materiasInscritas) {
                JButton btnMateria = crearBotonEstilizado(
                        materia.getNombre(), null, e -> mostrarTemas(materia));
                panelMaterias.add(btnMateria, crearConstraintBotonAncho(3, 0, 3, 1, 200));
                panelMaterias.add(Box.createVerticalStrut(5));
            }
        } else {
            JLabel mensaje = crearTituloCentrado("No hay materias inscritas aún");
            mensaje.setFont(new Font("Segoe UI", Font.ITALIC, 22));
            panelMaterias.add(mensaje, crearConstraint(0, 0, 1, 1, 1));
            panelMaterias.add(Box.createVerticalStrut(5));
        }

        JButton btnInscribirMateria = crearBotonEstilizado("Inscribir Materia", null, e -> {
            PantallasControlador.mostrarPantalla(PantallasEnum.INSCRIBIR_MATERIA);
        });
        panelMaterias.add(btnInscribirMateria, crearConstraintCentrado(2, 2, 1, 1, 1));

        mainPanel.add(panelMaterias, "Materias");
        cardLayout.show(mainPanel, "Materias");
    }




    private void mostrarTemas(Materia materia) {
        TemaVentana temaVentana = new TemaVentana(materia, this);
        String nombre = "Temas_" + historial.size();
        historial.push(new PanelHistorial(temaVentana, nombre));
        mainPanel.add(temaVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void mostrarSubtemas(Tema tema) {
        SubtemaVentana subtemaVentana = new SubtemaVentana(tema, this);
        String nombre = "Subtemas_" + historial.size();
        historial.push(new PanelHistorial(subtemaVentana, nombre));
        mainPanel.add(subtemaVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void mostrarContenidoTeorico(Subtema subtema) {
        ContenidoTeoricoVentana contenidoTeoricoVentana = new ContenidoTeoricoVentana(subtema, this);
        String nombre = "ContenidoT_" + historial.size();
        historial.push(new PanelHistorial(contenidoTeoricoVentana, nombre));
        mainPanel.add(contenidoTeoricoVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void mostrarContenidoPractico(Subtema subtema) {
        ContenidoPracticoVentana contenidoPracticoVentana = new ContenidoPracticoVentana(subtema, this);
        String nombre = "ContenidoP_" + historial.size();
        historial.push(new PanelHistorial(contenidoPracticoVentana, nombre));
        mainPanel.add(contenidoPracticoVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void regresar() {
        if (!historial.isEmpty()) {
            PanelHistorial panelActual = historial.pop();
            mainPanel.remove(panelActual.panel);

            if (!historial.isEmpty()) {
                cardLayout.show(mainPanel, historial.peek().nombre);
            } else {
                cardLayout.show(mainPanel, "Materias");
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    public static void main(String[] args) {
        Usuario ejemplo = Usuario.usuarioDao.findById(18);
        VariablesGlobales.usuario = ejemplo;

        SwingUtilities.invokeLater(() -> new MenuPrincipalVentana().setVisible(true));
    }

    private static class PanelHistorial {
        JPanel panel;
        String nombre;

        PanelHistorial(JPanel panel, String nombre) {
            this.panel = panel;
            this.nombre = nombre;
        }
    }
}

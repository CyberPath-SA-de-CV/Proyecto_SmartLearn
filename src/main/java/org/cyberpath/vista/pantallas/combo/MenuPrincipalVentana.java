package org.cyberpath.vista.pantallas.combo;

import org.cyberpath.modelo.entidades.divisionTematica.Materia;
import org.cyberpath.modelo.entidades.divisionTematica.Subtema;
import org.cyberpath.modelo.entidades.divisionTematica.Tema;
import org.cyberpath.vista.util.base.ContenidoConPanelSuperior;
import org.cyberpath.vista.util.materias.ContenidoPracticoVentana;
import org.cyberpath.vista.util.materias.ContenidoTeoricoVentana;
import org.cyberpath.vista.util.materias.SubtemaVentana;
import org.cyberpath.vista.util.materias.TemaVentana;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

import static org.cyberpath.vista.util.componentes.ComponentesReutilizables.*;

public class MenuPrincipalVentana extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final Stack<PanelHistorial> historial = new Stack<>();

    public MenuPrincipalVentana() {
        super("Menú Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        ContenidoConPanelSuperior panelConEncabezado = new ContenidoConPanelSuperior(mainPanel);
        getContentPane().add(panelConEncabezado, BorderLayout.CENTER);

        mostrarMenuMaterias();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipalVentana().setVisible(true));
    }

    private void mostrarMenuMaterias() {
        JPanel panelMaterias = crearPanelDegradadoDecorativo("Materias");
        panelMaterias.setLayout(new BoxLayout(panelMaterias, BoxLayout.Y_AXIS));

        java.util.List<Materia> materias = Materia.materiaDao.findAll();
        for (Materia materia : materias) {
            JButton btnMateria = crearBotonEstilizado(materia.getNombre(), null, e -> mostrarTemas(materia));
            panelMaterias.add(btnMateria, crearConstraintBotonAncho(3, 0, 3, 1, 200));
            panelMaterias.add(Box.createVerticalStrut(5));
        }

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
        String nombre = "Subtemas" + historial.size();
        historial.push(new PanelHistorial(subtemaVentana, nombre));
        mainPanel.add(subtemaVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void mostrarContenidoTeorico(Subtema subtema) {
        ContenidoTeoricoVentana contenidoTeoricoVentana = new ContenidoTeoricoVentana(subtema, this);
        String nombre = "Contenido" + historial.size();
        historial.push(new PanelHistorial(contenidoTeoricoVentana, nombre));
        mainPanel.add(contenidoTeoricoVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void mostrarContenidoPractico(Subtema subtema) {
        ContenidoPracticoVentana contenidoPracticoVentana = new ContenidoPracticoVentana(subtema, this);
        String nombre = "Contenido" + historial.size();
        historial.push(new PanelHistorial(contenidoPracticoVentana, nombre));
        mainPanel.add(contenidoPracticoVentana, nombre);
        cardLayout.show(mainPanel, nombre);
    }

    public void regresar() {
        if (!historial.isEmpty()) {
            PanelHistorial panelActual = historial.pop();
            mainPanel.remove(panelActual.panel);

            if (!historial.isEmpty()) {
                PanelHistorial panelAnterior = historial.peek();
                cardLayout.show(mainPanel, panelAnterior.nombre);
            } else {
                cardLayout.show(mainPanel, "Materias");
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        }
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